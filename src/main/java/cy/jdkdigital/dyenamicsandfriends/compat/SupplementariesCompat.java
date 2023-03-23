package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsPresentBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsTrappedPresentBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsPresentBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsTrappedPresentBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.mehvahdjukaar.supplementaries.setup.ModSounds;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class SupplementariesCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> PRESENTS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> TRAPPED_PRESENTS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "supplementaries_" + color.getSerializedName();

        PRESENTS.put(color, DyenamicRegistry.registerBlock(prefix + "_present", () -> new DyenamicsPresentBlock(color, BlockBehaviour.Properties.of(Material.WOOL, color.getMapColor()).strength(1.0F).sound(ModSounds.PRESENT).lightLevel(state -> color.getLightValue()), DyenamicRegistry.registerBlockEntity(prefix + "_present", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsPresentBlockEntity((DyenamicsPresentBlock) PRESENTS.get(color).get(), pos, state), PRESENTS.get(color).get()))), () -> new BlockItem(PRESENTS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS))));
        TRAPPED_PRESENTS.put(color, DyenamicRegistry.registerBlock(prefix + "_trapped_present", () -> new DyenamicsTrappedPresentBlock(color, BlockBehaviour.Properties.of(Material.WOOL, color.getMapColor()).strength(1.0F).sound(ModSounds.PRESENT).lightLevel(state -> color.getLightValue()), DyenamicRegistry.registerBlockEntity(prefix + "_trapped_present", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsTrappedPresentBlockEntity((DyenamicsTrappedPresentBlock) TRAPPED_PRESENTS.get(color).get(), pos, state), TRAPPED_PRESENTS.get(color).get()))), () -> new BlockItem(TRAPPED_PRESENTS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE))));
    }

    public static void registerBlockRendering() {
    }

    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    }
}
