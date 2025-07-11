package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.common.item.DyenamicDyeItem;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.blay09.mods.cookingforblockheads.block.*;
import net.blay09.mods.cookingforblockheads.block.entity.ModBlockEntities;
import net.blay09.mods.cookingforblockheads.block.entity.util.TransferableBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nullable;
import java.util.*;

public class CookingForBlockheadsCompat
{
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
        KITCHEN_FLOORS.put(color, DyenamicRegistry.registerBlock(prefix + "_kitchen_floor", () -> new KitchenFloorBlock(BlockBehaviour.Properties.of().lightLevel((state) -> color.getLightValue())), true));
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

    public static void playerRightClick(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();

        if (event.getLevel() instanceof ServerLevel level) {
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof DyenamicDyeItem dyeItem) {
                BlockState state = level.getBlockState(event.getPos());
                if (state.getBlock() instanceof BaseKitchenBlock) {

                    var newState = recolorBlock(state, dyeItem.getDyeColor());
                    if (newState != null && !newState.is(state.getBlock())) {
                        final var blockEntity = level.getBlockEntity(event.getPos());
                        Object transferData = null;
                        if (blockEntity instanceof TransferableBlockEntity transferableBlockEntity) {
                            transferData = transferableBlockEntity.snapshotDataForTransfer();
                        }

                        level.setBlockAndUpdate(event.getPos(), newState);

                        final var newBlockEntity = level.getBlockEntity(event.getPos());
                        if (transferData != null && newBlockEntity instanceof TransferableBlockEntity transferableBlockEntity) {
                            transferableBlockEntity.restoreFromTransferSnapshot(transferData);
                        }

                        if (!event.getEntity().isCreative()) {
                            itemStack.shrink(1);
                        }
                        event.getEntity().swing(event.getHand());
                        event.setCanceled(true);
                    }
                }
            }
        }
    }


    @Nullable
    private static BlockState recolorBlock(BlockState state, DyenamicDyeColor color) {
        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        BlockState newState = null;
        if (key.getPath().contains("_oven")) {
            newState = OVENS.get(color).get().defaultBlockState();
        } else if (key.getPath().contains("_fridge")) {
            newState = FRIDGES.get(color).get().defaultBlockState();
        } else if (key.getPath().contains("_connector")) {
            newState = CONNECTORS.get(color).get().defaultBlockState();
        } else if (key.getPath().contains("_cooking_table")) {
            newState = COOKING_TABLES.get(color).get().defaultBlockState();
        } else if (key.getPath().contains("_counter")) {
            newState = COUNTERS.get(color).get().defaultBlockState();
        } else if (key.getPath().contains("_cabinet")) {
            newState = CABINETS.get(color).get().defaultBlockState();
        } else if (key.getPath().contains("_sink")) {
            newState = SINKS.get(color).get().defaultBlockState();
        }
        if (newState != null) {
            for (Property property : state.getProperties()) {
                if (newState.hasProperty(property)) {
                    newState = newState.setValue(property, state.getValue(property));
                }
            }
            if (newState.hasProperty(BaseKitchenBlock.COLOR) && newState.hasProperty(BaseKitchenBlock.HAS_COLOR)) {
                newState = newState.setValue(BaseKitchenBlock.HAS_COLOR, true).setValue(BaseKitchenBlock.COLOR, color.getAnalogue());
            }
        }

        return newState;
    }

    public static class Client
    {
        public static void register() {
        }

        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            event.register((blockState, blockAndTintGetter, blockPos, i) -> 0x3f76e4,
                    SINKS.values().stream().map(DeferredHolder::get).toArray(Block[]::new)
            );
        }
    }
}
