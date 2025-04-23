package cy.jdkdigital.dyenamicsandfriends.datagen;

import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.compat.CookingForBlockheadsCompat;
import cy.jdkdigital.dyenamicsandfriends.compat.CrystalixCompat;
import cy.jdkdigital.dyenamicsandfriends.compat.LuminaxCompat;
import cy.jdkdigital.dyenamicsandfriends.compat.ProductiveMetalworksCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.neoforged.fml.ModList;
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
        var MINEABLE_PICKAXE = tag(BlockTags.MINEABLE_WITH_PICKAXE);

        MINEABLE_PICKAXE.addTag(BlockTags.create(ResourceLocation.parse("dyenamicsandfriends:mineable/quark_pickaxe")));
        MINEABLE_PICKAXE.addTag(BlockTags.create(ResourceLocation.parse("dyenamicsandfriends:mineable/ceramics_pickaxe")));
        MINEABLE_PICKAXE.addTag(BlockTags.create(ResourceLocation.parse("dyenamicsandfriends:mineable/clayworks_pickaxe")));
        MINEABLE_PICKAXE.addTag(BlockTags.create(ResourceLocation.parse("dyenamicsandfriends:mineable/glazedresymmetry_pickaxe")));
        MINEABLE_PICKAXE.addOptionalTag(BlockTags.create(ResourceLocation.parse("botanypots:all_botany_pots")));

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

            CrystalixCompat.GLASS.forEach((dyenamicDyeColor, holder) -> GLASS.addOptional(holder.getId()));
            CrystalixCompat.CLEAR.forEach((dyenamicDyeColor, holder) -> CLEAR.addOptional(holder.getId()));
            CrystalixCompat.BORDERED.forEach((dyenamicDyeColor, holder) -> BORDERED.addOptional(holder.getId()));
        }

        if (ModList.get().isLoaded("luminax")) {
            var BLOCKS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "blocks")));
            var STAIRS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "stairs")));
            var SLABS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "slabs")));
            var WALLS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "walls")));
            var PRESSURE_PLATES = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "pressure_plates")));
            var BUTTONS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "buttons")));

            LuminaxCompat.BLOCKS.forEach((dyenamicDyeColor, holder) -> BLOCKS.addOptional(holder.getId()));
            LuminaxCompat.STAIRS.forEach((dyenamicDyeColor, holder) -> STAIRS.addOptional(holder.getId()));
            LuminaxCompat.SLABS.forEach((dyenamicDyeColor, holder) -> SLABS.addOptional(holder.getId()));
            LuminaxCompat.WALLS.forEach((dyenamicDyeColor, holder) -> WALLS.addOptional(holder.getId()));
            LuminaxCompat.PRESSURE_PLATES.forEach((dyenamicDyeColor, holder) -> PRESSURE_PLATES.addOptional(holder.getId()));
            LuminaxCompat.BUTTONS.forEach((dyenamicDyeColor, holder) -> BUTTONS.addOptional(holder.getId()));

            var DIM_BLOCKS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_blocks")));
            var DIM_STAIRS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_stairs")));
            var DIM_SLABS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_slabs")));
            var DIM_WALLS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_walls")));
            var DIM_PRESSURE_PLATES = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_pressure_plates")));
            var DIM_BUTTONS = tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("luminax", "dim_buttons")));

            LuminaxCompat.DIM_BLOCKS.forEach((dyenamicDyeColor, holder) -> DIM_BLOCKS.addOptional(holder.getId()));
            LuminaxCompat.DIM_STAIRS.forEach((dyenamicDyeColor, holder) -> DIM_STAIRS.addOptional(holder.getId()));
            LuminaxCompat.DIM_SLABS.forEach((dyenamicDyeColor, holder) -> DIM_SLABS.addOptional(holder.getId()));
            LuminaxCompat.DIM_WALLS.forEach((dyenamicDyeColor, holder) -> DIM_WALLS.addOptional(holder.getId()));
            LuminaxCompat.DIM_PRESSURE_PLATES.forEach((dyenamicDyeColor, holder) -> DIM_PRESSURE_PLATES.addOptional(holder.getId()));
            LuminaxCompat.DIM_BUTTONS.forEach((dyenamicDyeColor, holder) -> DIM_BUTTONS.addOptional(holder.getId()));
        }

        if (ModList.get().isLoaded("cookingforblockheads")) {
            CookingForBlockheadsCompat.OVENS.forEach((dyenamicDyeColor, holder) -> MINEABLE_PICKAXE.add(holder.get()));
            CookingForBlockheadsCompat.FRIDGES.forEach((dyenamicDyeColor, holder) -> MINEABLE_PICKAXE.add(holder.get()));
            CookingForBlockheadsCompat.CONNECTORS.forEach((dyenamicDyeColor, holder) -> MINEABLE_PICKAXE.add(holder.get()));
            CookingForBlockheadsCompat.KITCHEN_FLOORS.forEach((dyenamicDyeColor, holder) -> MINEABLE_PICKAXE.add(holder.get()));
            CookingForBlockheadsCompat.COOKING_TABLES.forEach((dyenamicDyeColor, holder) -> MINEABLE_PICKAXE.add(holder.get()));
            CookingForBlockheadsCompat.COUNTERS.forEach((dyenamicDyeColor, holder) -> MINEABLE_PICKAXE.add(holder.get()));
            CookingForBlockheadsCompat.CABINETS.forEach((dyenamicDyeColor, holder) -> MINEABLE_PICKAXE.add(holder.get()));
            CookingForBlockheadsCompat.SINKS.forEach((dyenamicDyeColor, holder) -> MINEABLE_PICKAXE.add(holder.get()));

            var COOKING_TABLES = tag(BlockTags.create(ResourceLocation.parse("cookingforblockheads:cooking_tables")));
            CookingForBlockheadsCompat.COOKING_TABLES.forEach((dyenamicDyeColor, holder) -> COOKING_TABLES.add(holder.get()));
            var KITCHEN_CONNECTORS = tag(BlockTags.create(ResourceLocation.parse("cookingforblockheads:kitchen_connectors")));
            CookingForBlockheadsCompat.CONNECTORS.forEach((dyenamicDyeColor, holder) -> KITCHEN_CONNECTORS.add(holder.get()));
            CookingForBlockheadsCompat.KITCHEN_FLOORS.forEach((dyenamicDyeColor, holder) -> KITCHEN_CONNECTORS.add(holder.get()));
            var KITCHEN_ITEM_PROVIDERS = tag(BlockTags.create(ResourceLocation.parse("cookingforblockheads:kitchen_item_providers")));
            CookingForBlockheadsCompat.CABINETS.forEach((dyenamicDyeColor, holder) -> KITCHEN_ITEM_PROVIDERS.add(holder.get()));
            CookingForBlockheadsCompat.COUNTERS.forEach((dyenamicDyeColor, holder) -> KITCHEN_ITEM_PROVIDERS.add(holder.get()));
        }
    }

    @Override
    public String getName() {
        return "Dyenamics and Friends Block Tags Provider";
    }
}
