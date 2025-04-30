package cy.jdkdigital.dyenamicsandfriends.compat;

import com.teamabnormals.clayworks.common.block.GlassDoorBlock;
import com.teamabnormals.clayworks.common.block.GlassTrapDoorBlock;
import com.teamabnormals.clayworks.core.registry.ClayworksBlocks;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class ClayworksCompat
{
    public static final List<DeferredHolder<Block, ? extends Block>> BLOCKS = new ArrayList<>();
    public static final List<DeferredHolder<Block, ? extends Block>> STAIRS = new ArrayList<>();
    public static final List<DeferredHolder<Block, ? extends Block>> SLABS = new ArrayList<>();
    public static final List<DeferredHolder<Block, ? extends Block>> WALLS = new ArrayList<>();
    public static final List<DeferredHolder<Block, ? extends Block>> DOORS = new ArrayList<>();
    public static final List<DeferredHolder<Block, ? extends Block>> TRAPDOORS = new ArrayList<>();
    public static final List<DeferredHolder<Block, ? extends Block>> POTS = new ArrayList<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "clayworks_" + color.getSerializedName();

        final BlockBehaviour.Properties properties = ClayworksBlocks.ClayworksProperties.terracotta(color.getMapColor()).lightLevel(state -> color.getLightValue());
        WALLS.add(DyenamicRegistry.registerBlock(prefix + "_terracotta_wall", () -> new WallBlock(properties), true));
        SLABS.add(DyenamicRegistry.registerBlock(prefix + "_terracotta_slab", () -> new SlabBlock(properties), true));
        STAIRS.add(DyenamicRegistry.registerBlock(prefix + "_terracotta_stairs", () -> new StairBlock(BuiltInRegistries.BLOCK.get(ResourceLocation.parse("dyenamics:" + color.getSerializedName() + "_terracotta")).defaultBlockState(), properties), true));
        BLOCKS.add(DyenamicRegistry.registerBlock(prefix + "_terracotta_bricks", () -> new Block(properties), true));
        WALLS.add(DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_wall", () -> new WallBlock(properties), true));
        STAIRS.add(DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_stairs", () -> new StairBlock(BuiltInRegistries.BLOCK.get(ResourceLocation.parse("dyenamics:" + color.getSerializedName() + "_terracotta")).defaultBlockState(), properties), true));
        SLABS.add(DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_slab", () -> new SlabBlock(properties), true));
        BLOCKS.add(DyenamicRegistry.registerBlock(prefix + "_terracotta_chiseled_bricks", () -> new Block(properties), true));
        DOORS.add(DyenamicRegistry.registerBlock(prefix + "_stained_glass_door", () -> new GlassDoorBlock(color.getAnalogue()), true));
        TRAPDOORS.add(DyenamicRegistry.registerBlock(prefix + "_stained_glass_trapdoor", () -> new GlassTrapDoorBlock(color.getAnalogue()), true));
//        POTS.add(createPot(prefix + "_decorated_pot", color));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.COLORED_BLOCKS)) {
            BLOCKS.forEach(holder -> event.accept(holder.get()));
            WALLS.forEach(holder -> event.accept(holder.get()));
            SLABS.forEach(holder -> event.accept(holder.get()));
            STAIRS.forEach(holder -> event.accept(holder.get()));
            DOORS.forEach(holder -> event.accept(holder.get()));
            TRAPDOORS.forEach(holder -> event.accept(holder.get()));
//            POTS.forEach(holder -> event.accept(holder.get()));
        }
        if (event.getTabKey().equals(CreativeModeTabs.REDSTONE_BLOCKS)) {
            DOORS.forEach(holder -> event.accept(holder.get()));
            TRAPDOORS.forEach(holder -> event.accept(holder.get()));
        }
    }

    public static void addBlocks(BlockEntityTypeAddBlocksEvent event) {
//        event.modify(BlockEntityType.DECORATED_POT, POTS.stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
    }

    public static DyenamicDyeColor getDyeColorFromPot(Block block) {
        var id = BuiltInRegistries.BLOCK.getKey(block);
        if (id.getNamespace().equals(DyenamicsAndFriends.MODID) && id.getPath().startsWith("clayworks_") && id.getPath().endsWith("_decorated_pot")) {
            return DyenamicDyeColor.byTranslationKey(id.getPath().replace("claywworks_", "").replace("_decorated_pot", ""), DyenamicDyeColor.WHITE);
        }
        return null;
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            DOORS.forEach((registryObject) -> ItemBlockRenderTypes.setRenderLayer(registryObject.get(), RenderType.translucent()));
            TRAPDOORS.forEach((registryObject) -> ItemBlockRenderTypes.setRenderLayer(registryObject.get(), RenderType.translucent()));
        }

        public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
//            POTS.forEach(holder -> {
//                event.registerItem(MemoizedBEWLR.asCustomItemRenderer((dispatcher, entityModelSet) -> new DecoratedPotBlockEntityWithoutLevelRenderer<>(dispatcher, entityModelSet, new DecoratedPotBlockEntity(BlockPos.ZERO, holder.get().defaultBlockState()))), holder.get().asItem());
//            });
        }
    }

    static DeferredHolder<Block, ? extends Block> createPot(String name, DyenamicDyeColor color) {
        DeferredHolder<Block, ? extends Block> block = DyenamicRegistry.registerBlock(name, () -> new DecoratedPotBlock(ClayworksBlocks.ClayworksProperties.decoratedPot(color.getMapColor())), false);
        DyenamicRegistry.registerItem(name, () -> new BlockItem(block.get(), new Item.Properties().component(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY)));
        return block;
    }
}
