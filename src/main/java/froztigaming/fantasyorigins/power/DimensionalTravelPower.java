package froztigaming.fantasyorigins.power;

import com.mojang.serialization.Dynamic;
import froztigaming.fantasyorigins.advancement.criterion.FantasyOriginsCriteria;
import froztigaming.fantasyorigins.advancement.criterion.FirstDimensionTravelCriterion;
import froztigaming.fantasyorigins.init.SoundInit;
import froztigaming.fantasyorigins.init.StatInit;
import froztigaming.fantasyorigins.utils.DimensionTravelUtil;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancement.Advancement;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.AreaHelper;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;
import virtuoel.pehkui.api.PehkuiConfig;

import java.util.Objects;
import java.util.Optional;

public class DimensionalTravelPower extends Power implements Active {
    private Key key;
    private static int option = 1;
    private static boolean dimension = true;

    public DimensionalTravelPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    @Override
    public void onUse() {

        for (int i = 0; i < 50; ++i) {
            entity.world.addParticle(ParticleTypes.SMOKE, true, entity.getParticleX(0.5D), entity.getRandomBodyY() - 0.5D, entity.getParticleZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
        if (!entity.world.isClient && !entity.isWet())
        {
            if (entity.isSneaking()) {
                dimension = !dimension;
                option = dimension ? 1 : 2;

                switch (option) {
                    case 1 -> {
                        entity.sendMessage(Text.of("Nether Dimension Selected"));
                    }
                    case 2 -> {
                        entity.sendMessage(Text.of("End Dimension Selected"));

                    }
                }
            } else {
                DimensionTravelUtil.travel(entity, option);
                PlayerEntity player = (PlayerEntity) entity;
                FantasyOriginsCriteria.FIRST_DIMENSION_TRAVEL.trigger((ServerPlayerEntity) player);
                player.incrementStat(StatInit.USE_DIMENSION_TRAVEL);
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
}
