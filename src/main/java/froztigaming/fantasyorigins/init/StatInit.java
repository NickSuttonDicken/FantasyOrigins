package froztigaming.fantasyorigins.init;

import froztigaming.fantasyorigins.FantasyOrigins;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StatInit {

    public static final Identifier USE_TRAVELER_TELEPORT = new Identifier(FantasyOrigins.MODID, "use_traveler_teleport");
    public static final Identifier USE_DIMENSION_TRAVEL = new Identifier(FantasyOrigins.MODID, "use_dimension_travel");


    public static void registerStats()
    {
        Registry.register(Registry.CUSTOM_STAT, "use_traveler_teleport", USE_TRAVELER_TELEPORT);
        Registry.register(Registry.CUSTOM_STAT, "use_dimension_travel", USE_DIMENSION_TRAVEL);

        Stats.CUSTOM.getOrCreateStat(USE_TRAVELER_TELEPORT, StatFormatter.DEFAULT);
        Stats.CUSTOM.getOrCreateStat(USE_DIMENSION_TRAVEL, StatFormatter.DEFAULT);
    }
}
