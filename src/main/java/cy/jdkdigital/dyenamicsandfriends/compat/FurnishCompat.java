package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.common.blocks.DyenamicCarpetBlock;
import cofh.dyenamics.core.init.BlockInit;
import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsAmphora;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsPlate;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsShowcase;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsAmphoraBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsPlateBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsShowcaseBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import io.github.wouink.furnish.block.*;
import io.github.wouink.furnish.client.renderer.PlateRenderer;
import io.github.wouink.furnish.client.renderer.ShowcaseRenderer;
import io.github.wouink.furnish.setup.FurnishItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.world.BlockEvent;
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

        var woolCarpet = BlockInit.DYED_BLOCKS.get(color.getSerializedName()).get("carpet");
        CARPET_ON_STAIRS.put(color, DyenamicRegistry.registerBlock(prefix + "_carpet_on_stairs", () -> new CarpetOnStairs(BlockBehaviour.Properties.copy(woolCarpet.get()), woolCarpet.get()), FurnishItems.Furnish_ItemGroup , false));
        CARPET_ON_TRAPDOOR.put(color, DyenamicRegistry.registerBlock(prefix + "_carpet_on_trapdoor", () -> new CarpetOnTrapdoor(BlockBehaviour.Properties.copy(woolCarpet.get()), woolCarpet.get()), FurnishItems.Furnish_ItemGroup , false));
        AWNINGS.put(color, DyenamicRegistry.registerBlock(prefix + "_awning", () -> new Awning(BlockBehaviour.Properties.copy(woolCarpet.get())), FurnishItems.Furnish_ItemGroup , true));
        CURTAINS.put(color, DyenamicRegistry.registerBlock(prefix + "_curtain", () -> new Curtain(BlockBehaviour.Properties.copy(woolCarpet.get())), FurnishItems.Furnish_ItemGroup , true));

        var woolBlock = BlockInit.DYED_BLOCKS.get(color.getSerializedName()).get("wool");
        SOFAS.put(color, DyenamicRegistry.registerBlock(prefix + "_sofa", () -> new Sofa(BlockBehaviour.Properties.copy(woolBlock.get())), FurnishItems.Furnish_ItemGroup , true));
        SHOWCASES.put(color, DyenamicRegistry.registerBlock(prefix + "_showcase", () -> new DyenamicsShowcase(BlockBehaviour.Properties.copy(woolBlock.get()), DyenamicRegistry.registerBlockEntity(prefix + "_showcase", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsShowcaseBlockEntity(pos, state, (DyenamicsShowcase) SHOWCASES.get(color).get()), SHOWCASES.get(color).get()))), FurnishItems.Furnish_ItemGroup, true));

        var terracotta = BlockInit.DYED_BLOCKS.get(color.getSerializedName()).get("terracotta");
        AMPHORAS.put(color, DyenamicRegistry.registerBlock(prefix + "_amphora", () -> new DyenamicsAmphora(BlockBehaviour.Properties.copy(terracotta.get()), DyenamicRegistry.registerBlockEntity(prefix + "_amphora", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsAmphoraBlockEntity(pos, state, (DyenamicsAmphora) AMPHORAS.get(color).get()), AMPHORAS.get(color).get()))), FurnishItems.Furnish_ItemGroup , true));
        PLATES.put(color, DyenamicRegistry.registerBlock(prefix + "_plate", () -> new DyenamicsPlate(BlockBehaviour.Properties.copy(terracotta.get()), DyenamicRegistry.registerBlockEntity(prefix + "_plate", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsPlateBlockEntity(pos, state, (DyenamicsPlate) PLATES.get(color).get()), PLATES.get(color).get()))), FurnishItems.Furnish_ItemGroup , true));

        PAPER_LAMPS.put(color, DyenamicRegistry.registerBlock(prefix + "_paper_lamp", PaperLamp::new, FurnishItems.Furnish_ItemGroup , true));
    }

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

    public static void registerBlockRendering() {
        for(RegistryObject<? extends Block> b : SHOWCASES.values()) {
            ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.translucent());
        }
    }

    public static void entityPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().getBlock() instanceof DyenamicCarpetBlock) {
            // only replace dyenamics carpets
            if (!ForgeRegistries.BLOCKS.getKey(event.getPlacedBlock().getBlock()).getNamespace().equals("dyenamics")) {
                return;
            }

            BlockState stateBelow = event.getWorld().getBlockState(event.getPos().below());
            if (stateBelow.getBlock() instanceof StairBlock && !event.getEntity().isShiftKeyDown()) {
                if (stateBelow.getValue(StairBlock.HALF) == Half.BOTTOM && stateBelow.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT) {
                    var color = ((DyenamicCarpetBlock) event.getPlacedBlock().getBlock()).getDyenamicColor();
                    event.getWorld().setBlock(
                            event.getPos(),
                            FurnishCompat.CARPET_ON_STAIRS.get(color).get().defaultBlockState().setValue(
                                    BlockStateProperties.HORIZONTAL_FACING, stateBelow.getValue(BlockStateProperties.HORIZONTAL_FACING)),
                            Block.UPDATE_ALL
                    );
                }
            } else if (stateBelow.getBlock() instanceof TrapDoorBlock) {
                if (stateBelow.getValue(TrapDoorBlock.HALF) == Half.TOP) {
                    var color = ((DyenamicCarpetBlock) event.getPlacedBlock().getBlock()).getDyenamicColor();
                    event.getWorld().setBlock(
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
}
