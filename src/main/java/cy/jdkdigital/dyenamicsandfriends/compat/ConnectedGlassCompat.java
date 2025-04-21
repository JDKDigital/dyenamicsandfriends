package cy.jdkdigital.dyenamicsandfriends.compat;

import com.supermartijn642.connectedglass.CGColoredGlassBlock;
import com.supermartijn642.connectedglass.CGColoredPaneBlock;
import com.supermartijn642.connectedglass.CGColoredTintedGlassBlock;
import com.supermartijn642.connectedglass.CGGlassType;
import com.supermartijn642.core.registry.ClientRegistrationHandler;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
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
            var block = DyenamicRegistry.registerBlock(prefix, () -> glassType.isTinted ? new CGColoredTintedGlassBlock(typeName + "_" + color.getSerializedName(), true, color.getAnalogue()) : new CGColoredGlassBlock(prefix + "_glass", true, color.getAnalogue()), true);
            GLASS_BLOCKS.add(block);
            if (glassType.hasPanes) {
                GLASS_PANES.add(DyenamicRegistry.registerBlock(prefix + "_pane", () -> new CGColoredPaneBlock((CGColoredGlassBlock) block.get()), true));
            }
        }
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
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

        public static void register(){
            ClientRegistrationHandler handler = ClientRegistrationHandler.get(DyenamicsAndFriends.MODID);

            // Set render type for all the blocks
            for(CGGlassType glassType : CGGlassType.values()){
                var typeName = glassType.name().toLowerCase(Locale.ROOT);
                // Register translucent render type for all the colored blocks
                for (DyenamicDyeColor color: DyenamicDyeColor.dyenamicValues()) {
                    String prefix = "connectedglass_" + typeName + "_" + color.getSerializedName();
                    var block = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, prefix));
                    handler.registerBlockModelTranslucentRenderType(() -> block);
                    if(glassType.hasPanes) {
                        var pane = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, prefix + "_pane"));
                        handler.registerBlockModelTranslucentRenderType(() -> pane);
                    }
                }
            }
        }
    }
}
