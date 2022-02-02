package froztigaming.fantasyorigins.utils;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EffectsUtil {

    public static void giveDolphinGraceEffect(World world, PlayerEntity player)
    {
        if (player.isWet())
        {
            StatusEffectInstance dolphinGrace = new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 8, 0, false, false);
            {
                player.addStatusEffect(dolphinGrace);
            }
        }
    }
}
