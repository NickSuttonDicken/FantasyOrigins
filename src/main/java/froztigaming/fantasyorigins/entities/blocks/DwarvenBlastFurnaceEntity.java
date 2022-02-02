package froztigaming.fantasyorigins.entities.blocks;

import froztigaming.fantasyorigins.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class DwarvenBlastFurnaceEntity extends AbstractFurnaceBlockEntity {

    public DwarvenBlastFurnaceEntity(BlockPos pos, BlockState state){
        super(BlockInit.DWARVEN_BLAST_FURNACE_ENTITY, pos, state, RecipeType.BLASTING);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.dwarven_blast_furnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new BlastFurnaceScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) / 4;
    }
}
