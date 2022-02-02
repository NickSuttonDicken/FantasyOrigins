package froztigaming.fantasyorigins.mixins;

import froztigaming.fantasyorigins.advancement.criterion.FantasyOriginsCriteria;
import froztigaming.fantasyorigins.init.ItemInit;
import froztigaming.fantasyorigins.init.SoundInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlassBottleItem.class)
public abstract class GlassBottleItemMixin {

    @Shadow protected abstract ItemStack fill(ItemStack stack, PlayerEntity player, ItemStack outputStack);

    @Inject(
            method = "use",
            at = @At(value = "RETURN"), cancellable = true)
    public void setItemStack(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir)
    {
        ItemStack itemStack = user.getStackInHand(hand);
        HitResult hit = ItemInvoker.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);

        assert hit != null;
        if (hit.getType() == HitResult.Type.BLOCK)
        {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos blockPos = blockHit.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();

            if (block == Blocks.NETHER_PORTAL)
            {
                world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundInit.NETHER_PORTAL_BOTTLE_EVENT, SoundCategory.NEUTRAL, 2F, 1.0F);
                world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
                cir.setReturnValue(TypedActionResult.success(fill(itemStack, user, new ItemStack(ItemInit.NETHER_PORTAL_BOTTLE)), world.isClient()));
            }

            if (block == Blocks.END_PORTAL) {
                world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundInit.END_PORTAL_BOTTLE_EVENT, SoundCategory.NEUTRAL, 2F, 1.0F);
                world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
                cir.setReturnValue(TypedActionResult.success(fill(itemStack, user, new ItemStack(ItemInit.END_PORTAL_BOTTLE)), world.isClient()));
            }
        }
    }
}
