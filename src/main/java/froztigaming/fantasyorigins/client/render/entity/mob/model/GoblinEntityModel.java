package froztigaming.fantasyorigins.client.render.entity.mob.model;

import com.google.common.collect.ImmutableList;
import com.terraformersmc.modmenu.util.mod.Mod;
import froztigaming.fantasyorigins.entities.mobs.GoblinEntity;
import froztigaming.fantasyorigins.init.ItemInit;
import net.minecraft.block.CandleBlock;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

public class GoblinEntityModel<T extends MobEntity> extends BipedEntityModel<T>{
    public final ModelPart rightEar;
    public final ModelPart leftEar;
    public final ModelPart nose;
    public final ModelPart head;
    public final ModelPart hat;
    public final ModelPart body;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

    public GoblinEntityModel(ModelPart root) {
        super(root);
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.hat = root.getChild(EntityModelPartNames.HAT);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
        this.leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightEar = head.getChild(EntityModelPartNames.RIGHT_EAR);
        this.leftEar = head.getChild(EntityModelPartNames.LEFT_EAR);
        this.nose = head.getChild(EntityModelPartNames.NOSE);
    }

    @Override
    public void animateModel(T goblinEntity, float f, float g, float h) {
        this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
        this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
        ItemStack itemStack = ((LivingEntity) goblinEntity).getStackInHand(Hand.MAIN_HAND);
        if ((itemStack.isOf(Items.TRIDENT) || itemStack.isOf(ItemInit.SPEAR) || itemStack.isOf(ItemInit.TRITON_TRIDENT)) && ((MobEntity) goblinEntity).isAttacking()) {
            if (((MobEntity) goblinEntity).getMainArm() == Arm.RIGHT) {
                this.rightArmPose = BipedEntityModel.ArmPose.THROW_SPEAR;
            } else {
                this.leftArmPose = BipedEntityModel.ArmPose.THROW_SPEAR;
            }
        }
        super.animateModel(goblinEntity, f, g, h);
    }

    public static TexturedModelData getTexturedModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        //Head
        ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.5F)), ModelTransform.NONE);
        //Hat
        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.25F)), ModelTransform.NONE);
        //Nose
        modelPartData2.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(56, 16).cuboid(-1.0F, -1.0F, -5.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.NONE);
        //Ears
        modelPartData2.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(56, 0).cuboid(-2.0F, 20.0F, 0.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -22.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        modelPartData2.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(56, 0).mirrored().cuboid(2.0F, 20.0F, 0.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -22.0F, 0.0F,0.0F, 0.7854F, 0.0F));
        //Body
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 2.0F, -3.0F, 8.0F, 12.0F, 4.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        //Arms
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(32, 48).cuboid(-2F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.5F)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-2.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.5F)), ModelTransform.NONE);
        //Legs
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.5F)), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().mirrored().uv(16, 48).cuboid(0.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(-0.5F)), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg, this.hat);
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }


}
