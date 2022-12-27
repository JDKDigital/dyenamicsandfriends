package cy.jdkdigital.dyenamicsandfriends.loot.modifier;

import com.google.gson.JsonObject;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StainedGlassBlockLootModifier extends LootModifier
{
    private final Item addition;

    protected StainedGlassBlockLootModifier(LootItemCondition[] conditionsIn, Item addition) {
        super(conditionsIn);
        this.addition = addition;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        ItemStack tool = context.getParam(LootContextParams.TOOL);
        if (tool.equals(ItemStack.EMPTY) || !EnchantmentHelper.getEnchantments(tool).containsKey(Enchantments.SILK_TOUCH)) {
            int count = Math.min(4, (context.getRandom().nextInt(1, 4) + 1) * (context.getLootingModifier() + 1));

            generatedLoot.add(new ItemStack(this.addition, count));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<StainedGlassBlockLootModifier>
    {
        @Override
        public StainedGlassBlockLootModifier read(ResourceLocation resourceLocation, JsonObject jsonObject, LootItemCondition[] lootItemConditions) {
            Item addition = ForgeRegistries.ITEMS.getValue(new ResourceLocation((GsonHelper.getAsString(jsonObject, "addition"))));
            return new StainedGlassBlockLootModifier(lootItemConditions, addition);
        }

        @Override
        public JsonObject write(StainedGlassBlockLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());
            return json;
        }
    }
}
