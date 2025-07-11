package cy.jdkdigital.dyenamicsandfriends.event;

import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.compat.*;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = DyenamicsAndFriends.MODID)
public class ModEventHandler
{
    @SubscribeEvent
    public static void init(final FMLCommonSetupEvent event) {
        DyenamicRegistry.onCommonSetup(event);
    }

    @SubscribeEvent
    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (ModList.get().isLoaded("ae2")) {
//            Ae2Compat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("create")) {
            CreateCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("another_furniture")) {
            AnotherFurnitureCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("botanypots")) {
            BotanyPotsCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("comforts")) {
            ComfortsCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("elevatorid")) {
            ElevatoridCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("productivebees")) {
            ProductiveBeesCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("productivemetalworks")) {
            ProductiveMetalworksCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("farmersdelight")) {
        }
        if (ModList.get().isLoaded("supplementaries")) {
        }
        if (ModList.get().isLoaded("suppsquared")) {
        }
        if (ModList.get().isLoaded("regions_unexplored")) {
            RegionsUnexploredCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("quark")) {
        }
        if (ModList.get().isLoaded("handcrafted")) {
//            HandcraftedCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("furnish")) {
            FurnishCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("chalk")) {
//            ChalkCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("oreganized")) {
        }
        if (ModList.get().isLoaded("ceramics")) {
        }
        if (ModList.get().isLoaded("glazedresymmetry")) {
        }
        if (ModList.get().isLoaded("clayworks")) {
        }
        if (ModList.get().isLoaded("the_bumblezone")) {
            BumblezoneCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("sleep_tight")) {
//            SleepTightCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("connectedglass")) {
            ConnectedGlassCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("crystalix")) {
            CrystalixCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("luminax")) {
            LuminaxCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("cookingforblockheads")) {
            CookingForBlockheadsCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("clayworks")) {
            ClayworksCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("chromacarvings")) {
            ChromaCarvingsCompat.buildTabContents(event);
        }
        if (ModList.get().isLoaded("just_blahaj")) {
            JustBlahajCompat.buildTabContents(event);
        }
    }

    @SubscribeEvent
    public static void addBlocks(final BlockEntityTypeAddBlocksEvent event) {
        if (ModList.get().isLoaded("productivemetalworks")) {
            ProductiveMetalworksCompat.addBlocks(event);
        }
        if (ModList.get().isLoaded("cookingforblockheads")) {
            CookingForBlockheadsCompat.addBlocks(event);
        }
        if (ModList.get().isLoaded("clayworks")) {
            ClayworksCompat.addBlocks(event);
        }
        if (ModList.get().isLoaded("botanypots")) {
            BotanyPotsCompat.addBlocks(event);
        }
    }

    @SubscribeEvent
    public static void onEntityAttributeCreate(EntityAttributeCreationEvent event) {
        if (ModList.get().isLoaded("chromacarvings")) {
            ChromaCarvingsCompat.createEntityAttributes(event);
        }
    }
}
