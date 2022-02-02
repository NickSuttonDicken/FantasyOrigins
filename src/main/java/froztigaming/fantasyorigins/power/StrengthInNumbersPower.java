package froztigaming.fantasyorigins.power;

import com.terraformersmc.modmenu.util.mod.Mod;
import froztigaming.fantasyorigins.FantasyOrigins;
import froztigaming.fantasyorigins.advancement.criterion.FantasyOriginsCriteria;
import froztigaming.fantasyorigins.entities.mobs.GoblinEntity;
import froztigaming.fantasyorigins.init.EntityInit;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.origins.Origins;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.component.PlayerOriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.registry.ModComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StrengthInNumbersPower extends Power {

    private static List<GoblinEntity> goblinEntities = new ArrayList<>();
    private static List<PlayerEntity> goblinPlayers = new ArrayList<>();

    public StrengthInNumbersPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
        setTicking(true );
    }

    @Override
    public void tick() {
        super.tick();
        if (!entity.world.isClient){
            goblinPlayers = getNearbyPlayerGoblins(Box.of(entity.getPos(), 16, 16, 16), entity);
            goblinEntities = getNearbyGoblins(Box.of(entity.getPos(), 16, 16, 16), entity);
            if (goblinEntities.size() > 0 || goblinPlayers.size() > 0) {
                StatusEffectInstance groupStrength = new StatusEffectInstance(StatusEffects.STRENGTH, 40, 0, false, false);
                {
                    entity.addStatusEffect(groupStrength);
                }
                PlayerEntity player = (PlayerEntity) entity;
                FantasyOriginsCriteria.STRENGTH_IN_NUMBERS.trigger((ServerPlayerEntity) player);
            }
            if (goblinEntities.size() > 0)
            {
                goblinEntities.clear();
            }
            if (goblinPlayers.size() > 0)
            {
                goblinPlayers.clear();
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

    private static List<PlayerEntity> getNearbyPlayerGoblins(Box box, LivingEntity entity)
    {
        World world = entity.getWorld();
        for (int x = (int) box.minX; x < box.maxX; x++) {
            for (int y = (int) box.minY; y < box.maxY; y++) {
                for (int z = (int) box.minZ; z < box.maxZ; z++) {
                    List<Entity> nearbyEntities = world.getOtherEntities(entity, box);
                    for (Entity e: nearbyEntities) {
                        if (e == null) continue;
                        if (goblinPlayers.contains(e)) continue;
                        if (e.getType() != EntityType.PLAYER) continue;
                        OriginComponent component = ModComponents.ORIGIN.get(entity);
                        OriginComponent component1 = ModComponents.ORIGIN.get(e);
                        if (component1.getOrigins().equals(component.getOrigins()))
                        {
                            goblinPlayers.add((PlayerEntity) e);
                        }
                    }
                }
            }
        }
        return goblinPlayers;
    }


}
