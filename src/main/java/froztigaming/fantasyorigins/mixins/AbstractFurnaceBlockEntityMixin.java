package froztigaming.fantasyorigins.mixins;

import froztigaming.fantasyorigins.entities.blocks.DwarvenBlastFurnaceEntity;
import froztigaming.fantasyorigins.entities.blocks.HalflingOvenEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin extends BlockEntity {

    @Unique
    @Nullable
    private static AbstractFurnaceBlockEntity fo_entityContext = null;

    private AbstractFurnaceBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD"))
    private static void resetCraftingContext(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        fo_entityContext = blockEntity;
    }

    @Inject(
            method = "getCookTime",
            at = @At("RETURN"), cancellable = true)
    private static void modifyCookTime(World world, AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Integer> cir) {

        if(fo_entityContext instanceof DwarvenBlastFurnaceEntity || fo_entityContext instanceof HalflingOvenEntity) {
            cir.setReturnValue(50);
        }
    }
}
