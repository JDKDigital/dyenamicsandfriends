package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.illusivesoulworks.comforts.common.item.HammockItem;
import com.illusivesoulworks.comforts.common.item.SleepingBagItem;
import cy.jdkdigital.dyenamicsandfriends.client.render.DyenamicsHammockBlockRenderer;
import cy.jdkdigital.dyenamicsandfriends.client.render.DyenamicsSleepingBagBlockRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsHammockBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsSleepingBagBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsHammockBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsSleepingBagBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import earth.terrarium.handcrafted.common.block.CushionBlock;
import earth.terrarium.handcrafted.common.item.CushionBlockItem;
import earth.terrarium.handcrafted.common.item.SheetItem;
import earth.terrarium.handcrafted.common.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
