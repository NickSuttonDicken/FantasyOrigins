package froztigaming.fantasyorigins.power;

import froztigaming.fantasyorigins.advancement.criterion.FantasyOriginsCriteria;
import froztigaming.fantasyorigins.init.BlockInit;
import froztigaming.fantasyorigins.mixins.AbstractFurnaceBlockEntityInterfaceAccessor;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HalflingCookingPower extends Power {

    private static List<AbstractFurnaceBlockEntity> furnaceBlockEntities = new ArrayList<>();


    public HalflingCookingPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
        setTicking(true);
    }

    @Override
    public void tick() {
        super.tick();
        if (!entity.world.isClient) {
            furnaceBlockEntities = getNearbyFurnaces(Box.of(entity.getPos(), 32, 32, 32), entity);
            if (furnaceBlockEntities.size() > 0)
            {
                PlayerEntity player = (PlayerEntity) entity;
                FantasyOriginsCriteria.HALFLING_COOKING.trigger((ServerPlayerEntity) player);
            }
            for (AbstractFurnaceBlockEntity b : furnaceBlockEntities) {
                ((AbstractFurnaceBlockEntityInterfaceAccessor) b).setCookTimeTotal(AbstractFurnaceBlockEntityInterfaceAccessor.getCookTime(entity.getWorld(), b) / 2);
                if (((AbstractFurnaceBlockEntityInterfaceAccessor) b).getCookTime() > ((AbstractFurnaceBlockEntityInterfaceAccessor) b).getCookTimeTotal())
                {
                    ((AbstractFurnaceBlockEntityInterfaceAccessor) b).setCookTime(0);
                    ((AbstractFurnaceBlockEntityInterfaceAccessor) b).setCookTimeTotal(AbstractFurnaceBlockEntityInterfaceAccessor.getCookTime(entity.getWorld(), b) / 2);

                }
            }
            furnaceBlockEntities.clear();
        }
    }

    private static List<AbstractFurnaceBlockEntity> getNearbyFurnaces(Box box, LivingEntity entity) {
        World world = entity.getWorld();
        for (int x = (int) box.minX; x < box.maxX; x++) {
            for (int y = (int) box.minY; y < box.maxY; y++) {
                for (int z = (int) box.minZ; z < box.maxZ; z++) {
                    BlockEntity block = world.getBlockEntity(new BlockPos(x, y, z));
                    if (block == null) continue;
                    if (furnaceBlockEntities.contains(block)) continue;
                    if (block.getType() != BlockEntityType.SMOKER && block.getType() != BlockEntityType.FURNACE && block.getType() != BlockInit.HALFLING_OVEN_ENTITY)
                        continue;
                    furnaceBlockEntities.add((AbstractFurnaceBlockEntity) block);
                }
            }
        }
        return furnaceBlockEntities;
    }
}