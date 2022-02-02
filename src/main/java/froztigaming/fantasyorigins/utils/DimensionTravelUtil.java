package froztigaming.fantasyorigins.utils;

import froztigaming.fantasyorigins.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.SkullItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class DimensionTravelUtil {

    public static void travel(LivingEntity entity, int option) {

        if (!entity.world.isClient) {

            entity.setVelocity(Vec3d.ZERO);
            MinecraftServer server = entity.world.getServer();

            if (server == null)
                return;

            ServerWorld serverWorld = null;

            switch (option) {
                case 1:
                    serverWorld = server.getWorld(entity.world.getRegistryKey() == World.OVERWORLD ? World.NETHER : World.OVERWORLD);
                    break;
                case 2:
                    serverWorld = server.getWorld(entity.world.getRegistryKey() == World.OVERWORLD ? World.END : World.OVERWORLD);
                    break;
            }

            if (serverWorld != null && server.isNetherAllowed()) {

                double movementFactor = entity.world.getRegistryKey() == World.OVERWORLD ? 0.125d : 8;
                BlockPos pos = createDestinationSpawn(new BlockPos(entity.getPos().getX() * movementFactor, entity.getPos().getY(), entity.getPos().getZ() * movementFactor), serverWorld);
                entity.world.playSound(null, entity.getBlockPos(), SoundInit.DIMENSION_TRAVEL_EVENT, SoundCategory.NEUTRAL, 0.75F, 1);
                if (entity instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) entity).teleport(serverWorld, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, entity.getYaw(), entity.getPitch());
                } else {
                    server.getWorld(entity.world.getRegistryKey()).removePlayer((ServerPlayerEntity) entity, Entity.RemovalReason.CHANGED_DIMENSION);
                    entity.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);
                    entity.refreshPositionAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, entity.getYaw(), entity.getPitch());
                    entity.moveToWorld(serverWorld);
                    serverWorld.spawnEntity(entity);
                }
                serverWorld.playSound(null, pos, SoundInit.DIMENSION_TRAVEL_EVENT, SoundCategory.NEUTRAL, 0.75F, 1);
            }
        }
    }

    private static BlockPos createDestinationSpawn(BlockPos posIn, World serverWorld) {
        double bestDistance = Double.MAX_VALUE;
        int posX = MathHelper.floor(posIn.getX());
        int posY = MathHelper.floor(posIn.getY());
        int posZ = MathHelper.floor(posIn.getZ());
        int bestX = posX;
        int bestY = posY;
        int bestZ = posZ;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int xIndex = posX - 16; xIndex <= posX + 16; ++xIndex) {
            double xDistance = (double) xIndex + 0.5D - posX;

            for (int zIndex = posZ - 16; zIndex <= posZ + 16; ++zIndex) {
                double zDistance = (double) zIndex + 0.5D - posZ;

                for (int yIndex = 128 - 1; yIndex >= 0; --yIndex) {
                    if (serverWorld.isAir(mutable.set(xIndex, yIndex, zIndex))) {
                        while (yIndex > 0 && serverWorld.isAir(mutable.set(xIndex, yIndex - 1, zIndex))) {
                            --yIndex;
                        }
                        if (!serverWorld.getBlockState(mutable.set(xIndex, yIndex - 1, zIndex)).isOpaque())
                            continue;
                        if (!serverWorld.isAir(mutable.set(xIndex, yIndex + 1, zIndex)))
                            continue;

                        double yDistance = (double) yIndex + 0.5D - posY;
                        double distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2) + Math.pow(zDistance, 2));
                        if (distance < bestDistance) {
                            bestDistance = distance;
                            bestX = xIndex;
                            bestY = yIndex;
                            bestZ = zIndex;
                        }
                    }
                }
            }
        }
        if (bestDistance == Double.MAX_VALUE) {
            for (int xIndex = -1; xIndex <= 1; xIndex++) {
                for (int zIndex = -1; zIndex <= 1; zIndex++) {
                    BlockState bs = serverWorld.getBlockState(mutable.set(posX + xIndex, posY - 1, posZ + zIndex));
                    if (!bs.isOpaque() && bs.getBlock() != Blocks.BEDROCK) {
                        serverWorld.setBlockState(mutable, Blocks.END_STONE.getDefaultState());
                    }
                }
            }
            for (int yIndex = 0; yIndex <= 1; yIndex++) {
                for (int xIndex = -1; xIndex <= 1; xIndex++) {
                    for (int zIndex = -1; zIndex <= 1; zIndex++) {
                        BlockState bs = serverWorld.getBlockState(mutable.set(posX + xIndex, posY + yIndex, posZ + zIndex));
                        if (bs.getBlock() != Blocks.BEDROCK) {
                            serverWorld.setBlockState(mutable, Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }
        return mutable.set(bestX, bestY, bestZ);
    }
}
