package froztigaming.fantasyorigins.mixins;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {
    @Accessor("tridentStack")
    ItemStack fantasyorigins$getTridentStack();

    @Accessor("tridentStack")
    void fantasyorigins$setTridentStack(ItemStack stack);

    @Accessor("dealtDamage")
    boolean fantasyorigins$hasDealtDamage();

    @Accessor("dealtDamage")
    void fantasyorigins$setDealtDamage(boolean dealtDamage);

    @Accessor("LOYALTY")
    static TrackedData<Byte> fantasyorigins$getLoyalty() {
        return null;
    }

    @Accessor("ENCHANTED")
    static TrackedData<Boolean> fantasyorigins$getEnchanted() {
        return null;
    }
}

