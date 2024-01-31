package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import earth.terrarium.handcrafted.common.blocks.CushionBlock;
import earth.terrarium.handcrafted.common.constants.ConstantComponents;
import earth.terrarium.handcrafted.common.items.TooltipItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class HandcraftedCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CUSHIONS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Item>> SHEETS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "handcrafted_" + color.getSerializedName();
        CUSHIONS.put(color, DyenamicRegistry.registerBlock(prefix + "_cushion", () -> new CushionBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel(state -> color.getLightValue())), () -> new ItemNameBlockItem(CUSHIONS.get(color).get(), new Item.Properties())));
    }

    public static void registerItems(DyenamicDyeColor color) {
        String prefix = "handcrafted_" + color.getSerializedName();
        SHEETS.put(color, DyenamicRegistry.registerItem(prefix + "_sheet", () -> new TooltipItem(ConstantComponents.PLACE_ON_FURNITURE, new Item.Properties())));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("handcrafted:main"));
        if (event.getTabKey().equals(key)) {
            CUSHIONS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
            SHEETS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
        }
    }
}
