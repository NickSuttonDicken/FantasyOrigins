package froztigaming.fantasyorigins.init;

import froztigaming.fantasyorigins.client.render.entity.mob.GoblinEntityRenderer;
import froztigaming.fantasyorigins.client.render.entity.mob.model.GoblinArmorModel;
import froztigaming.fantasyorigins.client.render.entity.mob.model.GoblinEntityModel;
import froztigaming.fantasyorigins.entities.items.SpearEntity;
import froztigaming.fantasyorigins.entities.items.TritonTridentEntity;
import froztigaming.fantasyorigins.entities.mobs.GoblinEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

import java.util.function.Predicate;

import static froztigaming.fantasyorigins.FantasyOrigins.MODID;
import static froztigaming.fantasyorigins.client.FantasyOriginsClient.*;

public class EntityInit {

    public static EntityType<TritonTridentEntity> TRITON_TRIDENT;
    public static EntityType<SpearEntity> SPEAR;

    private static final Predicate<BiomeSelectionContext> goblinSpawnKeys = BiomeSelectors.includeByKey(
            BiomeKeys.DARK_FOREST,
            BiomeKeys.FOREST,
            BiomeKeys.BIRCH_FOREST,
            BiomeKeys.JUNGLE,
            BiomeKeys.WOODED_BADLANDS,
            BiomeKeys.LUSH_CAVES,
            BiomeKeys.SNOWY_TAIGA,
            BiomeKeys.TAIGA,
            BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
            BiomeKeys.OLD_GROWTH_PINE_TAIGA,
            BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA
    );

    public static final EntityType<GoblinEntity> GOBLIN = Registry.register(Registry.ENTITY_TYPE,
            new Identifier(MODID, "goblin"),
            FabricEntityTypeBuilder.createMob()
                    .spawnGroup(SpawnGroup.MONSTER)
                    .entityFactory(GoblinEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75F, 1.75F))
                    .trackRangeBlocks(32).build()
    );

    private static <T extends Entity> EntityType<T> register(String s, EntityType<T> bombEntityType) {
        return Registry.register(Registry.ENTITY_TYPE, MODID + ":" + s, bombEntityType);
    }

    private static <T extends Entity> EntityType<T> createTridentEntityType(EntityType.EntityFactory<T> factory) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.changing(0.5f, 0.5f)).trackRangeBlocks(4).trackedUpdateRate(20).build();
    }

    public static void init() {
        TRITON_TRIDENT = register("triton_trident", createTridentEntityType(TritonTridentEntity::new));
        SPEAR = register("spear", createTridentEntityType(SpearEntity::new));

        FabricDefaultAttributeRegistry.register(GOBLIN, GoblinEntity.createGoblinAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14));

        SpawnGroup spawnGroup = SpawnGroup.MONSTER;

        BiomeModifications.addSpawn(goblinSpawnKeys, spawnGroup, GOBLIN, 8, 2, 4);
        SpawnRestrictionAccessor.callRegister(GOBLIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GoblinEntity::canSpawn);
    }

    @Environment(EnvType.CLIENT)
    public static void initClient()
    {
        EntityRendererRegistry.register(GOBLIN, GoblinEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_LAYER, GoblinEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_ARMOR_OUTER, GoblinArmorModel::createOuterArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_ARMOR_INNER, GoblinArmorModel::createInnerArmorLayer);
    }



}
