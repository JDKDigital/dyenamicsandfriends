package cy.jdkdigital.dyenamicsandfriends.registry;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.compat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import xyz.vsngamer.elevatorid.client.render.ElevatorBakedModel;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class DyenamicRegistry
{
    public static void setup() {
        QuarkCompat.setup();
    }

    public static void registerCompatBlocks() {
        for (DyenamicDyeColor color: DyenamicDyeColor.dyenamicValues()) {
            if (ModList.get().isLoaded("create")) {
                CreateCompat.registerBlocks(color);
            }
            if (ModList.get().isLoaded("createdeco")) {
                // decal item
                // zinc lamp
            }
            if (ModList.get().isLoaded("another_furniture")) {
                AnotherFurnitureCompat.registerBlocks(color);
            }
            if (ModList.get().isLoaded("botanypots")) {
                BotanyPotsCompat.registerBlocks(color);
            }
            if (ModList.get().isLoaded("comforts")) {
                ComfortsCompat.registerBlocks(color);
            }
            if (ModList.get().isLoaded("domesticationinnovation")) {
                // pet bed
            }
            if (ModList.get().isLoaded("elevatorid")) {
                ElevatoridCompat.registerBlocks(color);
            }
            if (ModList.get().isLoaded("farmersdelight")) {
                // canvas sign
            }
            if (ModList.get().isLoaded("reliquary")) {
                // pedestal
                // pedestal (display only)
            }
            if (ModList.get().isLoaded("snowyspirit")) {
                // glow lights
                // gumdrop
            }
            if (ModList.get().isLoaded("supplementaries")) {
                // blackboard
                // present
                // flag
                // candle holder
                // trapped present
            }
            if (ModList.get().isLoaded("waystones")) {
                // sharestone
            }
            if (ModList.get().isLoaded("quark")) {
                // rune
                QuarkCompat.registerBlocks(color);
                QuarkCompat.registerItems(color);
            }
            if (ModList.get().isLoaded("furnish")) {
                // sofa
                // showcase
                // awning
                // curtain
                // amphora
                // plate
                // paper lamp
            }
            if (ModList.get().isLoaded("skinnedlanterns")) {
                // paper lantern
                // paper soul lantern
                // ornament lantern
                // ornament soul lantern
            }
            if (ModList.get().isLoaded("nightlights")) {
                // mushroom night light
                // hanging lights
                // octopus night light
                // frog night light
                // fairy lights
            }
            if (ModList.get().isLoaded("cfm")) { // crayfish

            }
            if (ModList.get().isLoaded("chalk")) {
                // chalk
            }
        }
    }

    public static RegistryObject<? extends Block> registerBlock(final String name, final Supplier<? extends Block> sup, CreativeModeTab tab, boolean registerItem) {
        var block = DyenamicsAndFriends.BLOCKS.register(name, sup);
        if (registerItem) {
            registerItem(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
        }
        return block;
    }

    public static RegistryObject<? extends Block> registerBlock(final String name, final Supplier<? extends Block> sup, @Nullable Supplier<Item> itemSupplier) {
        var block = DyenamicsAndFriends.BLOCKS.register(name, sup);
        if (itemSupplier != null) {
            registerItem(name, itemSupplier);
        }
        return block;
    }

    public static RegistryObject<? extends Item> registerItem(final String name, @Nullable Supplier<Item> itemSupplier) {
        return DyenamicsAndFriends.ITEMS.register(name, itemSupplier);
    }

    public static <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> supplier) {
        return DyenamicsAndFriends.BLOCK_ENTITIES.register(id, supplier);
    }

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityType.BlockEntitySupplier<E> factory, Block... blocks) {
        return BlockEntityType.Builder.of(factory, blocks).build(null);
    }

    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        if (ModList.get().isLoaded("another_furniture")) {
            AnotherFurnitureCompat.registerBlockEntityRenderers(event);
        }
        if (ModList.get().isLoaded("botanypots")) {
            BotanyPotsCompat.registerBlockEntityRenderers(event);
        }
        if (ModList.get().isLoaded("comforts")) {
            ComfortsCompat.registerBlockEntityRenderers(event);
        }
    }

    public static void registerBlockRendering(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("another_furniture")) {
            AnotherFurnitureCompat.registerBlockRendering();
        }
        if (ModList.get().isLoaded("botanypots")) {
            BotanyPotsCompat.registerBlockRendering();
        }
        if (ModList.get().isLoaded("elevatorid")) {
            ElevatoridCompat.registerBlockRendering();
        }
        if (ModList.get().isLoaded("quark")) {
            QuarkCompat.registerBlockRendering();
        }
    }

    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (ModList.get().isLoaded("comforts")) {
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

    public static void onModelBake(ModelBakeEvent event) {
        if (ModList.get().isLoaded("elevatorid")) {
            event.getModelRegistry().entrySet().stream()
                .filter(entry -> "dyenamicsandfriends".equals(entry.getKey().getNamespace()) && entry.getKey().getPath().contains("_elevator"))
                .forEach(entry -> event.getModelRegistry().put(entry.getKey(), new ElevatorBakedModel(entry.getValue())));
        }
    }
}
