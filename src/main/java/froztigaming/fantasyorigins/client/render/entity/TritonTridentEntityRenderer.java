package froztigaming.fantasyorigins.client.render.entity;

import froztigaming.fantasyorigins.client.render.entity.model.TritonTridentEntityModel;
import froztigaming.fantasyorigins.entities.items.TritonTridentEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class TritonTridentEntityRenderer extends EntityRenderer<TritonTridentEntity> {
private final TritonTridentEntityModel model;
private final Identifier texture;

public TritonTridentEntityRenderer(EntityRendererFactory.Context context, Identifier texture, EntityModelLayer modelLayer) {
        super(context);
        this.model = new TritonTridentEntityModel(context.getPart(modelLayer));
        this.texture = texture;
        }

public void render(TritonTridentEntity tritonTridentEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, tritonTridentEntity.prevYaw, tritonTridentEntity.getYaw()) - 90.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, tritonTridentEntity.prevPitch, tritonTridentEntity.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(this.getTexture(tritonTridentEntity)), false, tritonTridentEntity.isEnchanted());
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(tritonTridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
        }

public Identifier getTexture(TritonTridentEntity tritonTridentEntity) {
        return this.texture;
        }
}
