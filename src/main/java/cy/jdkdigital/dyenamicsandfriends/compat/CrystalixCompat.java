package cy.jdkdigital.dyenamicsandfriends.compat;

import com.satherov.crystalix.content.CrystalixRegistry;
import com.satherov.crystalix.content.block.CrystalixGlass;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.client.render.comforts.DyenamicsHammockBlockEntityRenderer;
import cy.jdkdigital.dyenamicsandfriends.client.render.comforts.DyenamicsSleepingBagBlockEntityRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.comforts.DyenamicsHammockBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.comforts.DyenamicsSleepingBagBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.crystalix.DyenamicsCrystalixGlass;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import cy.jdkdigital.productivemetalworks.common.block.*;
import cy.jdkdigital.productivemetalworks.registry.MetalworksRegistrator;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CrystalixCompat
{
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> GLASS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> CLEAR = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> BORDERED = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "crystalix_" + color.getSerializedName();
        GLASS.put(color, DyenamicRegistry.registerBlock(prefix + "_crystalix_glass", () -> new DyenamicsCrystalixGlass(color), true));
        CLEAR.put(color, DyenamicRegistry.registerBlock(prefix + "_clear_crystalix_glass", () -> new DyenamicsCrystalixGlass(color), true));
        BORDERED.put(color, DyenamicRegistry.registerBlock(prefix + "_bordered_crystalix_glass", () -> new DyenamicsCrystalixGlass(color), true));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CrystalixRegistry.CREATIVE_TAB.getKey())) {
            Arrays.stream(DyenamicDyeColor.dyenamicValues()).forEach(dyenamicDyeColor -> {
                event.accept(GLASS.get(dyenamicDyeColor).get());
                event.accept(CLEAR.get(dyenamicDyeColor).get());
                event.accept(BORDERED.get(dyenamicDyeColor).get());
            });
        }
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            GLASS.forEach((dyenamicDyeColor, registryObject) -> ItemBlockRenderTypes.setRenderLayer(registryObject.get(), RenderType.translucent()));
            CLEAR.forEach((dyenamicDyeColor, registryObject) -> ItemBlockRenderTypes.setRenderLayer(registryObject.get(), RenderType.translucent()));
            BORDERED.forEach((dyenamicDyeColor, registryObject) -> ItemBlockRenderTypes.setRenderLayer(registryObject.get(), RenderType.translucent()));
        }
    }
}
