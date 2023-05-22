package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.common.blocks.DyenamicStainedGlassBlock;
import cofh.dyenamics.common.blocks.DyenamicStainedGlassPane;
import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.loot.modifier.StainedGlassBlockLootModifier;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class QuarkCompat
{
    public static void setup() {
        DyenamicsAndFriends.LOOT_SERIALIZERS.register("shard", StainedGlassBlockLootModifier.CODEC);
    }

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "quark_" + color.getSerializedName();
        // CreativeModeTab.TAB_BUILDING_BLOCKS
        DyenamicRegistry.registerBlock(prefix + "_framed_glass", () -> new DyenamicStainedGlassBlock(color, BlockBehaviour.Properties.of(Material.GLASS, color.getMapColor()).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a, b, c, d) -> false).isRedstoneConductor((a, b, c) -> false).isSuffocating((a, b, c) -> false).isViewBlocking((a, b, c) -> false).lightLevel(state -> color.getLightValue())), true);
        DyenamicRegistry.registerBlock(prefix + "_framed_glass_pane", () -> new DyenamicStainedGlassPane(color, BlockBehaviour.Properties.of(Material.GLASS, color.getMapColor()).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a, b, c, d) -> false).isRedstoneConductor((a, b, c) -> false).isSuffocating((a, b, c) -> false).isViewBlocking((a, b, c) -> false).lightLevel(state -> color.getLightValue())), true);

        BlockBehaviour.Properties props = BlockBehaviour.Properties.of(Material.STONE, color.getMapColor()).strength(1.25F, 4.2F).lightLevel((state) -> color.getLightValue());
        var shingles = DyenamicRegistry.registerBlock(prefix + "_shingles", () -> new Block(props), true);
        DyenamicRegistry.registerBlock(prefix + "_shingles_stairs", () -> new StairBlock(() -> shingles.get().defaultBlockState(), props), true);
        DyenamicRegistry.registerBlock(prefix + "_shingles_slab", () -> new SlabBlock(props), true);
    }

    public static void registerItems(DyenamicDyeColor color) {
        String prefix = "quark_" + color.getSerializedName();
        // .tab(CreativeModeTab.TAB_MATERIALS)
        DyenamicRegistry.registerItem(prefix + "_shard", () -> new Item(new Item.Properties()));
    }
}
