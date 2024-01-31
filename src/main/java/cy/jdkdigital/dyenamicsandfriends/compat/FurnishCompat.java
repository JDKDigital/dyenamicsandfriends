package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.common.blocks.DyenamicCarpetBlock;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsAmphoraBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsPlateBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsShowcaseBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.furnish.*;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import io.github.wouink.furnish.Furnish;
import io.github.wouink.furnish.block.*;
import io.github.wouink.furnish.client.renderer.PlateRenderer;
import io.github.wouink.furnish.client.renderer.ShowcaseRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class FurnishCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> SOFAS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> AWNINGS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CURTAINS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> SHOWCASES = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> AMPHORAS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> PLATES = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> PAPER_LAMPS = new HashMap<>();
    public static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CARPET_ON_STAIRS = new HashMap<>();
    public static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CARPET_ON_TRAPDOOR = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "furnish_" + color.getSerializedName();

        var carpetProps = BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(0.1F).sound(SoundType.WOOL).lightLevel((state) -> color.getLightValue());
        CARPET_ON_STAIRS.put(color, DyenamicRegistry.registerBlock(prefix + "_carpet_on_stairs", () -> new DyenamicsCarpetOnStairs(carpetProps, color), false));
        CARPET_ON_TRAPDOOR.put(color, DyenamicRegistry.registerBlock(prefix + "_carpet_on_trapdoor", () -> new DyenamicsCarpetOnTrapdoor(carpetProps, color), false));
        AWNINGS.put(color, DyenamicRegistry.registerBlock(prefix + "_awning", () -> new Awning(carpetProps), true));
        CURTAINS.put(color, DyenamicRegistry.registerBlock(prefix + "_curtain", () -> new Curtain(carpetProps), true));

        var woolProps = BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(0.8F).sound(SoundType.WOOL).lightLevel((state) -> color.getLightValue());
        SOFAS.put(color, DyenamicRegistry.registerBlock(prefix + "_sofa", () -> new Sofa(woolProps), true));
        SHOWCASES.put(color, DyenamicRegistry.registerBlock(prefix + "_showcase", () -> new DyenamicsShowcase(woolProps, DyenamicRegistry.registerBlockEntity(prefix + "_showcase", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsShowcaseBlockEntity(pos, state, (DyenamicsShowcase) SHOWCASES.get(color).get()), SHOWCASES.get(color).get()))), true));

        BlockBehaviour.Properties terracottaProps = BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(1.25F, 4.2F).lightLevel((state) -> color.getLightValue());
        AMPHORAS.put(color, DyenamicRegistry.registerBlock(prefix + "_amphora", () -> new DyenamicsAmphora(terracottaProps, DyenamicRegistry.registerBlockEntity(prefix + "_amphora", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsAmphoraBlockEntity(pos, state, (DyenamicsAmphora) AMPHORAS.get(color).get()), AMPHORAS.get(color).get()))) , true));
        PLATES.put(color, DyenamicRegistry.registerBlock(prefix + "_plate", () -> new DyenamicsPlate(terracottaProps, DyenamicRegistry.registerBlockEntity(prefix + "_plate", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsPlateBlockEntity(pos, state, (DyenamicsPlate) PLATES.get(color).get()), PLATES.get(color).get()))), true));

        PAPER_LAMPS.put(color, DyenamicRegistry.registerBlock(prefix + "_paper_lamp", PaperLamp::new, true));
    }

    public static void entityPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().getBlock() instanceof DyenamicCarpetBlock) {
            // only replace dyenamics carpets
            if (!ForgeRegistries.BLOCKS.getKey(event.getPlacedBlock().getBlock()).getNamespace().equals("dyenamics")) {
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
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("furnish:furnish"));
        if (event.getTabKey().equals(key)) {
            SOFAS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
            AWNINGS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
            CURTAINS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
            SHOWCASES.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
            AMPHORAS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
            PLATES.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
            PAPER_LAMPS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
        }
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            SHOWCASES.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsShowcase showcase) {
                    event.registerBlockEntityRenderer(showcase.getBlockEntitySupplier().get(), ShowcaseRenderer::new);
                }
            });
            PLATES.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsPlate plate) {
                    event.registerBlockEntityRenderer(plate.getBlockEntitySupplier().get(), PlateRenderer::new);
                }
            });
        }
    }
}
