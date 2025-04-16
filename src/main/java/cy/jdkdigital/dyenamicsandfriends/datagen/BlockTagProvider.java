package cy.jdkdigital.dyenamicsandfriends.datagen;

import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.compat.CrystalixCompat;
import cy.jdkdigital.dyenamicsandfriends.compat.ProductiveMetalworksCompat;
import cy.jdkdigital.productivemetalworks.ProductiveMetalworks;
import cy.jdkdigital.productivemetalworks.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends BlockTagsProvider
{
    public BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, DyenamicsAndFriends.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        var axeMineable = tag(BlockTags.MINEABLE_WITH_AXE);
//        var storageBlocks = tag(Tags.Blocks.STORAGE_BLOCKS);
//        var flowers = tag(BlockTags.FLOWERS);
//        var planks = tag(BlockTags.PLANKS);
//        var logs = tag(BlockTags.LOGS);
//        var logsThatBurn = tag(BlockTags.LOGS_THAT_BURN);
//        var sapling = tag(BlockTags.SAPLINGS);
//        var leaves = tag(BlockTags.LEAVES);
//        var slabs = tag(BlockTags.WOODEN_SLABS);
//        var pressurePlates = tag(BlockTags.WOODEN_PRESSURE_PLATES);
//        var stairs = tag(BlockTags.WOODEN_STAIRS);
//        var fences = tag(BlockTags.WOODEN_FENCES);
//        var fenceGates = tag(BlockTags.FENCE_GATES);
//        var buttons = tag(BlockTags.WOODEN_BUTTONS);
//        var doors = tag(BlockTags.WOODEN_DOORS);
//        var trapdoors = tag(BlockTags.WOODEN_TRAPDOORS);
//        var bookshelves = tag(Tags.Blocks.BOOKSHELVES);
//        var enchantment = tag(BlockTags.ENCHANTMENT_POWER_PROVIDER);
//        var signs = tag(BlockTags.STANDING_SIGNS);
//        var hangingSigns = tag(BlockTags.CEILING_HANGING_SIGNS);
//        var wallHangingSigns = tag(BlockTags.WALL_HANGING_SIGNS);
//        var wallSigns = tag(BlockTags.WALL_SIGNS);

        if (ModList.get().isLoaded("productivemetalworks")) {
            var FOUNDRY_CONTROLLERS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_controllers")));
            var FOUNDRY_DRAINS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_drains")));
            var FOUNDRY_TANKS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_tanks")));
            var FOUNDRY_WINDOWS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "foundry_windows")));
            var FIRE_BRICKS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("productivemetalworks", "fire_bricks")));

            ProductiveMetalworksCompat.FOUNDRY_CONTROLLERS.forEach((dyenamicDyeColor, holder) -> FOUNDRY_CONTROLLERS.addOptional(holder.getId()));
            ProductiveMetalworksCompat.FOUNDRY_DRAINS.forEach((dyenamicDyeColor, holder) -> FOUNDRY_DRAINS.addOptional(holder.getId()));
            ProductiveMetalworksCompat.FOUNDRY_TANKS.forEach((dyenamicDyeColor, holder) -> FOUNDRY_TANKS.addOptional(holder.getId()));
            ProductiveMetalworksCompat.FOUNDRY_WINDOWS.forEach((dyenamicDyeColor, holder) -> FOUNDRY_WINDOWS.addOptional(holder.getId()));
            ProductiveMetalworksCompat.FIRE_BRICKS.forEach((dyenamicDyeColor, holder) -> FIRE_BRICKS.addOptional(holder.getId()));
        }

        if (ModList.get().isLoaded("crystalix")) {
            var GLASS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("crystalix", "glass")));
            var CLEAR = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("crystalix", "clear")));
            var BORDERED = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("crystalix", "bordered")));

            CrystalixCompat.GLASS.forEach((dyenamicDyeColor, holder) -> GLASS.add(holder.get()));
            CrystalixCompat.CLEAR.forEach((dyenamicDyeColor, holder) -> CLEAR.add(holder.get()));
            CrystalixCompat.BORDERED.forEach((dyenamicDyeColor, holder) -> BORDERED.add(holder.get()));
        }
    }

    @Override
    public String getName() {
        return "Dyenamics and Friends Block Tags Provider";
    }
}
