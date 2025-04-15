package cy.jdkdigital.dyenamicsandfriends.compat;

import com.illusivesoulworks.comforts.client.renderer.BaseComfortsBlockEntityRenderer;
import com.illusivesoulworks.comforts.common.item.HammockItem;
import com.illusivesoulworks.comforts.common.item.SleepingBagItem;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.client.render.comforts.DyenamicsHammockBlockEntityRenderer;
import cy.jdkdigital.dyenamicsandfriends.client.render.comforts.DyenamicsSleepingBagBlockEntityRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.comforts.DyenamicsHammockBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.comforts.DyenamicsSleepingBagBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsHammockBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsSleepingBagBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ComfortsCompat
{
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> HAMMOCKS = new HashMap<>();
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> SLEEPING_BAGS = new HashMap<>();

    public static Supplier<BlockEntityType<DyenamicsHammockBlockEntity>> HAMMOCK_BLOCK_ENTITY;
    public static Supplier<BlockEntityType<DyenamicsSleepingBagBlockEntity>> SLEEPING_BAG_BLOCK_ENTITY;

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "comforts_" + color.getSerializedName();
        HAMMOCKS.put(color, DyenamicRegistry.registerBlock(prefix + "_hammock", () -> new DyenamicsHammockBlock(color), () -> new HammockItem(HAMMOCKS.get(color).get())));
        SLEEPING_BAGS.put(color, DyenamicRegistry.registerBlock(prefix + "_sleeping_bag", () -> new DyenamicsSleepingBagBlock(color), () -> new SleepingBagItem(SLEEPING_BAGS.get(color).get())));

        HAMMOCK_BLOCK_ENTITY = DyenamicRegistry.registerBlockEntity(prefix + "_hammock", () -> DyenamicRegistry.createBlockEntityType(DyenamicsHammockBlockEntity::new, HAMMOCKS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0])));
        SLEEPING_BAG_BLOCK_ENTITY = DyenamicRegistry.registerBlockEntity(prefix + "_sleeping_bag", () -> DyenamicRegistry.createBlockEntityType(DyenamicsSleepingBagBlockEntity::new, SLEEPING_BAGS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0])));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.COLORED_BLOCKS) || event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            HAMMOCKS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            SLEEPING_BAGS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
        }
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            HAMMOCKS.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsHammockBlock) {
                    event.registerBlockEntityRenderer(HAMMOCK_BLOCK_ENTITY.get(), DyenamicsHammockBlockEntityRenderer::new);
                }
            });
            SLEEPING_BAGS.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsSleepingBagBlock) {
                    event.registerBlockEntityRenderer(SLEEPING_BAG_BLOCK_ENTITY.get(), DyenamicsSleepingBagBlockEntityRenderer::new);
                }
            });
        }

        public static void layerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
            evt.registerLayerDefinition(BaseComfortsBlockEntityRenderer.SLEEPING_BAG_HEAD,
                    DyenamicsSleepingBagBlockEntityRenderer::createHeadLayer);
            evt.registerLayerDefinition(BaseComfortsBlockEntityRenderer.SLEEPING_BAG_FOOT,
                    DyenamicsSleepingBagBlockEntityRenderer::createFootLayer);
            evt.registerLayerDefinition(BaseComfortsBlockEntityRenderer.HAMMOCK_HEAD,
                    DyenamicsHammockBlockEntityRenderer::createHeadLayer);
            evt.registerLayerDefinition(BaseComfortsBlockEntityRenderer.HAMMOCK_FOOT,
                    DyenamicsHammockBlockEntityRenderer::createFootLayer);
        }
    }
}
