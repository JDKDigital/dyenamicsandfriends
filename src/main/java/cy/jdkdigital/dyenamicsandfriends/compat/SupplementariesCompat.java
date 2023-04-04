package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.client.render.supplementaries.DyenamicsFlagBlockRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsFlagBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsPresentBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsTrappedPresentBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsFlagBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsPresentBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsTrappedPresentBlock;
import cy.jdkdigital.dyenamicsandfriends.common.item.supplementaries.DyenamicsFlagItem;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.mehvahdjukaar.supplementaries.common.block.blocks.CandleHolderBlock;
import net.mehvahdjukaar.supplementaries.common.items.FlagItem;
import net.mehvahdjukaar.supplementaries.setup.ModSounds;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class SupplementariesCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> PRESENTS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> TRAPPED_PRESENTS = new HashMap<>();
    public static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> FLAGS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CANDLE_HOLDERS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "supplementaries_" + color.getSerializedName();

        PRESENTS.put(color, DyenamicRegistry.registerBlock(prefix + "_present", () -> new DyenamicsPresentBlock(color, BlockBehaviour.Properties.of(Material.WOOL, color.getMapColor()).strength(1.0F).sound(ModSounds.PRESENT).lightLevel(state -> color.getLightValue()), DyenamicRegistry.registerBlockEntity(prefix + "_present", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsPresentBlockEntity((DyenamicsPresentBlock) PRESENTS.get(color).get(), pos, state), PRESENTS.get(color).get()))), () -> new BlockItem(PRESENTS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS))));
        TRAPPED_PRESENTS.put(color, DyenamicRegistry.registerBlock(prefix + "_trapped_present", () -> new DyenamicsTrappedPresentBlock(color, BlockBehaviour.Properties.of(Material.WOOL, color.getMapColor()).strength(1.0F).sound(ModSounds.PRESENT).lightLevel(state -> color.getLightValue()), DyenamicRegistry.registerBlockEntity(prefix + "_trapped_present", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsTrappedPresentBlockEntity((DyenamicsTrappedPresentBlock) TRAPPED_PRESENTS.get(color).get(), pos, state), TRAPPED_PRESENTS.get(color).get()))), () -> new BlockItem(TRAPPED_PRESENTS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE))));
//        FLAGS.put(color, DyenamicRegistry.registerBlock(prefix + "_flag", () -> new DyenamicsFlagBlock(color, BlockBehaviour.Properties.of(Material.WOOD, color.getMapColor()).strength(1.0F).noOcclusion().sound(SoundType.WOOD).lightLevel(state -> color.getLightValue()), DyenamicRegistry.registerBlockEntity(prefix + "_flag", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsFlagBlockEntity((DyenamicsFlagBlock) FLAGS.get(color).get(), pos, state), FLAGS.get(color).get()))), () -> new DyenamicsFlagItem(FLAGS.get(color).get(), new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS))));
        CANDLE_HOLDERS.put(color, DyenamicRegistry.registerBlock(prefix + "_candle_holder", () -> new CandleHolderBlock(color.getVanillaColor(), BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().sound(SoundType.LANTERN).lightLevel(state -> color.getLightValue())), () -> new BlockItem(CANDLE_HOLDERS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS))));
    }

    public static class Client
    {
        public static void registerBlockRendering() {
            CANDLE_HOLDERS.values().forEach(registryObject -> {
                if (registryObject.get() instanceof CandleHolderBlock candleHolder) {
                    ItemBlockRenderTypes.setRenderLayer(candleHolder, RenderType.cutout());
                }
            });
        }

        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
//            FLAGS.values().forEach(registryObject -> {
//                if (registryObject.get() instanceof DyenamicsFlagBlock flag) {
//                    event.registerBlockEntityRenderer(flag.getBlockEntitySupplier().get(), DyenamicsFlagBlockRenderer::new);
//                }
//            });
        }
    }
}
