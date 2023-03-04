//package cy.jdkdigital.dyenamicsandfriends.compat;
//
//import cofh.dyenamics.core.util.DyenamicDyeColor;
//import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsFluidCisternBlock;
//import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsCisternBlockEntity;
//import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
//import knightminer.ceramics.client.renderer.CisternBlockEntityRenderer;
//import knightminer.ceramics.items.CrackableBlockItem;
//import knightminer.ceramics.items.FixedTooltipBlockItem;
//import net.minecraft.client.renderer.ItemBlockRenderTypes;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.world.item.BlockItem;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.material.Material;
//import net.minecraftforge.client.event.EntityRenderersEvent;
//import net.minecraftforge.registries.RegistryObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class CeramicsCompat
//{
//    public static Map<DyenamicDyeColor, RegistryObject<? extends Block>> PORCELAIN_BLOCKS = new HashMap<>();
//    public static Map<DyenamicDyeColor, RegistryObject<? extends Block>> PORCELAIN_CISTERNS = new HashMap<>();
//    public static Map<DyenamicDyeColor, RegistryObject<? extends Block>> COLORED_CISTERNS = new HashMap<>();
//
//    public static void registerBlocks(DyenamicDyeColor color) {
//        String prefix = "ceramics_" + color.getSerializedName();
//
//        final BlockBehaviour.Properties properties = Block.Properties.of(Material.STONE, color.getMapColor()).strength(1.25F, 4.2F).noOcclusion().lightLevel(state -> color.getLightValue());
//        PORCELAIN_BLOCKS.put(color, DyenamicRegistry.registerBlock(prefix + "_porcelain", () -> new Block(properties), () -> new BlockItem(PORCELAIN_BLOCKS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS))));
//        COLORED_CISTERNS.put(color, DyenamicRegistry.registerBlock(prefix + "_terracotta_cistern", () -> new DyenamicsFluidCisternBlock(properties.noOcclusion().randomTicks(), true, DyenamicRegistry.registerBlockEntity(prefix + "_terracotta_cistern", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsCisternBlockEntity((DyenamicsFluidCisternBlock) COLORED_CISTERNS.get(color).get(), pos, state, true), COLORED_CISTERNS.get(color).get()))), () -> new CrackableBlockItem(COLORED_CISTERNS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS), "terracotta_cistern.tooltip")));
//        PORCELAIN_CISTERNS.put(color, DyenamicRegistry.registerBlock(prefix + "_porcelain_cistern", () -> new DyenamicsFluidCisternBlock(properties.noOcclusion(), false, DyenamicRegistry.registerBlockEntity(prefix + "_porcelain_cistern", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsCisternBlockEntity((DyenamicsFluidCisternBlock) PORCELAIN_CISTERNS.get(color).get(), pos, state, false), PORCELAIN_CISTERNS.get(color).get()))), () -> new FixedTooltipBlockItem(PORCELAIN_CISTERNS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS), "porcelain_cistern.tooltip")));
//    }
//
//    public static class Client
//    {
//        public static void registerBlockRendering() {
//            COLORED_CISTERNS.values().forEach((cistern -> ItemBlockRenderTypes.setRenderLayer(cistern.get(), RenderType.cutout())));
//        }
//
//        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
//            COLORED_CISTERNS.values().forEach(registryObject -> {
//                if (registryObject.get() instanceof DyenamicsFluidCisternBlock cistern) {
//                    event.registerBlockEntityRenderer(cistern.getBlockEntitySupplier().get(), CisternBlockEntityRenderer::new);
//                }
//            });
//            PORCELAIN_CISTERNS.values().forEach(registryObject -> {
//                if (registryObject.get() instanceof DyenamicsFluidCisternBlock cistern) {
//                    event.registerBlockEntityRenderer(cistern.getBlockEntitySupplier().get(), CisternBlockEntityRenderer::new);
//                }
//            });
//        }
//    }
//}
