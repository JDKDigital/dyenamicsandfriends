package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import earth.terrarium.handcrafted.common.block.CushionBlock;
import earth.terrarium.handcrafted.common.item.CushionBlockItem;
import earth.terrarium.handcrafted.common.item.SheetItem;
import earth.terrarium.handcrafted.common.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class HandcraftedCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CUSHIONS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "handcrafted_" + color.getSerializedName();
        CUSHIONS.put(color, DyenamicRegistry.registerBlock(prefix + "_cushion", () -> new CushionBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel(state -> color.getLightValue())), () -> new CushionBlockItem(CUSHIONS.get(color).get(), new Item.Properties().tab(ModItems.ITEM_GROUP))));
    }

    public static void registerItems(DyenamicDyeColor color) {
        String prefix = "handcrafted_" + color.getSerializedName();
        DyenamicRegistry.registerItem(prefix + "_sheet", () -> new SheetItem(new Item.Properties().tab(ModItems.ITEM_GROUP)));
    }
}
