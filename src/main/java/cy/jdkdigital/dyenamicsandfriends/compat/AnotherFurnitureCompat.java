package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.starfish_studios.another_furniture.block.LampBlock;
import com.starfish_studios.another_furniture.block.SofaBlock;
import com.starfish_studios.another_furniture.block.StoolBlock;
import com.starfish_studios.another_furniture.block.TallStoolBlock;
import com.starfish_studios.another_furniture.block.properties.ModBlockStateProperties;
import com.starfish_studios.another_furniture.client.renderer.blockentity.CurtainRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsCurtainBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsCurtainBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class AnotherFurnitureCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CURTAINS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> LAMPS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> SOFAS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "another_furniture_" + color.getSerializedName();
        LAMPS.put(color, DyenamicRegistry.registerBlock(prefix + "_lamp", () -> new LampBlock(DyeColor.WHITE, BlockBehaviour.Properties.of(Material.METAL, color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.METAL).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? (blockState.getValue(ModBlockStateProperties.LEVEL_1_3) * 5) : color.getLightValue())), CreativeModeTab.TAB_MISC, true));
        CURTAINS.put(color, DyenamicRegistry.registerBlock(prefix + "_curtain", () -> new DyenamicsCurtainBlock(color, BlockBehaviour.Properties.of(Material.WOOL, color.getMapColor()).strength(0.1F).sound(SoundType.WOOL).noOcclusion().lightLevel(state -> color.getLightValue()), DyenamicRegistry.registerBlockEntity(prefix + "_curtain", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsCurtainBlockEntity((DyenamicsCurtainBlock) CURTAINS.get(color).get(), pos, state), CURTAINS.get(color).get()))), CreativeModeTab.TAB_MISC, true));
        SOFAS.put(color, DyenamicRegistry.registerBlock(prefix + "_sofa", () -> new SofaBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getMapColor()).strength(1.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_MISC, true));
        DyenamicRegistry.registerBlock(prefix + "_stool", () -> new StoolBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getMapColor()).strength(1.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_MISC, true);
        DyenamicRegistry.registerBlock(prefix + "_tall_stool", () -> new TallStoolBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getMapColor()).strength(1.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_MISC, true);
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            CURTAINS.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsCurtainBlock curtain) {
                    event.registerBlockEntityRenderer(curtain.getBlockEntitySupplier().get(), CurtainRenderer::new);
                }
            });
        }
    }
}
