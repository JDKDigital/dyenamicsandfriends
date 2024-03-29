package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.client.render.comforts.DyenamicsHammockBlockRenderer;
import cy.jdkdigital.dyenamicsandfriends.client.render.comforts.DyenamicsSleepingBagBlockRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.comforts.DyenamicsHammockBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.comforts.DyenamicsSleepingBagBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsHammockBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsSleepingBagBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.item.DyenamicsHammockItem;
import cy.jdkdigital.dyenamicsandfriends.common.item.DyenamicsSleepingBagItem;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ComfortsCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> HAMMOCKS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> SLEEPING_BAGS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "comforts_" + color.getSerializedName();
        HAMMOCKS.put(color, DyenamicRegistry.registerBlock(prefix + "_hammock", () -> new DyenamicsHammockBlock(color, BlockBehaviour.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.1F).lightLevel(state -> color.getLightValue()), DyenamicRegistry.registerBlockEntity(prefix + "_hammock", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsHammockBlockEntity((DyenamicsHammockBlock) HAMMOCKS.get(color).get(), pos, state), HAMMOCKS.get(color).get()))), () -> new DyenamicsHammockItem(HAMMOCKS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC))));
        SLEEPING_BAGS.put(color, DyenamicRegistry.registerBlock(prefix + "_sleeping_bag", () -> new DyenamicsSleepingBagBlock(color, BlockBehaviour.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.1F).lightLevel(state -> color.getLightValue()), DyenamicRegistry.registerBlockEntity(prefix + "_sleeping_bag", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsSleepingBagBlockEntity((DyenamicsSleepingBagBlock) SLEEPING_BAGS.get(color).get(), pos, state), SLEEPING_BAGS.get(color).get()))), () -> new DyenamicsSleepingBagItem(SLEEPING_BAGS.get(color).get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC))));
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            HAMMOCKS.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsHammockBlock hammock) {
                    event.registerBlockEntityRenderer(hammock.getBlockEntitySupplier().get(), DyenamicsHammockBlockRenderer::new);
                }
            });
            SLEEPING_BAGS.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsSleepingBagBlock hammock) {
                    event.registerBlockEntityRenderer(hammock.getBlockEntitySupplier().get(), DyenamicsSleepingBagBlockRenderer::new);
                }
            });
        }
    }

    public static void stitchTextures(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location() == InventoryMenu.BLOCK_ATLAS) {
            for (final DyenamicDyeColor color : DyenamicDyeColor.values()) {
                if (color.getId() > 15) {
                    event.addSprite(new ResourceLocation(DyenamicsAndFriends.MODID, "entity/comforts/hammock/" + color.getSerializedName()));
                    event.addSprite(new ResourceLocation(DyenamicsAndFriends.MODID, "entity/comforts/sleeping_bag/" + color.getSerializedName()));
                }
            }
        }
    }
}
