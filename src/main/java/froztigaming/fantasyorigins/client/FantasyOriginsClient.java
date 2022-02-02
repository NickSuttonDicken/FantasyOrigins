package froztigaming.fantasyorigins.client;

import froztigaming.fantasyorigins.FantasyOrigins;
import froztigaming.fantasyorigins.client.render.entity.SpearEntityRenderer;
import froztigaming.fantasyorigins.client.render.entity.TritonTridentEntityRenderer;
import froztigaming.fantasyorigins.client.render.entity.model.SpearEntityModel;
import froztigaming.fantasyorigins.client.render.item.SpearItemRenderer;
import froztigaming.fantasyorigins.client.render.item.TritonTridentItemRenderer;
import froztigaming.fantasyorigins.init.EntityInit;
import froztigaming.fantasyorigins.init.ItemInit;
import froztigaming.fantasyorigins.items.SpearItem;
import froztigaming.fantasyorigins.items.TravelerStone;
import froztigaming.fantasyorigins.items.TritonTridentItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static froztigaming.fantasyorigins.FantasyOrigins.MODID;

@Environment(EnvType.CLIENT)
public class FantasyOriginsClient implements ClientModInitializer {

    public static final EntityModelLayer SPEAR = new EntityModelLayer(new Identifier(FantasyOrigins.MODID, "spear"), "spear");

    public static final EntityModelLayer GOBLIN_LAYER = new EntityModelLayer(new Identifier( MODID, "goblin"), "goblin");
    public static final EntityModelLayer GOBLIN_ARMOR_OUTER = new EntityModelLayer(new Identifier(MODID, "goblin_armor_outer"), "guard_armor_outer");
    public static final EntityModelLayer GOBLIN_ARMOR_INNER = new EntityModelLayer(new Identifier(MODID, "goblin_armor_inner"), "guard_armor_inner");

    @Override
    public void onInitializeClient() {


       EntityInit.initClient();
       initialiseTritonTrident();
       initialiseSpear();


        //Model Predicate Registry
        FabricModelPredicateProviderRegistry.register(ItemInit.TRAVELER_STONE, new Identifier("charged"), (itemStack, world, entity, seed) -> {
            return entity != null && TravelerStone.isDimension() ? 1.0F : 0.0F;
        });

        FabricModelPredicateProviderRegistry.register(ItemInit.ELVEN_BOW, new Identifier("pull"), (itemStack, world, entity, seed) -> {
            if (entity == null)
            {
                return 0.0F;
            }
            return entity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.F;
        });

        FabricModelPredicateProviderRegistry.register(ItemInit.ELVEN_BOW, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
    }

    public void initialiseTritonTrident()
    {
        TritonTridentItem item = ItemInit.TRITON_TRIDENT;
        Identifier tridentId = Registry.ITEM.getId(item);
        Identifier texture = new Identifier(tridentId.getNamespace(), "textures/entity/" + tridentId.getPath() + ".png");

        EntityModelLayer modelLayer = EntityModelLayers.TRIDENT;
        TritonTridentItemRenderer tridentItemRenderer = new TritonTridentItemRenderer(tridentId, texture, modelLayer);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(tridentItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(item, tridentItemRenderer);
        EntityRendererRegistry.register(item.getEntityType(), ctx -> new TritonTridentEntityRenderer(ctx, texture, modelLayer));

        FabricModelPredicateProviderRegistry.register(item, new Identifier("throwing"), ((stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier(tridentId + "_in_inventory", "inventory")));
    }

    public void initialiseSpear()
    {
        EntityModelLayerRegistry.registerModelLayer(SPEAR, SpearEntityModel::getTexturedModelData);

        SpearItem item = ItemInit.SPEAR;
        Identifier spearId = Registry.ITEM.getId(item);
        Identifier texture = new Identifier(spearId.getNamespace(), "textures/entity/" + spearId.getPath() + ".png");

        EntityModelLayer modelLayer = SPEAR;
        SpearItemRenderer spearItemRenderer = new SpearItemRenderer(spearId, texture, modelLayer);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(spearItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(item, spearItemRenderer);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(item.getEntityType(), ctx -> new SpearEntityRenderer(ctx, texture, modelLayer));

        FabricModelPredicateProviderRegistry.register(item, new Identifier("throwing"), (((stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F)));
        ModelLoadingRegistry.INSTANCE.registerModelProvider(((manager, out) -> out.accept(new ModelIdentifier(spearId + "_in_inventory", "inventory"))));
    }
}
