package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsCrystalGlassBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsCrystalGlassPaneBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ClayworksCompat
{
    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "clayworks_" + color.getSerializedName();

        final BlockBehaviour.Properties properties = Block.Properties.of(Material.CLAY, color.getMapColor()).strength(1.25F, 4.2F).noOcclusion().lightLevel(state -> color.getLightValue());
        DyenamicRegistry.registerBlock(prefix + "_terracotta_wall", () -> new WallBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_terracotta_slab", () -> new SlabBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_terracotta_vertical_slab", () -> new VerticalSlabBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_terracotta_stairs", () -> new StairBlock(() -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("dyenamics:" + color.getSerializedName() + "_terracotta")).defaultBlockState(), properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_terracotta_bricks", () -> new Block(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_wall", () -> new WallBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_stairs", () -> new StairBlock(() -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("dyenamics:" + color.getSerializedName() + "_terracotta")).defaultBlockState(), properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_slab", () -> new SlabBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_terracotta_chiseled_bricks", () -> new Block(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
    }
}
