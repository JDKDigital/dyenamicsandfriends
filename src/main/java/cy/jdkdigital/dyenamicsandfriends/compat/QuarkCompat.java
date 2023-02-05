package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.common.blocks.DyenamicStainedGlassBlock;
import cofh.dyenamics.common.blocks.DyenamicStainedGlassPane;
import cofh.dyenamics.core.init.BlockInit;
import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.loot.modifier.StainedGlassBlockLootModifier;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class QuarkCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> FRAMED_GLASS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> FRAMED_GLASS_PANES = new HashMap<>();

    public static void setup() {
        DyenamicsAndFriends.LOOT_SERIALIZERS.register("shard", StainedGlassBlockLootModifier.CODEC);
    }

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "quark_" + color.getSerializedName();
        FRAMED_GLASS.put(color, DyenamicRegistry.registerBlock(prefix + "_framed_glass", () -> new DyenamicStainedGlassBlock(color, BlockBehaviour.Properties.of(Material.GLASS, color.getMapColor()).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a, b, c, d) -> false).isRedstoneConductor((a, b, c) -> false).isSuffocating((a, b, c) -> false).isViewBlocking((a, b, c) -> false).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS, true));
        FRAMED_GLASS_PANES.put(color, DyenamicRegistry.registerBlock(prefix + "_framed_glass_pane", () -> new DyenamicStainedGlassPane(color, BlockBehaviour.Properties.of(Material.GLASS, color.getMapColor()).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a, b, c, d) -> false).isRedstoneConductor((a, b, c) -> false).isSuffocating((a, b, c) -> false).isViewBlocking((a, b, c) -> false).lightLevel(state -> color.getLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS, true));

        var terracotta = BlockInit.DYED_BLOCKS.get(color.getSerializedName()).get("terracotta");
        var shingles = DyenamicRegistry.registerBlock(prefix + "_shingles", () -> new Block(BlockBehaviour.Properties.copy(terracotta.get())), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_shingles_stairs", () -> new StairBlock(() -> shingles.get().defaultBlockState(), BlockBehaviour.Properties.copy(terracotta.get())), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
        DyenamicRegistry.registerBlock(prefix + "_shingles_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(terracotta.get())), CreativeModeTab.TAB_BUILDING_BLOCKS, true);
    }

    public static void registerItems(DyenamicDyeColor color) {
        String prefix = "quark_" + color.getSerializedName();
        DyenamicRegistry.registerItem(prefix + "_shard", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    }

    public static void registerBlockRendering() {
        FRAMED_GLASS.values().forEach(registryObject -> {
            if (registryObject.get() instanceof DyenamicStainedGlassBlock glass) {
                ItemBlockRenderTypes.setRenderLayer(glass, RenderType.translucent());
            }
        });
        FRAMED_GLASS_PANES.values().forEach(registryObject -> {
            if (registryObject.get() instanceof DyenamicStainedGlassPane pane) {
                ItemBlockRenderTypes.setRenderLayer(pane, RenderType.translucent());
            }
        });
    }
}
