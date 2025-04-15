package cy.jdkdigital.dyenamicsandfriends.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Optional;
import java.util.Set;

public record OptionalLootItemBlockStatePropertyCondition(Holder<Block> block, Optional<StatePropertiesPredicate> properties) implements LootItemCondition
{
    public static final MapCodec<OptionalLootItemBlockStatePropertyCondition> CODEC = RecordCodecBuilder.mapCodec((inst) -> inst.group(
            BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("block").orElse(BuiltInRegistries.BLOCK.getHolder(BuiltInRegistries.BLOCK.getDefaultKey()).get()).forGetter(OptionalLootItemBlockStatePropertyCondition::block),
            StatePropertiesPredicate.CODEC.optionalFieldOf("properties").forGetter(OptionalLootItemBlockStatePropertyCondition::properties)
    ).apply(inst, OptionalLootItemBlockStatePropertyCondition::new));

    @Override
    public LootItemConditionType getType() {
        return DyenamicsAndFriends.OPTIONAL_BLOCK_STATE_PROPERTY.get();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.BLOCK_STATE);
    }

    @Override
    public boolean test(LootContext context) {
        BlockState blockstate = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        return blockstate != null && blockstate.is(this.block) && (this.properties.isEmpty() || this.properties.get().matches(blockstate));
    }

    public static OptionalLootItemBlockStatePropertyCondition.Builder hasBlockStateProperties(Block pBlock) {
        return new OptionalLootItemBlockStatePropertyCondition.Builder(pBlock);
    }

    public static class Builder implements LootItemCondition.Builder {
        private final Holder<Block> block;
        private Optional<StatePropertiesPredicate> properties = Optional.empty();

        public Builder(Block block) {
            this.block = block.builtInRegistryHolder();
        }

        public OptionalLootItemBlockStatePropertyCondition.Builder setProperties(StatePropertiesPredicate.Builder statePredicateBuilder) {
            this.properties = statePredicateBuilder.build();
            return this;
        }

        @Override
        public LootItemCondition build() {
            return new OptionalLootItemBlockStatePropertyCondition(this.block, this.properties);
        }
    }
}
