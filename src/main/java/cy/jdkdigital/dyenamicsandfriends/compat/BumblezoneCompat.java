package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.telepathicgrunt.the_bumblezone.blocks.StringCurtain;
import com.telepathicgrunt.the_bumblezone.blocks.SuperCandleBase;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.item.CreativeModeTab;

public class BumblezoneCompat
{
    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "bumblezone_" + color.getSerializedName();
        // CreativeModeTab.TAB_DECORATIONS
        DyenamicRegistry.registerBlock(prefix + "_super_candle_base", SuperCandleBase::new, true);
        DyenamicRegistry.registerBlock(prefix + "_string_curtain", StringCurtain::new, true);
    }
}
