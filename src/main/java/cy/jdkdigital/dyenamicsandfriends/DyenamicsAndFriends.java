package cy.jdkdigital.dyenamicsandfriends;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import cy.jdkdigital.dyenamicsandfriends.loot.OptionalLootItem;
import cy.jdkdigital.dyenamicsandfriends.loot.condition.ModLoadedCondition;
import cy.jdkdigital.dyenamicsandfriends.loot.condition.OptionalLootItemBlockStatePropertyCondition;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DyenamicsAndFriends.MODID)
public class DyenamicsAndFriends
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "dyenamicsandfriends";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);
    public static final DeferredRegister<LootPoolEntryType> LOOT_POOL_ENTRIES = DeferredRegister.create(Registries.LOOT_POOL_ENTRY_TYPE, MODID);
    public static final DeferredRegister<LootItemConditionType> LOOT_POOL_CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, MODID);

    public static final DeferredHolder<LootPoolEntryType, LootPoolEntryType> OPTIONAL_LOOT_ITEM = LOOT_POOL_ENTRIES.register("optional_loot_item", () -> new LootPoolEntryType(OptionalLootItem.CODEC));
    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> OPTIONAL_BLOCK_STATE_PROPERTY = LOOT_POOL_CONDITIONS.register("optional_block_state_property", () -> new LootItemConditionType(OptionalLootItemBlockStatePropertyCondition.CODEC));
    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> MOD_LOADED_CONDITION = LOOT_POOL_CONDITIONS.register("mod_loaded", () -> new LootItemConditionType(ModLoadedCondition.CODEC));

    public DyenamicsAndFriends(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::onPackEvent);

        DyenamicRegistry.setup();
        DyenamicRegistry.registerCompatBlocks();

        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CONTAINER_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        PARTICLE_TYPES.register(modEventBus);
        LOOT_SERIALIZERS.register(modEventBus);
        LOOT_POOL_ENTRIES.register(modEventBus);
        LOOT_POOL_CONDITIONS.register(modEventBus);
    }

    private void onPackEvent(AddPackFindersEvent event) {
        for (String modId: DyenamicRegistry.MODS) {
            if (ModList.get().isLoaded(modId)) {
                event.addPackFinders(ResourceLocation.fromNamespaceAndPath(MODID, "compat_packs/" + modId + "/"), PackType.SERVER_DATA, Component.translatable("dataPack." + MODID + "." + modId), PackSource.BUILT_IN, true, Pack.Position.BOTTOM);
                if (modId.equals("connectedglass") || modId.equals("luminax") || modId.equals("crystalix") || modId.equals("cookingforblockheads")) {
                    event.addPackFinders(ResourceLocation.fromNamespaceAndPath(MODID, "compat_packs/" + modId + "/"), PackType.CLIENT_RESOURCES, Component.translatable("resourcePack." + MODID + "." + modId), PackSource.BUILT_IN, true, Pack.Position.BOTTOM);
                }
            }
        }
    }
}
