package froztigaming.fantasyorigins.entities.blocks;

import froztigaming.fantasyorigins.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SmokerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class HalflingOvenEntity extends AbstractFurnaceBlockEntity {

    public HalflingOvenEntity(BlockPos pos, BlockState state){
        super(BlockInit.HALFLING_OVEN_ENTITY, pos, state, RecipeType.SMOKING);
    }
    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.halfling_oven");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new SmokerScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) / 4;
    }
}
