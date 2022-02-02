package froztigaming.fantasyorigins;

import froztigaming.fantasyorigins.advancement.criterion.FantasyOriginsCriteria;
import froztigaming.fantasyorigins.init.*;
import froztigaming.fantasyorigins.power.conditions.CustomPlayerConditions;
import froztigaming.fantasyorigins.init.PowersInit;
import net.fabricmc.api.ModInitializer;

public class FantasyOrigins implements ModInitializer {
    public static final String MODID = "fantasyorigins";

    @Override
    public void onInitialize() {

        //OreGen.init();
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
