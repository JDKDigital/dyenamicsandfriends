package cy.jdkdigital.dyenamicsandfriends.compat;

import com.supermartijn642.connectedglass.*;
import com.supermartijn642.core.registry.ClientRegistrationHandler;
import cy.jdkdigital.dyenamics.common.block.DyenamicStainedGlassBlock;
import cy.jdkdigital.dyenamics.common.block.DyenamicStainedGlassPane;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.common.block.TintedDyenamicStainedGlassBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConnectedGlassCompat
{
    public static final List<DeferredHolder<Block, ? extends Block>> GLASS_BLOCKS = new ArrayList<>();
    public static final List<DeferredHolder<Block, ? extends Block>> GLASS_PANES = new ArrayList<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        for (CGGlassType glassType : CGGlassType.values()) {
            // borderless, clear, scratched, tinted
            var typeName = glassType.name().toLowerCase(Locale.ROOT);
            String prefix = "connectedglass_" + typeName + "_" + color.getSerializedName();
            var block = DyenamicRegistry.registerBlock(prefix, () -> glassType.isTinted ?
                    new TintedDyenamicStainedGlassBlock(color, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)) :
                    new DyenamicStainedGlassBlock(color, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)), true);
            GLASS_BLOCKS.add(block);
            if (glassType.hasPanes) {
                GLASS_PANES.add(DyenamicRegistry.registerBlock(prefix + "_pane", () -> new DyenamicStainedGlassPane(color, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE)), true));
            }
        }
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.parse("connectedglass:connectedglass"));
        if (event.getTabKey().equals(key)) {
            GLASS_BLOCKS.forEach(holder -> event.accept(holder.get()));
            GLASS_PANES.forEach(holder -> event.accept(holder.get()));
        }
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            GLASS_BLOCKS.forEach(( holder) -> ItemBlockRenderTypes.setRenderLayer(holder.get(), RenderType.translucent()));
            GLASS_PANES.forEach(( holder) -> ItemBlockRenderTypes.setRenderLayer(holder.get(), RenderType.translucent()));
        }
    }
}
