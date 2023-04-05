package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.oreganized.DyenamicsCrystalGlassBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.oreganized.DyenamicsCrystalGlassPaneBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class RegionsUnexploredCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> PLANKS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "regions_unexplored_" + color.getSerializedName();
        PLANKS.put(color, DyenamicRegistry.registerBlock(prefix + "_painted_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS, true));
        DyenamicRegistry.registerBlock(prefix + "_painted_stairs", () -> new StairBlock(() -> PLANKS.get(color).get().defaultBlockState(),BlockBehaviour.Properties.of(Material.WOOD, color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_painted_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
    }
}
