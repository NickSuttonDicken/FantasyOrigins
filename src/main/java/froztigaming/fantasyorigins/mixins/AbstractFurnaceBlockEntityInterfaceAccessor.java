package froztigaming.fantasyorigins.mixins;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeManager.MatchGetter;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityInterfaceAccessor {


    @Invoker("getCookTime")
    static int getCookTime(World world, @NotNull AbstractFurnaceBlockEntity furnace) {
        int cookTime = furnace.matchGetter.getFirstMatch(furnace, world).map(AbstractCookingRecipe::getCookTime).orElse(50);
        return (cookTime / 2);
    }

    @Accessor("matchGetter")
    MatchGetter<Inventory, ? extends AbstractCookingRecipe> getMatch();

    @Accessor("cookTime")
    int getCookTime();

    @Accessor("cookTimeTotal")
    void setCookTimeTotal(int cookTimeTotal);

    @Accessor("cookTimeTotal")
    int getCookTimeTotal();

    @Accessor("cookTime")
    void setCookTime(int cookTime);
}
