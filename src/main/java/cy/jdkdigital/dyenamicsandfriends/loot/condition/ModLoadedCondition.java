package cy.jdkdigital.dyenamicsandfriends.loot.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.fml.ModList;

public record ModLoadedCondition(String modId) implements LootItemCondition
{
    public static MapCodec<ModLoadedCondition> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(Codec.STRING.fieldOf("modId").forGetter(ModLoadedCondition::modId))
                    .apply(builder, ModLoadedCondition::new));

    @Override
    public LootItemConditionType getType() {
        return DyenamicsAndFriends.MOD_LOADED_CONDITION.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return ModList.get().isLoaded(modId);
    }
}
