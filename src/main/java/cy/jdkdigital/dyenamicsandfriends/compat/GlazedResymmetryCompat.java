//package cy.jdkdigital.dyenamicsandfriends.compat;
//
//import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
//import com.lethosos.glazedresymmetry.GlazedSlabBlock;
//import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.GlazedTerracottaBlock;
//import net.minecraft.world.level.block.RotatedPillarBlock;
//import net.minecraft.world.level.block.SlabBlock;
//import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.material.Material;
//
//public class GlazedResymmetryCompat
//{
//    public static void registerBlocks(DyenamicDyeColor color) {
//        String prefix = "glazedresymmetry_" + color.getSerializedName();
//
//        final BlockBehaviour.Properties properties = Block.Properties.of(Material.STONE, color.getMapColor()).strength(1.4F, 4.2F).noOcclusion().lightLevel(state -> color.getLightValue());
//        DyenamicRegistry.registerBlock(prefix + "_centered_glazed_terracotta", () -> new GlazedTerracottaBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
//        DyenamicRegistry.registerBlock(prefix + "_glazed_terracotta_pillar", () -> new RotatedPillarBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
//        DyenamicRegistry.registerBlock(prefix + "_glazed_terracotta_slab", () -> new GlazedSlabBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
//        DyenamicRegistry.registerBlock(prefix + "_glazed_terracotta_pillar_slab", () -> new SlabBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
//        DyenamicRegistry.registerBlock(prefix + "_glazed_terracotta_pillar_side_slab", () -> new GlazedSlabBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
//        DyenamicRegistry.registerBlock(prefix + "_centered_glazed_terracotta_slab", () -> new SlabBlock(properties), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
//    }
//}
