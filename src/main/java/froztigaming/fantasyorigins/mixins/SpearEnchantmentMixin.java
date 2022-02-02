package froztigaming.fantasyorigins.mixins;

import froztigaming.fantasyorigins.items.SpearItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class SpearEnchantmentMixin {
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void isAcceptableItemMixin(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if ((Object) this == Enchantments.RIPTIDE && stack.getItem() instanceof SpearItem) {
            info.setReturnValue(false);
        }
        if ((Object) this == Enchantments.LOOTING & stack.getItem() instanceof SpearItem) {
            info.setReturnValue(true);
        }
    }
}
