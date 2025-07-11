package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import cy.jdkdigital.productivemetalworks.registry.MetalworksRegistrator;
import justblahaj.block.BlahajBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;

public class JustBlahajCompat
{
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> HAJ = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "just_blahaj_" + color.getSerializedName();
        HAJ.put(color, DyenamicRegistry.registerBlock(prefix + "_blahaj", BlahajBlock::new, true));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.parse("just_blahaj:just_blahaj"));
        if (event.getTabKey().equals(key)) {
            HAJ.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
        }
    }
}
