package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.common.block.DyenamicCarpetBlock;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.client.render.furnish.DyenamicsPlateRenderer;
import cy.jdkdigital.dyenamicsandfriends.client.render.furnish.DyenamicsShowcaseRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsAmphoraBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsPlateBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsShowcaseBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.furnish.*;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import io.github.wouink.furnish.block.*;
import io.github.wouink.furnish.client.renderer.PlateRenderer;
import io.github.wouink.furnish.client.renderer.ShowcaseRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FurnishCompat
{
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> SOFAS = new HashMap<>();
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> AWNINGS = new HashMap<>();
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> CURTAINS = new HashMap<>();
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> SHOWCASES = new HashMap<>();
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> AMPHORAS = new HashMap<>();
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> PLATES = new HashMap<>();
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> PAPER_LAMPS = new HashMap<>();
    public static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> CARPET_ON_STAIRS = new HashMap<>();
    public static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> CARPET_ON_TRAPDOOR = new HashMap<>();

    public static Supplier<BlockEntityType<DyenamicsAmphoraBlockEntity>> AMPHORA_BLOCK_ENTITY;
    public static Supplier<BlockEntityType<DyenamicsPlateBlockEntity>> PLATE_BLOCK_ENTITY;
    public static Supplier<BlockEntityType<DyenamicsShowcaseBlockEntity>> SHOWCASE_BLOCK_ENTITY;

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "furnish_" + color.getSerializedName();

        var carpetProps = BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(0.1F).sound(SoundType.WOOL).lightLevel((state) -> color.getLightValue());
        CARPET_ON_STAIRS.put(color, DyenamicRegistry.registerBlock(prefix + "_carpet_on_stairs", () -> new DyenamicsCarpetOnStairs(carpetProps, color), false));
        CARPET_ON_TRAPDOOR.put(color, DyenamicRegistry.registerBlock(prefix + "_carpet_on_trapdoor", () -> new DyenamicsCarpetOnTrapdoor(carpetProps, color), false));
        AWNINGS.put(color, DyenamicRegistry.registerBlock(prefix + "_awning", () -> new Awning(carpetProps), true));
        CURTAINS.put(color, DyenamicRegistry.registerBlock(prefix + "_curtain", () -> new Curtain(carpetProps), true));

        var woolProps = BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(0.8F).sound(SoundType.WOOL).lightLevel((state) -> color.getLightValue());
        SOFAS.put(color, DyenamicRegistry.registerBlock(prefix + "_sofa", () -> new Sofa(woolProps), true));
        SHOWCASES.put(color, DyenamicRegistry.registerBlock(prefix + "_showcase", () -> new DyenamicsShowcase(woolProps), true));

        BlockBehaviour.Properties terracottaProps = BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(1.25F, 4.2F).lightLevel((state) -> color.getLightValue());
        AMPHORAS.put(color, DyenamicRegistry.registerBlock(prefix + "_amphora", () -> new DyenamicsAmphora(terracottaProps) , true));
        PLATES.put(color, DyenamicRegistry.registerBlock(prefix + "_plate", () -> new DyenamicsPlate(terracottaProps), true));

        PAPER_LAMPS.put(color, DyenamicRegistry.registerBlock(prefix + "_paper_lamp", PaperLamp::new, true));

        AMPHORA_BLOCK_ENTITY = DyenamicRegistry.registerBlockEntity(prefix + "_amphora", () -> DyenamicRegistry.createBlockEntityType(DyenamicsAmphoraBlockEntity::new, AMPHORAS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0])));
        PLATE_BLOCK_ENTITY = DyenamicRegistry.registerBlockEntity(prefix + "_plate", () -> DyenamicRegistry.createBlockEntityType(DyenamicsPlateBlockEntity::new, PLATES.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0])));
        SHOWCASE_BLOCK_ENTITY = DyenamicRegistry.registerBlockEntity(prefix + "_showcase", () -> DyenamicRegistry.createBlockEntityType(DyenamicsShowcaseBlockEntity::new, SHOWCASES.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0])));
    }

    public static void entityPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().getBlock() instanceof DyenamicCarpetBlock) {
            // only replace dyenamics carpets
            if (!BuiltInRegistries.BLOCK.getKey(event.getPlacedBlock().getBlock()).getNamespace().equals("dyenamics")) {
                return;
            }

            BlockState stateBelow = event.getLevel().getBlockState(event.getPos().below());
            if (stateBelow.getBlock() instanceof StairBlock && !event.getEntity().isShiftKeyDown()) {
                if (stateBelow.getValue(StairBlock.HALF) == Half.BOTTOM && stateBelow.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT) {
                    var color = ((DyenamicCarpetBlock) event.getPlacedBlock().getBlock()).getDyenamicColor();
                    event.getLevel().setBlock(
                            event.getPos(),
                            FurnishCompat.CARPET_ON_STAIRS.get(color).get().defaultBlockState().setValue(
                                    BlockStateProperties.HORIZONTAL_FACING, stateBelow.getValue(BlockStateProperties.HORIZONTAL_FACING)),
                            Block.UPDATE_ALL
                    );
                }
            } else if (stateBelow.getBlock() instanceof TrapDoorBlock) {
                if (stateBelow.getValue(TrapDoorBlock.HALF) == Half.TOP) {
                    var color = ((DyenamicCarpetBlock) event.getPlacedBlock().getBlock()).getDyenamicColor();
                    event.getLevel().setBlock(
                            event.getPos(),
                            FurnishCompat.CARPET_ON_TRAPDOOR.get(color).get().defaultBlockState().setValue(
                                            BlockStateProperties.HORIZONTAL_FACING, stateBelow.getValue(BlockStateProperties.HORIZONTAL_FACING))
                                    .setValue(CarpetOnTrapdoor.OPEN, stateBelow.getValue(TrapDoorBlock.OPEN)),
                            Block.UPDATE_ALL
                    );
                }
            }
        }
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.parse("furnish:furnish"));
        if (event.getTabKey().equals(key)) {
            SOFAS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            AWNINGS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            CURTAINS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            SHOWCASES.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            AMPHORAS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            PLATES.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            PAPER_LAMPS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
        }
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            SHOWCASES.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsShowcase) {
                    event.registerBlockEntityRenderer(FurnishCompat.SHOWCASE_BLOCK_ENTITY.get(), DyenamicsShowcaseRenderer::new);
                }
            });
            PLATES.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsPlate) {
                    event.registerBlockEntityRenderer(FurnishCompat.PLATE_BLOCK_ENTITY.get(), DyenamicsPlateRenderer::new);
                }
            });
        }
    }
}
