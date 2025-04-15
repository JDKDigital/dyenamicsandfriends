package cy.jdkdigital.dyenamicsandfriends.compat;

import com.vsngarcia.client.ColorCamoElevator;
import com.vsngarcia.level.ElevatorBlockEntityBase;
import com.vsngarcia.neoforge.client.render.ElevatorBakedModel;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.elevatorid.DyenamicsElevatorBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.elevatorid.DyenamicsElevatorBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ElevatoridCompat
{
    private static final Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> ELEVATORS = new HashMap<>();
    public static Supplier<BlockEntityType<ElevatorBlockEntityBase>> ELEVATOR_BLOCK_ENTITY;

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "elevatorid_" + color.getSerializedName();
        ELEVATORS.put(color, DyenamicRegistry.registerBlock(prefix + "_elevator", () -> new DyenamicsElevatorBlock(color), true));
        ELEVATOR_BLOCK_ENTITY = DyenamicRegistry.registerBlockEntity(prefix + "_elevator", () -> DyenamicRegistry.createBlockEntityType(DyenamicsElevatorBlockEntity::new, ELEVATORS.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0])));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.parse("elevatorid:elevators_tab"));
        if (event.getTabKey().equals(key)) {
            ELEVATORS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
        }
    }

    public static class Client
    {
        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            event.register(
                    new ColorCamoElevator(),
                    ELEVATORS.values().stream().map(DeferredHolder::get).toArray(Block[]::new)
            );
        }

        public static void bakeModel(ModelEvent.ModifyBakingResult e) {
            e.getModels().entrySet().stream()
                    .filter(entry -> "dyenamicsandfriends".equals(entry.getKey().id().getNamespace()) && entry.getKey().id().getPath().contains("_elevator"))
                    .forEach(entry -> e.getModels().put(entry.getKey(), new ElevatorBakedModel(entry.getValue())));
        }
    }
}
