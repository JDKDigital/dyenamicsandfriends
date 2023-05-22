package cy.jdkdigital.dyenamicsandfriends.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class OptionalLootItemBlockStatePropertyCondition implements LootItemCondition
{
    final Block block;
    final StatePropertiesPredicate properties;

    OptionalLootItemBlockStatePropertyCondition(Block pBlock, StatePropertiesPredicate pStatePredicate) {
        this.block = pBlock;
        this.properties = pStatePredicate;
    }

    @Override
    public LootItemConditionType getType() {
        return DyenamicsAndFriends.OPTIONAL_BLOCK_STATE_PROPERTY.get();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.BLOCK_STATE);
    }

    @Override
    public boolean test(LootContext p_81772_) {
        BlockState blockstate = p_81772_.getParamOrNull(LootContextParams.BLOCK_STATE);
        return blockstate != null && blockstate.is(this.block) && this.properties.matches(blockstate);
    }

    public static OptionalLootItemBlockStatePropertyCondition.Builder hasBlockStateProperties(Block pBlock) {
        return new OptionalLootItemBlockStatePropertyCondition.Builder(pBlock);
    }

    public static class Builder implements LootItemCondition.Builder
    {
        private final Block block;
        private StatePropertiesPredicate properties = StatePropertiesPredicate.ANY;

        public Builder(Block pBlock) {
            this.block = pBlock;
        }

        public OptionalLootItemBlockStatePropertyCondition.Builder setProperties(StatePropertiesPredicate.Builder pStatePredicateBuilder) {
            this.properties = pStatePredicateBuilder.build();
            return this;
        }

        @Override
        public LootItemCondition build() {
            return new OptionalLootItemBlockStatePropertyCondition(this.block, this.properties);
        }
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<OptionalLootItemBlockStatePropertyCondition>
    {
        @Override
        public void serialize(JsonObject pJson, OptionalLootItemBlockStatePropertyCondition propertyCondition, JsonSerializationContext p_81797_) {
            pJson.addProperty("block", BuiltInRegistries.BLOCK.getKey(propertyCondition.block).toString());
            pJson.add("properties", propertyCondition.properties.serializeToJson());
        }

        @Override
        public OptionalLootItemBlockStatePropertyCondition deserialize(JsonObject pJson, JsonDeserializationContext p_81806_) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(pJson, "block"));

            StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.fromJson(pJson.get("properties"));
            if (ForgeRegistries.BLOCKS.containsKey(resourcelocation)) {
                Block block = ForgeRegistries.BLOCKS.getValue(resourcelocation);
                statepropertiespredicate.checkState(block.getStateDefinition(), (s) -> {
                    throw new JsonSyntaxException("Block " + block + " has no property " + s);
                });
                return new OptionalLootItemBlockStatePropertyCondition(block, statepropertiespredicate);
            }
            return new OptionalLootItemBlockStatePropertyCondition(Blocks.DIRT, statepropertiespredicate);
        }
    }
}
