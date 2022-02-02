package froztigaming.fantasyorigins.init;

import froztigaming.fantasyorigins.FantasyOrigins;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static froztigaming.fantasyorigins.FantasyOrigins.MODID;

public class SoundInit {

    public static final Identifier TRAVELER_TELEPORT = new Identifier(MODID, "traveler_teleport");
    public static final Identifier DIMENSION_TRAVEL = new Identifier(MODID, "dimension_travel");
    public static final Identifier NETHER_PORTAL_BOTTLE = new Identifier(MODID, "nether_portal_bottle");
    public static final Identifier END_PORTAL_BOTTLE = new Identifier(MODID, "end_portal_bottle");
    public static final Identifier GOBLIN_HURT = new Identifier(MODID, "goblin_hurt");
    public static final Identifier GOBLIN_DEATH = new Identifier(MODID, "goblin_death");
    public static final Identifier GOBLIN_AMBIENT = new Identifier(MODID, "goblin_ambient");

    public static SoundEvent TRAVELER_TELEPORT_EVENT = new SoundEvent(TRAVELER_TELEPORT);
    public static SoundEvent DIMENSION_TRAVEL_EVENT = new SoundEvent(DIMENSION_TRAVEL);
    public static SoundEvent NETHER_PORTAL_BOTTLE_EVENT = new SoundEvent(NETHER_PORTAL_BOTTLE);
    public static SoundEvent END_PORTAL_BOTTLE_EVENT = new SoundEvent(END_PORTAL_BOTTLE);
    public static SoundEvent GOBLIN_HURT_EVENT = new SoundEvent(GOBLIN_HURT);
    public static SoundEvent GOBLIN_DEATH_EVENT = new SoundEvent(GOBLIN_DEATH);
    public static SoundEvent GOBLIN_AMBIENT_EVENT = new SoundEvent(GOBLIN_AMBIENT);

    public static void registerSounds()
    {
        Registry.register(Registry.SOUND_EVENT, TRAVELER_TELEPORT, TRAVELER_TELEPORT_EVENT);
        Registry.register(Registry.SOUND_EVENT, DIMENSION_TRAVEL, DIMENSION_TRAVEL_EVENT);
        Registry.register(Registry.SOUND_EVENT, NETHER_PORTAL_BOTTLE, NETHER_PORTAL_BOTTLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, END_PORTAL_BOTTLE, END_PORTAL_BOTTLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, GOBLIN_HURT, GOBLIN_HURT_EVENT);
        Registry.register(Registry.SOUND_EVENT, GOBLIN_DEATH, GOBLIN_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, GOBLIN_AMBIENT, GOBLIN_AMBIENT_EVENT);
    }
}
