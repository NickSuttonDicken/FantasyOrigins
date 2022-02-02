package froztigaming.fantasyorigins.client.render.entity.mob;

import froztigaming.fantasyorigins.FantasyOrigins;
import froztigaming.fantasyorigins.client.FantasyOriginsClient;
import froztigaming.fantasyorigins.client.render.entity.mob.model.GoblinArmorModel;
import froztigaming.fantasyorigins.client.render.entity.mob.model.GoblinEntityModel;
import froztigaming.fantasyorigins.entities.mobs.GoblinEntity;
import froztigaming.fantasyorigins.init.EntityInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GoblinEntityRenderer extends MobEntityRenderer<GoblinEntity, GoblinEntityModel<GoblinEntity>> {
    private static final Identifier TEXTURE = new Identifier(FantasyOrigins.MODID, "textures/entity/goblin/goblin.png");

    public GoblinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinEntityModel(context.getPart(FantasyOriginsClient.GOBLIN_LAYER)), 0.4F);
        this.addFeature(new HeldItemFeatureRenderer(this));
        this.addFeature(new ArmorFeatureRenderer<>(this, new GoblinArmorModel(context.getPart(FantasyOriginsClient.GOBLIN_ARMOR_INNER)), new GoblinArmorModel(context.getPart(FantasyOriginsClient.GOBLIN_ARMOR_OUTER))));
    }

    @Override
    public Identifier getTexture(GoblinEntity entity) {
        return TEXTURE;
    }

}
