package froztigaming.fantasyorigins.advancement.criterion;


import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class FantasyOriginsCriteria{

    public static final FirstTravelerTeleportCriterion FIRST_TRAVELER_TELEPORT = CriterionRegistry.register(new FirstTravelerTeleportCriterion());
    public static final FirstDimensionTravelCriterion FIRST_DIMENSION_TRAVEL = CriterionRegistry.register(new FirstDimensionTravelCriterion());
    public static final DwarvenSmeltingCriterion DWARVEN_SMELTING = CriterionRegistry.register(new DwarvenSmeltingCriterion());
    public static final HalflingCookingCriterion HALFLING_COOKING = CriterionRegistry.register(new HalflingCookingCriterion());
    public static final StrengthInNumbersCriterion STRENGTH_IN_NUMBERS = CriterionRegistry.register(new StrengthInNumbersCriterion());

    public static void init() {

    }
}
