package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.elevatorid.DyenamicsElevatorBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.elevatorid.DyenamicsElevatorBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.registries.RegistryObject;
import xyz.vsngamer.elevatorid.blocks.ElevatorBlock;
import xyz.vsngamer.elevatorid.client.render.ColorCamoElevator;
import xyz.vsngamer.elevatorid.client.render.ElevatorBakedModel;

import java.util.HashMap;
import java.util.Map;

public class ElevatoridCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> ELEVATORS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "elevatorid_" + color.getSerializedName();
        ELEVATORS.put(color, DyenamicRegistry.registerBlock(prefix + "_elevator", () -> new DyenamicsElevatorBlock(color, DyenamicRegistry.registerBlockEntity(prefix + "_elevator", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsElevatorBlockEntity((DyenamicsElevatorBlock) ELEVATORS.get(color).get(), pos, state), ELEVATORS.get(color).get()))), CreativeModeTab.TAB_MISC, true));
    }

    public static class Client
    {
        public static void registerBlockRendering() {
            Minecraft.getInstance().getBlockColors().register(
                    new ColorCamoElevator(),
                    ELEVATORS.values().stream().map(RegistryObject::get).toArray(ElevatorBlock[]::new)
            );
            ELEVATORS.values().forEach(o -> ItemBlockRenderTypes.setRenderLayer(o.get(), t -> true));
        }

        public static void bakeModel(ModelBakeEvent event) {
            event.getModelRegistry().entrySet().stream()
                    .filter(entry -> "dyenamicsandfriends".equals(entry.getKey().getNamespace()) && entry.getKey().getPath().contains("_elevator"))
                    .forEach(entry -> event.getModelRegistry().put(entry.getKey(), new ElevatorBakedModel(entry.getValue())));
        }
    }
}
