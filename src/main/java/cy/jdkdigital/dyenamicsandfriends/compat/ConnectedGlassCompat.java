package cy.jdkdigital.dyenamicsandfriends.compat;

import com.supermartijn642.connectedglass.CGColoredGlassBlock;
import com.supermartijn642.connectedglass.CGColoredPaneBlock;
import com.supermartijn642.connectedglass.CGColoredTintedGlassBlock;
import com.supermartijn642.connectedglass.CGGlassType;
import cy.jdkdigital.dyenamics.common.blocks.DyenamicStainedGlassBlock;
import cy.jdkdigital.dyenamics.common.blocks.DyenamicStainedGlassPane;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.TintedDyenamicStainedGlassBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class ConnectedGlassCompat
{
    public static final List<RegistryObject<? extends Block>> GLASS_BLOCKS = new ArrayList<>();
    public static final List<RegistryObject<? extends Block>> GLASS_PANES = new ArrayList<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        for (CGGlassType glassType : CGGlassType.values()) {
            // borderless, clear, scratched, tinted
            var typeName = glassType.name().toLowerCase(Locale.ROOT);
            String prefix = "connectedglass_" + typeName + "_" + color.getSerializedName();
            var block = DyenamicRegistry.registerBlock(prefix, () -> glassType.isTinted ?
                    new TintedDyenamicStainedGlassBlock(color, BlockBehaviour.Properties.copy(Blocks.GLASS)) :
                    new DyenamicStainedGlassBlock(color, BlockBehaviour.Properties.copy(Blocks.GLASS)), true);
            GLASS_BLOCKS.add(block);
            if (glassType.hasPanes) {
                GLASS_PANES.add(DyenamicRegistry.registerBlock(prefix + "_pane", () -> new DyenamicStainedGlassPane(color, BlockBehaviour.Properties.copy(Blocks.GLASS_PANE)), true));
            }
        }
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            GLASS_BLOCKS.forEach(event::accept);
            GLASS_PANES.forEach(event::accept);
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
