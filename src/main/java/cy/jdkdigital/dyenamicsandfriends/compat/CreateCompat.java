package cy.jdkdigital.dyenamicsandfriends.compat;

import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour;
import cy.jdkdigital.dyenamics.common.items.DyenamicDyeItem;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.create.DyenamicsSailBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.create.DyenamicsSeatBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class CreateCompat
{
    public static Map<DyenamicDyeColor, RegistryObject<? extends Block>> SEATS = new HashMap<>();
    public static Map<DyenamicDyeColor, RegistryObject<? extends Block>> SAILS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        SEATS.put(color, DyenamicRegistry.registerBlock("create_" + color.getSerializedName() + "_seat", () -> new DyenamicsSeatBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_WOOD).mapColor(color.getMapColor()).lightLevel(state -> color.getLightValue()), color), true));
        SAILS.put(color, DyenamicRegistry.registerBlock("create_" + color.getSerializedName() + "_sail", () -> new DyenamicsSailBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_WOOD).mapColor(color.getMapColor()).lightLevel(state -> color.getLightValue()).sound(SoundType.SCAFFOLDING).noOcclusion(), color), false));
    }

    public static void setup(FMLCommonSetupEvent event) {
        SEATS.forEach((color, seat) -> {
            AllInteractionBehaviours.registerBehaviour(seat.get(), new SeatInteractionBehaviour());
            AllMovementBehaviours.registerBehaviour(seat.get(), new SeatMovementBehaviour());
        });
    }

    public static void postRegister() {
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("create:base"));
        if (event.getTabKey().equals(key)) {
            SEATS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
        }
    }

    public static void playerRightClick(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();

        if (event.getLevel() instanceof ServerLevel level) {
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof DyenamicDyeItem) {
                BlockState state = level.getBlockState(event.getPos());

                var blockKey = ForgeRegistries.BLOCKS.getKey(state.getBlock());
                if (blockKey != null && blockKey.getNamespace().equals("create") && blockKey.getPath().contains("sail")) {
                    DyenamicsSailBlock.applyDye(state, level, event.getPos(), event.getHitVec().getLocation(), DyenamicDyeColor.getColor(itemStack));
                    if (!event.getEntity().isCreative()) {
                        itemStack.shrink(1);
                    }
                    event.getEntity().swing(event.getHand());
                }
            }
        }
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        }
    }
}
