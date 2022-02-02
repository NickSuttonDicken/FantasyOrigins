package froztigaming.fantasyorigins.mixins;

import froztigaming.fantasyorigins.init.PowersInit;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public class AlphaMixin {
    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/WolfEntity;setOwner(Lnet/minecraft/entity/player/PlayerEntity;)V", shift = At.Shift.AFTER))
    public void interactMob$MobOrigins(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (PowersInit.ALPHA.isActive(player)) {
            ((WolfEntity)(Object)this).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier("Wolfkind Origin Bonus", 10.0, EntityAttributeModifier.Operation.ADDITION));
            ((WolfEntity)(Object)this).getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addPersistentModifier(new EntityAttributeModifier("Wolfkind Origin Bonus", 2.0, EntityAttributeModifier.Operation.ADDITION));
        }
    }
}
