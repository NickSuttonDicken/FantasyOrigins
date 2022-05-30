package froztigaming.fantasyorigins.config;


import froztigaming.fantasyorigins.FantasyOrigins;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = FantasyOrigins.MODID)
@Config.Gui.Background("minecraft:textures/block/beacon.png")
public class FantasyOriginsConfig implements ConfigData {

    @Comment("Enables the Goblin mob, Default: True")
    public boolean goblinsEnable = true;
}
