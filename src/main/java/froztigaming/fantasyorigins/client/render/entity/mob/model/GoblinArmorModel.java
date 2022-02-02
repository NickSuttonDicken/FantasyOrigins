package froztigaming.fantasyorigins.client.render.entity.mob.model;

import froztigaming.fantasyorigins.entities.mobs.GoblinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.client.render.entity.model.ArmorStandEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class GoblinArmorModel extends BipedEntityModel<GoblinEntity> {

    public GoblinArmorModel(ModelPart part) {
        super(part);
    }

    public static TexturedModelData createOuterArmorLayer() {
        ModelData modelData = GoblinEntityModel.getModelData(new Dilation(1.0F), 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        //Head
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.0f, -5.0f, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        //Body
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 2.0F, -3.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        //Arms
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-2F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-2.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.NONE);
        //Legs
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().mirrored().uv(0, 16).cuboid(0.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }

    public static TexturedModelData createInnerArmorLayer() {
        ModelData modelData = GoblinEntityModel.getModelData(new Dilation(0.5F), 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        //Body
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 2.0F, -3.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        //Legs
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().mirrored().uv(0, 16).cuboid(0.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }
}
