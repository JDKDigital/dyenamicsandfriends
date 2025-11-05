package cy.jdkdigital.dyenamicsandfriends.datagen;

import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.compat.ChromaCarvingsCompat;
import cy.jdkdigital.dyenamicsandfriends.compat.CookingForBlockheadsCompat;
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
        copy(BlockTags.DOORS, ItemTags.DOORS);
        copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);

        if (ModList.get().isLoaded("productivemetalworks")) {
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_controllers")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_controllers")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_drains")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_drains")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_tanks")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_tanks")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_capacitors")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_capacitors")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_windows")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_windows")));
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "fire_bricks")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "fire_bricks")));
        }

        if (ModList.get().isLoaded("luminax")) {
            for (String name: new String[]{"blocks", "stairs", "slabs", "walls", "pressure_plates", "buttons"}) {
                copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", name)), ItemTags.create(ResourceLocation.fromNamespaceAndPath("luminax", name)));
                copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_" + name)), ItemTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_" + name)));
            }
        }

        if (ModList.get().isLoaded("cookingforblockheads")) {
            var CABINETS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:cabinets")));
            CookingForBlockheadsCompat.CABINETS.forEach((dyenamicDyeColor, holder) -> CABINETS.addOptional(holder.getId()));

            var CONNECTORS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:connectors")));
            CookingForBlockheadsCompat.CONNECTORS.forEach((dyenamicDyeColor, holder) -> CONNECTORS.addOptional(holder.getId()));

            var COOKING_TABLES = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:cooking_tables")));
            CookingForBlockheadsCompat.COOKING_TABLES.forEach((dyenamicDyeColor, holder) -> COOKING_TABLES.addOptional(holder.getId()));

            var COUNTERS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:counters")));
            CookingForBlockheadsCompat.COUNTERS.forEach((dyenamicDyeColor, holder) -> COUNTERS.addOptional(holder.getId()));

            var FRIDGES = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:fridges")));
            CookingForBlockheadsCompat.FRIDGES.forEach((dyenamicDyeColor, holder) -> FRIDGES.addOptional(holder.getId()));

            var OVENS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:ovens")));
            CookingForBlockheadsCompat.OVENS.forEach((dyenamicDyeColor, holder) -> OVENS.addOptional(holder.getId()));

            var SINKS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:sinks")));
            CookingForBlockheadsCompat.SINKS.forEach((dyenamicDyeColor, holder) -> SINKS.addOptional(holder.getId()));

            var DYED_CABINETS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:dyed_cabinets")));
            CookingForBlockheadsCompat.CABINETS.forEach((dyenamicDyeColor, holder) -> DYED_CABINETS.addOptional(holder.getId()));

            var DYED_CONNECTORS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:dyed_connectors")));
            CookingForBlockheadsCompat.CONNECTORS.forEach((dyenamicDyeColor, holder) -> DYED_CONNECTORS.addOptional(holder.getId()));

            var DYED_COOKING_TABLES = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:dyed_cooking_tables")));
            CookingForBlockheadsCompat.COOKING_TABLES.forEach((dyenamicDyeColor, holder) -> DYED_COOKING_TABLES.addOptional(holder.getId()));

            var DYED_COUNTERS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:dyed_counters")));
            CookingForBlockheadsCompat.COUNTERS.forEach((dyenamicDyeColor, holder) -> DYED_COUNTERS.addOptional(holder.getId()));

            var DYED_FRIDGES = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:dyed_fridges")));
            CookingForBlockheadsCompat.FRIDGES.forEach((dyenamicDyeColor, holder) -> DYED_FRIDGES.addOptional(holder.getId()));

            var DYED_OVENS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:dyed_ovens")));
            CookingForBlockheadsCompat.OVENS.forEach((dyenamicDyeColor, holder) -> DYED_OVENS.addOptional(holder.getId()));

            var DYED_SINKS = tag(ItemTags.create(ResourceLocation.parse("cookingforblockheads:dyed_sinks")));
            CookingForBlockheadsCompat.SINKS.forEach((dyenamicDyeColor, holder) -> DYED_SINKS.addOptional(holder.getId()));
        }

        if (ModList.get().isLoaded("botanypots")) {
            copy(BlockTags.create(ResourceLocation.fromNamespaceAndPath("botanypots", "botany_pots")), ItemTags.create(ResourceLocation.fromNamespaceAndPath("botanypots", "botany_pots")));
        }

        if (ModList.get().isLoaded("justblahaj")) {
            var HAJ = tag(ItemTags.create(ResourceLocation.parse("justblahaj:haj")));

        }

        if (ModList.get().isLoaded("chromacarvings")) {
            copy(Tags.Blocks.PUMPKINS, Tags.Items.PUMPKINS);
            copy(Tags.Blocks.PUMPKINS_CARVED, Tags.Items.PUMPKINS_CARVED);
            copy(Tags.Blocks.PUMPKINS_JACK_O_LANTERNS, Tags.Items.PUMPKINS_JACK_O_LANTERNS);

            var PUMPKIN_CROPS = tag(Tags.Items.CROPS_PUMPKIN);
            var PUMPKINS_NORMAL = tag(Tags.Items.PUMPKINS_NORMAL);
            var EQUIPPABLE = tag(ItemTags.EQUIPPABLE_ENCHANTABLE);
            var VANISHING = tag(ItemTags.VANISHING_ENCHANTABLE);
            ChromaCarvingsCompat.PUMPKINS.forEach((dyenamicDyeColor, holder) -> PUMPKIN_CROPS.addOptional(holder.getId()));
            ChromaCarvingsCompat.PUMPKINS.forEach((dyenamicDyeColor, holder) -> PUMPKINS_NORMAL.addOptional(holder.getId()));
            ChromaCarvingsCompat.CARVED_PUMPKINS.forEach((dyenamicDyeColor, holder) -> EQUIPPABLE.addOptional(holder.getId()));
            ChromaCarvingsCompat.CARVED_PUMPKINS.forEach((dyenamicDyeColor, holder) -> VANISHING.addOptional(holder.getId()));
        }
    }

    @Override
    public String getName() {
        return "Dyenamics and Friends Item Tags Provider";
    }
}
