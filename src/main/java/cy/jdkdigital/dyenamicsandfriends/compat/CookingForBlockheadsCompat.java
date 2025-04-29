package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.cookingforblockheads.block.*;
import net.blay09.mods.cookingforblockheads.block.entity.ModBlockEntities;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.*;

public class CookingForBlockheadsCompat
{
    // TODO right click with dye to switch block color
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> OVENS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> FRIDGES = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> CONNECTORS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> KITCHEN_FLOORS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> COOKING_TABLES = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> COUNTERS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> CABINETS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> SINKS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "cookingforblockheads_" + color.getSerializedName();
        OVENS.put(color, DyenamicRegistry.registerBlock(prefix + "_oven", () -> new OvenBlock(color.getAnalogue(), BlockBehaviour.Properties.of().lightLevel((state) -> color.getLightValue())), true));
        FRIDGES.put(color, DyenamicRegistry.registerBlock(prefix + "_fridge", () -> new FridgeBlock(color.getAnalogue(), BlockBehaviour.Properties.of().lightLevel((state) -> color.getLightValue())), true));
        CONNECTORS.put(color, DyenamicRegistry.registerBlock(prefix + "_connector", () -> new DyedConnectorBlock(color.getAnalogue(), BlockBehaviour.Properties.of().lightLevel((state) -> color.getLightValue())) {}, true));
        KITCHEN_FLOORS.put(color, DyenamicRegistry.registerBlock(prefix + "_kitchen_floor", () -> new Block(BlockBehaviour.Properties.of().lightLevel((state) -> color.getLightValue())), true));
        COOKING_TABLES.put(color, DyenamicRegistry.registerBlock(prefix + "_cooking_table", () -> new CookingTableBlock(color.getAnalogue(), BlockBehaviour.Properties.of().lightLevel((state) -> color.getLightValue())), true));
        COUNTERS.put(color, DyenamicRegistry.registerBlock(prefix + "_counter", () -> new CounterBlock(color.getAnalogue(), BlockBehaviour.Properties.of().lightLevel((state) -> color.getLightValue())), true));
        CABINETS.put(color, DyenamicRegistry.registerBlock(prefix + "_cabinet", () -> new CabinetBlock(color.getAnalogue(), BlockBehaviour.Properties.of().lightLevel((state) -> color.getLightValue())), true));
        SINKS.put(color, DyenamicRegistry.registerBlock(prefix + "_sink", () -> new SinkBlock(color.getAnalogue(), BlockBehaviour.Properties.of().lightLevel((state) -> color.getLightValue())), true));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.parse("cookingforblockheads:cookingforblockheads"));
        if (event.getTabKey().equals(key)) {
            Arrays.stream(DyenamicDyeColor.dyenamicValues()).forEach(dyenamicDyeColor -> {
                event.accept(OVENS.get(dyenamicDyeColor).get());
                event.accept(FRIDGES.get(dyenamicDyeColor).get());
                event.accept(CONNECTORS.get(dyenamicDyeColor).get());
                event.accept(KITCHEN_FLOORS.get(dyenamicDyeColor).get());
                event.accept(COOKING_TABLES.get(dyenamicDyeColor).get());
                event.accept(COUNTERS.get(dyenamicDyeColor).get());
                event.accept(CABINETS.get(dyenamicDyeColor).get());
                event.accept(SINKS.get(dyenamicDyeColor).get());
            });
        }
    }

    public static void addBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(ModBlockEntities.oven.get(), OVENS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
        event.modify(ModBlockEntities.fridge.get(), FRIDGES.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
        event.modify(ModBlockEntities.cookingTable.get(), COOKING_TABLES.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
        event.modify(ModBlockEntities.counter.get(), COUNTERS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
        event.modify(ModBlockEntities.cabinet.get(), CABINETS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
        event.modify(ModBlockEntities.sink.get(), SINKS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
    }

    public static class Client
    {
        public static List<DeferredObject<BakedModel>> ovenDoors;
        public static List<DeferredObject<BakedModel>> ovenDoorHandles;
        public static List<DeferredObject<BakedModel>> ovenDoorsActive;
        public static List<DeferredObject<BakedModel>> fridgeDoors;
        public static List<DeferredObject<BakedModel>> fridgeDoorsFlipped;
        public static List<DeferredObject<BakedModel>> fridgeDoorsLargeLower;
        public static List<DeferredObject<BakedModel>> fridgeDoorsLargeUpper;
        public static List<DeferredObject<BakedModel>> fridgeDoorsLargeLowerFlipped;
        public static List<DeferredObject<BakedModel>> fridgeDoorsLargeUpperFlipped;
        public static List<DeferredObject<BakedModel>> counterDoors;
        public static List<DeferredObject<BakedModel>> counterDoorsFlipped;
        public static List<DeferredObject<BakedModel>> cabinetDoors;
        public static List<DeferredObject<BakedModel>> cabinetDoorsFlipped;

        public static void register() {
//            BalmModels models = BalmClient.getModels();
//            DyenamicDyeColor[] colors = DyenamicDyeColor.values();
//
//            counterDoors = new ArrayList<>(colors.length);
//            counterDoorsFlipped = new ArrayList<>(colors.length);
//            cabinetDoors = Lists.newArrayListWithCapacity(colors.length);
//            cabinetDoorsFlipped = Lists.newArrayListWithCapacity(colors.length);
//            ovenDoors = new ArrayList<>(colors.length);
//            ovenDoorHandles = new ArrayList<>(colors.length);
//            ovenDoorsActive = new ArrayList<>(colors.length);
//            fridgeDoors = new ArrayList<>(colors.length);
//            fridgeDoorsFlipped = new ArrayList<>(colors.length);
//            fridgeDoorsLargeLower = new ArrayList<>(colors.length);
//            fridgeDoorsLargeUpper = new ArrayList<>(colors.length);
//            fridgeDoorsLargeLowerFlipped = new ArrayList<>(colors.length);
//            fridgeDoorsLargeUpperFlipped = new ArrayList<>(colors.length);
//            for (DyenamicDyeColor color : colors) {
//                final var colorPrefix = color.getSerializedName() + "_";
//                counterDoors.add(color.getId() + 1, models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "counter_door")));
//                counterDoorsFlipped.add(color.getId() + 1, models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "counter_door_flipped")));
//                cabinetDoors.add(color.getId() + 1, models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "cabinet_door")));
//                cabinetDoorsFlipped.add(color.getId() + 1, models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "cabinet_door_flipped")));
//                ovenDoors.add(color.getId(), models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "oven_door")));
//                ovenDoorsActive.add(color.getId(), models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "oven_door_active")));
//                ovenDoorHandles.add(color.getId(), models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "oven_door_handle")));
//                fridgeDoors.add(color.getId(), models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "fridge_door")));
//                fridgeDoorsFlipped.add(color.getId(), models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "fridge_door_flipped")));
//                fridgeDoorsLargeLower.add(color.getId(), models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "fridge_large_door_lower")));
//                fridgeDoorsLargeLowerFlipped.add(color.getId(), models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "fridge_large_door_lower_flipped")));
//                fridgeDoorsLargeUpper.add(color.getId(), models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "fridge_large_door_upper")));
//                fridgeDoorsLargeUpperFlipped.add(color.getId(), models.loadModel(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "block/cookingforblockheads/" + colorPrefix + "fridge_large_door_upper_flipped")));
//            }
        }

        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            event.register((blockState, blockAndTintGetter, blockPos, i) -> 0x3f76e4,
                    SINKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new)
            );
        }
    }
}
