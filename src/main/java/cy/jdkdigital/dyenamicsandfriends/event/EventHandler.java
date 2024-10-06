package cy.jdkdigital.dyenamicsandfriends.event;

import cy.jdkdigital.dyenamics.common.items.DyenamicDyeItem;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DyenamicsAndFriends.MODID)
public class EventHandler
{
    @SubscribeEvent
    public static void blockInteract(PlayerInteractEvent event) {
        if (!event.getLevel().isClientSide()) {
            DyenamicRegistry.onBlockInteract(event);
        }
    }

    @SubscribeEvent
    public static void entityPlace(BlockEvent.EntityPlaceEvent event) {
        if (!event.getLevel().isClientSide()) {
            DyenamicRegistry.onEntityPlace(event);
        }
    }

    @SubscribeEvent
    public static void playerRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getLevel().isClientSide()) {
            DyenamicRegistry.onPlayerRightClick(event);
        }
    }
}
