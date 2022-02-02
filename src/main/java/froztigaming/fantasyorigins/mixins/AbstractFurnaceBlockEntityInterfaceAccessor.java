package froztigaming.fantasyorigins.mixins;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityInterfaceAccessor {



    @Invoker("getCookTime")
    static int getCookTime(World world, RecipeType<? extends AbstractCookingRecipe> recipeType, Inventory inventory) {
        int cookTime = world.getRecipeManager().getFirstMatch(recipeType, inventory, world).map(AbstractCookingRecipe::getCookTime).orElse(50);
        return (int)(cookTime / 2);
    }



    @Accessor("recipeType")
    RecipeType<? extends AbstractCookingRecipe> getRecipe();


    @Accessor("cookTime")
    public int getCookTime();

    @Accessor("cookTimeTotal")
    public void setCookTimeTotal(int cookTimeTotal);

    @Accessor("cookTimeTotal")
    public int getCookTimeTotal();

    @Accessor("cookTime")
    public void setCookTime(int cookTime);


}
