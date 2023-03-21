package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.oreganized.DyenamicsCrystalGlassBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.oreganized.DyenamicsCrystalGlassPaneBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class OreganizedCompat
{
    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "oreganized_" + color.getSerializedName();
        DyenamicRegistry.registerBlock(prefix + "_crystal_glass", () -> new DyenamicsCrystalGlassBlock(color, BlockBehaviour.Properties.of(Material.GLASS, color.getMapColor()).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a, b, c, d) -> false).isRedstoneConductor((a, b, c) -> false).isSuffocating((a, b, c) -> false).isViewBlocking((a, b, c) -> false).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_crystal_glass_pane", () -> new DyenamicsCrystalGlassPaneBlock(color, BlockBehaviour.Properties.of(Material.GLASS, color.getMapColor()).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a, b, c, d) -> false).isRedstoneConductor((a, b, c) -> false).isSuffocating((a, b, c) -> false).isViewBlocking((a, b, c) -> false).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_waxed_concrete_powder", () -> new Block(BlockBehaviour.Properties.of(Material.SAND, color.getMapColor()).strength(0.5F).sound(SoundType.SAND).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
    }
}
