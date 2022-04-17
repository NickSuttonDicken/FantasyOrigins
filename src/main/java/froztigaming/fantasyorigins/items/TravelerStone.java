package froztigaming.fantasyorigins.items;

import froztigaming.fantasyorigins.utils.DimensionTravelUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TravelerStone extends Item {
    public TravelerStone(Settings settings) {
        super(settings);
    }

    private static int option = 1;

    public static boolean isDimension() {
        return dimension;
    }

    private static boolean dimension = true;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);

        if (user.isSneaking() && !user.world.isClient) {
            dimension = !dimension;
            option = dimension ? 1 : 2;

            switch (option) {
                case 1 -> {
                    user.sendSystemMessage(Text.of("Nether Dimension Selected"), user.getUuid());
                }
                case 2 -> {
                    user.sendSystemMessage(Text.of("End Dimension Selected"), user.getUuid());
                }
            }
        } else {
            for (int i = 0; i < 50; ++i) {
                user.world.addParticle(ParticleTypes.SMOKE, true, user.getParticleX(0.5D), user.getRandomBodyY() - 0.5D, user.getParticleZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
            if (!user.world.isClient)
            {
                DimensionTravelUtil.travel(user, option);
                stack.damage(1, user, (p) -> {
                    p.sendToolBreakStatus(hand);
                });
                user.getItemCooldownManager().set(this, 200);
            }
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, stack);
    }
}
