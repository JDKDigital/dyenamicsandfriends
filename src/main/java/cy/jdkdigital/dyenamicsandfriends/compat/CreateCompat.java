package cy.jdkdigital.dyenamicsandfriends.compat;

import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import cy.jdkdigital.dyenamics.common.item.DyenamicDyeItem;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.create.DyenamicsSailBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;

public class CreateCompat
{
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> SEATS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> SAILS = new HashMap<>();
//    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> MAILBOXES = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> TABLE_CLOTHS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        SEATS.put(color, DyenamicRegistry.registerBlock("create_" + color.getSerializedName() + "_seat", () -> new SeatBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD).mapColor(color.getMapColor()).lightLevel(state -> color.getLightValue()), color.getAnalogue()), true));
        SAILS.put(color, DyenamicRegistry.registerBlock("create_" + color.getSerializedName() + "_sail", () -> new DyenamicsSailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD).mapColor(color.getMapColor()).lightLevel(state -> color.getLightValue()).sound(SoundType.SCAFFOLDING).noOcclusion(), color), false));
    }

    public static void postRegister() {
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.parse("create:base"));
        if (event.getTabKey().equals(key)) {
            SEATS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
        }
    }

    public static void playerRightClick(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();

        if (event.getLevel() instanceof ServerLevel level) {
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof DyenamicDyeItem) {
                BlockState state = level.getBlockState(event.getPos());

                var blockKey = BuiltInRegistries.BLOCK.getKey(state.getBlock());
                if (blockKey.getNamespace().equals("create") && blockKey.getPath().contains("sail")) {
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
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {}
    }
}
