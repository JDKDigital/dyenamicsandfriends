package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import cy.jdkdigital.productivebees.ProductiveBees;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;

public class ProductiveBeesCompat
{
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> PETRIFIED_HONEY_BLOCKS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "productivebees_" + color.getSerializedName();
        PETRIFIED_HONEY_BLOCKS.put(color, DyenamicRegistry.registerBlock(prefix + "_petrified_honey", () -> new Block(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).lightLevel((state) -> color.getLightValue()).strength(0.3F).noOcclusion().sound(SoundType.BONE_BLOCK)), true));
    }

    public static void buildTabContents(net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(ProductiveBees.TAB_KEY)) {
            PETRIFIED_HONEY_BLOCKS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
        }
    }
}
