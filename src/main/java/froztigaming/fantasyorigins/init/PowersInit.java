package froztigaming.fantasyorigins.init;

import froztigaming.fantasyorigins.power.*;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static froztigaming.fantasyorigins.FantasyOrigins.MODID;

public class PowersInit {

    public static final PowerType<Power> ALPHA = new PowerTypeReference<>(new Identifier(MODID, "alpha"));

    public static final PowerFactory<Power> DWARVEN_SMELTING = new PowerFactory<>(new Identifier(MODID, "dwarven_smelting"), new SerializableData(), data -> (type, entity) -> {
            DwarvenSmeltingPower power = new DwarvenSmeltingPower(type, entity);
            return power;
    });

    public static final PowerFactory<Power> HALFLING_COOKING = new PowerFactory<>(new Identifier(MODID, "halfling_cooking"), new SerializableData(), data -> (type, entity) -> {
        HalflingCookingPower power = new HalflingCookingPower(type, entity);
        return power;
    });

    public static final PowerFactory<Power> DIMENSIONAL_TRAVEL = new PowerFactory<>(new Identifier(MODID, "dimensional_travel"), new SerializableData().add("key", ApoliDataTypes.KEY, new Active.Key()), data -> (type, entity) -> {
        DimensionalTravelPower power = new DimensionalTravelPower(type, entity);
        power.setKey(data.get("key"));
        return power;
    });

    public static final PowerFactory<Power> TRAVELER_TELEPORT = new PowerFactory<>(new Identifier(MODID, "traveler_teleport"), new SerializableData().add("key", ApoliDataTypes.KEY, new Active.Key()), data -> (type, entity) -> {
        TravelerTeleportPower power = new TravelerTeleportPower(type, entity);
        power.setKey(data.get("key"));
        return power;
    });

    public static final PowerFactory<Power> STRENGTH_IN_NUMBERS = new PowerFactory<>(new Identifier(MODID, "strength_in_numbers"), new SerializableData(), data -> (type, entity) -> {
        StrengthInNumbersPower power = new StrengthInNumbersPower(type, entity);
        return power;
    });

    public static void init() {

        Registry.register(ApoliRegistries.POWER_FACTORY, DIMENSIONAL_TRAVEL.getSerializerId(), DIMENSIONAL_TRAVEL);
        Registry.register(ApoliRegistries.POWER_FACTORY, TRAVELER_TELEPORT.getSerializerId(), TRAVELER_TELEPORT);
        Registry.register(ApoliRegistries.POWER_FACTORY, DWARVEN_SMELTING.getSerializerId(), DWARVEN_SMELTING);
        Registry.register(ApoliRegistries.POWER_FACTORY, HALFLING_COOKING.getSerializerId(), HALFLING_COOKING);
        Registry.register(ApoliRegistries.POWER_FACTORY, STRENGTH_IN_NUMBERS.getSerializerId(), STRENGTH_IN_NUMBERS);
    }
}
