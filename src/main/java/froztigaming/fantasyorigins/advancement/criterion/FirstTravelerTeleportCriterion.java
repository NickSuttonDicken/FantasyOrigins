package froztigaming.fantasyorigins.advancement.criterion;

import com.google.gson.JsonObject;
import froztigaming.fantasyorigins.FantasyOrigins;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static froztigaming.fantasyorigins.FantasyOrigins.MODID;

public class FirstTravelerTeleportCriterion extends AbstractCriterion<FirstTravelerTeleportCriterion.Conditions> {
    public static final Identifier id = new Identifier(MODID,"first_traveler_teleport");

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public FirstTravelerTeleportCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        return new FirstTravelerTeleportCriterion.Conditions(extended);
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
