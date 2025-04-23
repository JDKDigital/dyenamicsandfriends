package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ClayworksCompat
{
    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "clayworks_" + color.getSerializedName();

//        final BlockBehaviour.Properties properties = Block.Properties.of().mapColor(color.getMapColor()).strength(1.25F, 4.2F).noOcclusion().lightLevel(state -> color.getLightValue());
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_wall", () -> new WallBlock(properties), true);
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_slab", () -> new SlabBlock(properties), true);
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_vertical_slab", () -> new VerticalSlabBlock(properties), true);
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_stairs", () -> new StairBlock(() -> BuiltInRegistries.BLOCK.get(ResourceLocation.parse("dyenamics:" + color.getSerializedName() + "_terracotta")).defaultBlockState(), properties), true);
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_bricks", () -> new Block(properties), true);
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_wall", () -> new WallBlock(properties), true);
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_stairs", () -> new StairBlock(() -> BuiltInRegistries.BLOCK.get(ResourceLocation.parse("dyenamics:" + color.getSerializedName() + "_terracotta")).defaultBlockState(), properties), true);
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_slab", () -> new SlabBlock(properties), true);
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_brick_vertical_slab", () -> new VerticalSlabBlock(properties), true);
//        DyenamicRegistry.registerBlock(prefix + "_terracotta_chiseled_bricks", () -> new Block(properties), true);
    }
}
