package cy.jdkdigital.dyenamicsandfriends.datagen;

import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.compat.ProductiveMetalworksCompat;
import cy.jdkdigital.productivemetalworks.ProductiveMetalworks;
import cy.jdkdigital.productivemetalworks.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider
{
    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
        super(output, future, provider, DyenamicsAndFriends.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
//        copy(BlockTags.LEAVES, ItemTags.LEAVES);
//        copy(BlockTags.LOGS, ItemTags.LOGS);
//        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
//        copy(BlockTags.PLANKS, ItemTags.PLANKS);
//        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
//        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
//        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
//        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
//        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
//        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
//        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
//        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
//        copy(Tags.Blocks.BOOKSHELVES, Tags.Items.BOOKSHELVES);
//        copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
//        copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);

        if (ModList.get().isLoaded("productivemetalworks")) {
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_controllers")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_controllers")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_drains")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_drains")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_tanks")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_tanks")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_windows")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_windows")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "fire_bricks")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "fire_bricks")));
        }

        if (ModList.get().isLoaded("crystalix")) {
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("crystalix", "glass")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("crystalix", "glass")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("crystalix", "clear")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("crystalix", "clear")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("crystalix", "bordered")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("crystalix", "bordered")));
        }
    }

    @Override
    public String getName() {
        return "Dyenamics and Friends Item Tags Provider";
    }
}
