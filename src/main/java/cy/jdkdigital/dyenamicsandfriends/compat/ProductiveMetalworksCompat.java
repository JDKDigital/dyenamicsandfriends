package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import cy.jdkdigital.productivemetalworks.common.block.*;
import cy.jdkdigital.productivemetalworks.registry.MetalworksRegistrator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;

public class ProductiveMetalworksCompat
{
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> FOUNDRY_CONTROLLERS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> FOUNDRY_DRAINS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> FOUNDRY_TANKS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> FOUNDRY_WINDOWS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> FIRE_BRICKS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "productivemetalworks_" + color.getSerializedName();
        FOUNDRY_CONTROLLERS.put(color, DyenamicRegistry.registerBlock(prefix + "_foundry_controller", () -> new FoundryControllerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion().lightLevel(state -> state.getValue(BlockStateProperties.ATTACHED) ? 8 : 0).sound(SoundType.NETHER_BRICKS)), true));
        FOUNDRY_DRAINS.put(color, DyenamicRegistry.registerBlock(prefix + "_foundry_drain", () -> new FoundryDrainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).sound(SoundType.NETHER_BRICKS)), true));
        FOUNDRY_TANKS.put(color, DyenamicRegistry.registerBlock(prefix + "_foundry_tank", () -> new FoundryTankBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion().sound(SoundType.NETHER_BRICKS)), true));
        FOUNDRY_WINDOWS.put(color, DyenamicRegistry.registerBlock(prefix + "_foundry_window", () -> new FoundryWindowBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)), true));
        FIRE_BRICKS.put(color, DyenamicRegistry.registerBlock(prefix + "_fire_bricks", () -> new FireBricksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).sound(SoundType.NETHER_BRICKS)), true));
    }

    public static void buildTabContents(net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(MetalworksRegistrator.TAB_KEY)) {
            FOUNDRY_CONTROLLERS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            FOUNDRY_DRAINS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            FOUNDRY_TANKS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            FOUNDRY_WINDOWS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            FIRE_BRICKS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
        }
    }

    public static void addBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(MetalworksRegistrator.FOUNDRY_CONTROLLER_BLOCK_ENTITY.get(), FOUNDRY_CONTROLLERS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
        event.modify(MetalworksRegistrator.FOUNDRY_DRAIN_BLOCK_ENTITY.get(), FOUNDRY_DRAINS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
        event.modify(MetalworksRegistrator.FOUNDRY_TANK_BLOCK_ENTITY.get(), FOUNDRY_TANKS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
    }
}
