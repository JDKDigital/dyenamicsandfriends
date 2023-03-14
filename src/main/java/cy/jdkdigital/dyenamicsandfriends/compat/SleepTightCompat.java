package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.client.render.sleep_tight.DyenamicsHammockBlockTileRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.sleep_tight.DyenamicsHammockBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.sleep_tight.DyenamicsHammockBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SleepTightCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> HAMMOCKS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "sleep_tight_" + color.getSerializedName();
        HAMMOCKS.put(color, DyenamicRegistry.registerBlock(prefix + "_hammock", () -> new DyenamicsHammockBlock(color, DyenamicRegistry.registerBlockEntity(prefix + "_hammock", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsHammockBlockEntity((DyenamicsHammockBlock) HAMMOCKS.get(color).get(), pos, state), HAMMOCKS.get(color).get()))), () -> new BlockItem(HAMMOCKS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS))));
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            HAMMOCKS.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsHammockBlock hammock) {
                    event.registerBlockEntityRenderer(hammock.getBlockEntitySupplier().get(), DyenamicsHammockBlockTileRenderer::new);
                }
            });
        }
    }

    public static void stitchTextures(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(DyenamicsHammockBlockTileRenderer.BED_SHEET)) {
            Arrays.stream(DyenamicsHammockBlockTileRenderer.HAMMOCK_TEXTURES).forEach((e) -> {
                event.addSprite(e.texture());
            });
        }
    }
}
