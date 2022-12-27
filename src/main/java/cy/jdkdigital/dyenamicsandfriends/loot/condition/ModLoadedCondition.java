package cy.jdkdigital.dyenamicsandfriends.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.fml.ModList;

public class ModLoadedCondition implements LootItemCondition
{
    private final String modId;

    public ModLoadedCondition(final String modId) {
        this.modId = modId;
    }

    @Override
    public LootItemConditionType getType() {
        return DyenamicsAndFriends.MOD_LOADED_CONDITION.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return ModList.get().isLoaded(modId);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ModLoadedCondition>
    {
        @Override
        public void serialize(JsonObject object, ModLoadedCondition instance, JsonSerializationContext ctx)
        {
            object.addProperty("modid", instance.modId);
        }

        @Override
        public ModLoadedCondition deserialize(JsonObject object, JsonDeserializationContext ctx)
        {
            return new ModLoadedCondition(GsonHelper.getAsString(object, "modid"));
        }
    }
}
