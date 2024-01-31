package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.elevatorid.DyenamicsElevatorBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.elevatorid.DyenamicsElevatorBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;
import xyz.vsngamer.elevatorid.client.render.ColorCamoElevator;
import xyz.vsngamer.elevatorid.client.render.ElevatorBakedModel;

import java.util.HashMap;
import java.util.Map;

public class ElevatoridCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> ELEVATORS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "elevatorid_" + color.getSerializedName();
        ELEVATORS.put(color, DyenamicRegistry.registerBlock(prefix + "_elevator", () -> new DyenamicsElevatorBlock(color, DyenamicRegistry.registerBlockEntity(prefix + "_elevator", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsElevatorBlockEntity((DyenamicsElevatorBlock) ELEVATORS.get(color).get(), pos, state), ELEVATORS.get(color).get()))), true));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("elevatorid:elevators_tab"));
        if (event.getTabKey().equals(key)) {
            ELEVATORS.forEach((dyenamicDyeColor, registryObject) -> {event.accept(registryObject);});
        }
    }

    public static class Client
    {
        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            event.register(
                    new ColorCamoElevator(),
                    ELEVATORS.values().stream().map(RegistryObject::get).toArray(Block[]::new)
            );
        }

        public static void bakeModel(ModelEvent.ModifyBakingResult e) {
            e.getModels().entrySet().stream()
                    .filter(entry -> "dyenamicsandfriends".equals(entry.getKey().getNamespace()) && entry.getKey().getPath().contains("_elevator"))
                    .forEach(entry -> e.getModels().put(entry.getKey(), new ElevatorBakedModel(entry.getValue())));
        }
    }
}
