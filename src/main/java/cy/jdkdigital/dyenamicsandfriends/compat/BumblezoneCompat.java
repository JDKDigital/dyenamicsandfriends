package cy.jdkdigital.dyenamicsandfriends.compat;

import com.telepathicgrunt.the_bumblezone.blocks.StringCurtain;
import com.telepathicgrunt.the_bumblezone.blocks.SuperCandleBase;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class BumblezoneCompat
{
    static List<DeferredHolder<Block, ? extends Block>> CANDLES = new ArrayList<>();
    static List<DeferredHolder<Block, ? extends Block>> CURTAINS = new ArrayList<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "bumblezone_" + color.getSerializedName();
        // CreativeModeTab.TAB_DECORATIONS
        CANDLES.add(DyenamicRegistry.registerBlock(prefix + "_super_candle_base", SuperCandleBase::new, true));
        CURTAINS.add(DyenamicRegistry.registerBlock(prefix + "_string_curtain", StringCurtain::new, true));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.COLORED_BLOCKS)) {
            CANDLES.forEach(holder -> event.accept(holder.get()));
            CURTAINS.forEach(holder -> event.accept(holder.get()));
        }
    }
}
