package cy.jdkdigital.dyenamicsandfriends.datagen;

import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider
{
    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
        super(output, future, provider, DyenamicsAndFriends.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
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

        if (ModList.get().isLoaded("luminax")) {
            for (String name: new String[]{"blocks", "stairs", "slabs", "walls", "pressure_plates", "buttons"}) {
                copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", name)), ItemTags.create(ResourceLocation.fromNamespaceAndPath("luminax", name)));
                copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_" + name)), ItemTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_" + name)));
            }
        }
    }

    @Override
    public String getName() {
        return "Dyenamics and Friends Item Tags Provider";
    }
}
