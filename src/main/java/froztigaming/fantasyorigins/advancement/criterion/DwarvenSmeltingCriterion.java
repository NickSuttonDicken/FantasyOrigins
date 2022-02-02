package froztigaming.fantasyorigins.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static froztigaming.fantasyorigins.FantasyOrigins.MODID;

public class DwarvenSmeltingCriterion extends AbstractCriterion<DwarvenSmeltingCriterion.Conditions> {
    public static final Identifier id = new Identifier(MODID,"dwarven_smelting");

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public DwarvenSmeltingCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        return new DwarvenSmeltingCriterion.Conditions(extended);
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, (conditions) -> {
            return conditions.matches(player);
        });
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(EntityPredicate.Extended player) {
            super(id, player);
        }

        public boolean matches(ServerPlayerEntity player) {
            return true;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            return jsonObject;
        }
    }
}