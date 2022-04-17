package froztigaming.fantasyorigins.entities.mobs;

import froztigaming.fantasyorigins.ai.goal.GoblinAttackGoal;
import froztigaming.fantasyorigins.ai.goal.SpearAttackGoal;
import froztigaming.fantasyorigins.entities.items.SpearEntity;
import froztigaming.fantasyorigins.entities.items.TritonTridentEntity;
import froztigaming.fantasyorigins.init.EntityInit;
import froztigaming.fantasyorigins.init.ItemInit;
import froztigaming.fantasyorigins.init.SoundInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoblinEntity extends HostileEntity implements RangedAttackMob {
    private static List<GoblinEntity> goblinEntities = new ArrayList<>();

    public GoblinEntity(EntityType<? extends GoblinEntity> entityType, World world) {
        super(entityType, world);
        this.getNavigation().setCanSwim(true);
        this.setCanPickUpLoot(true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 15.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 15.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(2, new SpearAttackGoal(this, 1.0, 80, 10.0f));
        this.goalSelector.add(2, new GoblinAttackGoal(this, 1.0, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]).setGroupRevenge(ZombifiedPiglinEntity.class));
        this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<MerchantEntity>((MobEntity)this, MerchantEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<PigEntity>((MobEntity)this, PigEntity.class, true));
        this.goalSelector.add(3, new FleeEntityGoal<HoglinEntity>(this, HoglinEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<PiglinEntity>(this, PiglinEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<PiglinBruteEntity>(this, PiglinBruteEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<ZoglinEntity>(this, ZoglinEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<ZombifiedPiglinEntity>(this, ZombifiedPiglinEntity.class, 6.0f, 1.0, 1.2));
    }

    public static DefaultAttributeContainer.Builder createGoblinAttributes()
    {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0F);
    }

    public static boolean canSpawn(EntityType<GoblinEntity> type, WorldAccess worldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return isSpawnDark((ServerWorldAccess) worldAccess, blockPos, random);
    }

    @Override
    public double getHeightOffset() {
        return this.isBaby() ? -0.05 : -0.45;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.DEFAULT;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient && this.isAlive() && !this.isAiDisabled()){
            if (this.age % 20 == 0)
            {
                goblinEntities = getNearbyGoblins(Box.of(this.getPos(), 16, 16, 16), this);
                if (goblinEntities.size() > 0)
                {
                    StatusEffectInstance groupStrength = new StatusEffectInstance(StatusEffects.STRENGTH, 20, 0, false, false);
                    {
                        this.addStatusEffect(groupStrength);
                    }
                }
                if (goblinEntities.size() > 0)
                {
                    goblinEntities.clear();
                }
            }
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.initEquipment(difficulty);
        if (!this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty())
        {
            this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0f;
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initEquipment(LocalDifficulty difficulty) {
        if ((double)this.random.nextFloat() > 0.9) {
            int i = this.random.nextInt(16);
            if (i < 10) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ItemInit.SPEAR));
            }
        }
    }

    private static List<GoblinEntity> getNearbyGoblins(Box box, LivingEntity entity)
    {
        World world = entity.getWorld();
        for (int x = (int) box.minX; x < box.maxX; x++) {
            for (int y = (int) box.minY; y < box.maxY; y++) {
                for (int z = (int) box.minZ; z < box.maxZ; z++) {
                    List<Entity> nearbyEntities = world.getOtherEntities(entity, box);
                    for (Entity e: nearbyEntities) {
                        if (e == null) continue;
                        if (goblinEntities.contains(e)) continue;
                        if (e.getType() != EntityInit.GOBLIN) continue;
                        goblinEntities.add((GoblinEntity) e);
                    }
                }
            }
        }
        return goblinEntities;
    }

    @Override
    protected boolean prefersNewEquipment(ItemStack newStack, ItemStack oldStack) {
        if (oldStack.isOf(ItemInit.SPEAR)) {
            if (newStack.isOf(ItemInit.SPEAR)) {
                return newStack.getDamage() < oldStack.getDamage();
            }
            return false;
        }
        if (newStack.isOf(ItemInit.SPEAR)) {
            return true;
        }
        return super.prefersNewEquipment(newStack, oldStack);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        if (this.getMainHandStack().isOf(ItemInit.SPEAR))
        {
            SpearEntity spearEntity = new SpearEntity(this.world, (LivingEntity)this, new ItemStack(ItemInit.SPEAR));
            double d = target.getX() - this.getX();
            double e = target.getBodyY(0.3333333333333333) - spearEntity.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            spearEntity.setVelocity(d, e + g * (double)0.2f, f, 1.6f, 14 - this.world.getDifficulty().getId() * 4);
            this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity(spearEntity);
        }
        if (this.getMainHandStack().isOf(ItemInit.TRITON_TRIDENT))
        {
            TritonTridentEntity tritonTridentEntity = new TritonTridentEntity(this.world, (LivingEntity)this, new ItemStack(ItemInit.TRITON_TRIDENT));
            double d = target.getX() - this.getX();
            double e = target.getBodyY(0.3333333333333333) - tritonTridentEntity.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            tritonTridentEntity.setVelocity(d, e + g * (double)0.2f, f, 1.6f, 14 - this.world.getDifficulty().getId() * 4);
            this.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity(tritonTridentEntity);
        }
        if (this.getMainHandStack().isOf(Items.TRIDENT))
        {
            TridentEntity tridentEntity = new TridentEntity(this.world, (LivingEntity)this, new ItemStack(Items.TRIDENT));
            double d = target.getX() - this.getX();
            double e = target.getBodyY(0.3333333333333333) - tridentEntity.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            tridentEntity.setVelocity(d, e + g * (double)0.2f, f, 1.6f, 14 - this.world.getDifficulty().getId() * 4);
            this.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity(tridentEntity);
        }
    }

    @Override
    public boolean canUsePortals() {
        return true;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.GOBLIN_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.GOBLIN_DEATH_EVENT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.GOBLIN_AMBIENT_EVENT;
    }
}
