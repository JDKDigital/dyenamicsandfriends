package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour;
import cy.jdkdigital.dyenamicsandfriends.common.block.create.DyenamicsSailBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.create.DyenamicsSeatBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class CreateCompat
{
    public static Map<DyenamicDyeColor, RegistryObject<? extends Block>> VALVE_HANDLES = new HashMap<>();
    public static Map<DyenamicDyeColor, RegistryObject<? extends Block>> SEATS = new HashMap<>();
    public static Map<DyenamicDyeColor, RegistryObject<? extends Block>> SAILS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        // valve handle TODO tags
//        VALVE_HANDLES.put(color, DyenamicRegistry.registerBlock("create_" + color.getSerializedName() + "_valve_handle", () -> new DyenamicsValveHandleBlock(BlockBehaviour.Properties.of(Material.METAL, color.getMapColor()).lightLevel(state -> color.getLightValue()).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER), color), CreativeModeTab.TAB_MISC, true));
        // toolbox

        SEATS.put(color, DyenamicRegistry.registerBlock("create_" + color.getSerializedName() + "_seat", () -> new DyenamicsSeatBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getMapColor()).lightLevel(state -> color.getLightValue()).strength(2.0F).sound(SoundType.WOOD), color), CreativeModeTab.TAB_MISC, true));
        SAILS.put(color, DyenamicRegistry.registerBlock("create_" + color.getSerializedName() + "_sail", () -> new DyenamicsSailBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getMapColor()).lightLevel(state -> color.getLightValue()).strength(2.0F).sound(SoundType.WOOD), color), null, false));

        //        AtomicReference<BlockEntityType<?>> typeSupplier = new AtomicReference<>();
//        Block[] valves = (Block[]) VALVE_HANDLES.values().stream().flatMap(RegistryObject::stream).filter(Objects::nonNull).toList().toArray();
//        Supplier<BlockEntityType<BlockEntity>> s = () -> createBlockEntityType((pos, state) -> new DyenamicsValveHandleBlockEntity(typeSupplier, pos, state), valves);
//        typeSupplier.set(registerBlockEntity("dyenamics_valve_handles", s));
    }

    public static void setup(FMLCommonSetupEvent event) {
        SEATS.forEach((color, seat) -> {
            AllInteractionBehaviours.registerBehaviour(seat.get(), new SeatInteractionBehaviour());
            AllMovementBehaviours.registerBehaviour(seat.get(), new SeatMovementBehaviour());
        });
    }
}
