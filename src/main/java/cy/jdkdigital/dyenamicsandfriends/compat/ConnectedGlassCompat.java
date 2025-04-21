package cy.jdkdigital.dyenamicsandfriends.compat;

import com.supermartijn642.connectedglass.CGColoredGlassBlock;
import com.supermartijn642.connectedglass.CGColoredPaneBlock;
import com.supermartijn642.connectedglass.CGColoredTintedGlassBlock;
import com.supermartijn642.connectedglass.CGGlassType;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
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
            var block = DyenamicRegistry.registerBlock(prefix, () -> glassType.isTinted ? new CGColoredTintedGlassBlock(typeName + "_" + color.getSerializedName(), true, color.getAnalogue()) : new CGColoredGlassBlock(prefix + "_glass", true, color.getAnalogue()), true);
            GLASS_BLOCKS.add(block);
            if (glassType.hasPanes) {
                GLASS_PANES.add(DyenamicRegistry.registerBlock(prefix + "_pane", () -> new CGColoredPaneBlock((CGColoredGlassBlock) block.get()), true));
            }
        }
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            GLASS_BLOCKS.forEach(event::accept);
            GLASS_PANES.forEach(event::accept);
        }
    }
}
