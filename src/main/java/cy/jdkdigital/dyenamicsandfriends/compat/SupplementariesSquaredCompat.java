//package cy.jdkdigital.dyenamicsandfriends.compat;
//
//import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
//import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsPresentBlockEntity;
//import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsTrappedPresentBlockEntity;
//import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsPresentBlock;
//import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsTrappedPresentBlock;
//import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
//import net.mehvahdjukaar.supplementaries.common.block.blocks.CandleHolderBlock;
//import net.mehvahdjukaar.supplementaries.common.block.blocks.SackBlock;
//import net.mehvahdjukaar.supplementaries.reg.ModSounds;
//import net.minecraft.client.renderer.ItemBlockRenderTypes;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.world.item.BlockItem;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.SoundType;
//import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraftforge.registries.RegistryObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SupplementariesSquaredCompat
//{
//    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CANDLE_HOLDERS = new HashMap<>();
//    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> SACKS = new HashMap<>();
//
//    public static void registerBlocks(DyenamicDyeColor color) {
//        String prefix = "suppsquared_" + color.getSerializedName();
//
//        CANDLE_HOLDERS.put(color, DyenamicRegistry.registerBlock(prefix + "_gold_candle_holder", () -> new CandleHolderBlock(color.getAnalogue(), BlockBehaviour.Properties.of().mapColor(color.getMapColor()).noCollission().instabreak().sound(SoundType.LANTERN).lightLevel(state -> color.getLightValue())), () -> new BlockItem(CANDLE_HOLDERS.get(color).get(), new Item.Properties())));
//        SACKS.put(color, DyenamicRegistry.registerBlock(prefix + "_sack", () -> new SackBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(0.8F).sound(ModSounds.SACK).lightLevel(state -> color.getLightValue())), () -> new BlockItem(SACKS.get(color).get(), new Item.Properties())));
//    }
//
//    public static class Client
//    {
//        public static void registerBlockRendering() {
//            CANDLE_HOLDERS.values().forEach(registryObject -> {
//                if (registryObject.get() instanceof CandleHolderBlock candleHolder) {
//                    ItemBlockRenderTypes.setRenderLayer(candleHolder, RenderType.cutout());
//                }
//            });
//        }
//    }
//}
