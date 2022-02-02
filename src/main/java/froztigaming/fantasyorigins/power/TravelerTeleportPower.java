package froztigaming.fantasyorigins.power;

import com.mojang.datafixers.kinds.IdF;
import froztigaming.fantasyorigins.advancement.criterion.FantasyOriginsCriteria;
import froztigaming.fantasyorigins.init.SoundInit;
import froztigaming.fantasyorigins.init.StatInit;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import static net.minecraft.util.hit.HitResult.*;
import static net.minecraft.util.hit.HitResult.Type.*;

public class TravelerTeleportPower extends Power implements Active {
    private Key key;

    public TravelerTeleportPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }


    @Override
    public void onUse() {


        for (int i = 0;i < 50; ++i)
        {
            entity.world.addParticle(ParticleTypes.SMOKE, true, entity.getParticleX(0.5D), entity.getRandomBodyY() - 0.5D, entity.getParticleZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
        if (!entity.world.isClient && !entity.isWet()) {

            HitResult hit = raycast(entity, 40F, true);

            switch (hit.getType()) {
                case BLOCK:
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    BlockPos blockPos = blockHit.getBlockPos();
                    BlockPos.Mutable mutable = new BlockPos.Mutable();
                    if (entity.world.isAir(mutable.set(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ())))
                    {
                        entity.teleport(blockPos.getX() + 0.5f, blockPos.getY() + 1, blockPos.getZ() + 0.5F);
                        entity.world.playSound(null, entity.getBlockPos(), SoundInit.TRAVELER_TELEPORT_EVENT, SoundCategory.NEUTRAL, 0.5F, 1);
                        PlayerEntity player = (PlayerEntity) entity;
                        FantasyOriginsCriteria.FIRST_TRAVELER_TELEPORT.trigger((ServerPlayerEntity) player);
                        player.incrementStat(StatInit.USE_TRAVELER_TELEPORT);
                    }
                    break;
                case ENTITY:
                    EntityHitResult entityHit = (EntityHitResult) hit;
                    double entityX = entityHit.getEntity().getX();
                    double entityY = entityHit.getEntity().getY();
                    double entityZ = entityHit.getEntity().getZ();
                    entity.teleport(entityX, entityY, entityZ);
                    entity.world.playSound(null, entity.getBlockPos(), SoundInit.TRAVELER_TELEPORT_EVENT, SoundCategory.NEUTRAL, 0.5F, 1);
                    PlayerEntity player = (PlayerEntity) entity;
                    FantasyOriginsCriteria.FIRST_TRAVELER_TELEPORT.trigger((ServerPlayerEntity) player);
                    player.incrementStat(StatInit.USE_TRAVELER_TELEPORT);
                    break;
            }
        }
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }

    //Raycasting Code
    public static HitResult raycast(Entity origin, double maxDistance, boolean hitsEntities) {
        Vec3d startPos = origin.getCameraPosVec(1F);
        Vec3d rotation = origin.getRotationVec(1F);
        Vec3d endPos = startPos.add(rotation.x * maxDistance, rotation.y * maxDistance, rotation.z * maxDistance);
        HitResult hitResult = origin.world.raycast(new RaycastContext(startPos, endPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, origin));

        if (hitResult.getType() != MISS)
            endPos = hitResult.getPos();

        maxDistance *= 10;
        HitResult entityHitResult = ProjectileUtil.raycast(origin, startPos, endPos, origin.getBoundingBox().stretch(rotation.multiply(maxDistance)).expand(1.0D, 1.0D, 1.0D), entity1 -> !entity1.isSpectator(), maxDistance);

        if (hitsEntities && entityHitResult != null)
            hitResult = entityHitResult;

        return hitResult;
    }

}
