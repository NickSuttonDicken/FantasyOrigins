package froztigaming.fantasyorigins;

import froztigaming.fantasyorigins.advancement.criterion.FantasyOriginsCriteria;
import froztigaming.fantasyorigins.config.FantasyOriginsConfig;
import froztigaming.fantasyorigins.init.*;
import froztigaming.fantasyorigins.power.conditions.CustomPlayerConditions;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class FantasyOrigins implements ModInitializer {
    public static final String MODID = "fantasyorigins";

    public static FantasyOriginsConfig CONFIG;

    @Override
    public void onInitialize() {

        //OreGen.init();
        AutoConfig.register(FantasyOriginsConfig.class, GsonConfigSerializer::new);
        var holder = AutoConfig.getConfigHolder(FantasyOriginsConfig.class);
        CONFIG = holder.getConfig();

        CustomPlayerConditions.register();
        PowersInit.init();
        SoundInit.registerSounds();
        StatInit.registerStats();
        FantasyOriginsCriteria.init();
        EntityInit.init();
        ItemInit.registerItems();
        BlockInit.registerBlockEntities();
        BlockInit.registerBlocks();
        BlockInit.registerBlockItems();

    }


}
