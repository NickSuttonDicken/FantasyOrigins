package froztigaming.fantasyorigins.power.conditions;

import froztigaming.fantasyorigins.FantasyOrigins;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomPlayerConditions {
    private static final String MODID = FantasyOrigins.MODID;
    public static void register(){
        register(new ConditionFactory<>(new Identifier(MODID, "is_full_moon"), new SerializableData(), (data, player) -> player.world.getMoonSize() == 1.0));
    }

    private static void register(ConditionFactory<Entity> conditionFactory)
    {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
