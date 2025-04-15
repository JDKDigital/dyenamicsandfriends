package cy.jdkdigital.dyenamicsandfriends.event;

import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = DyenamicsAndFriends.MODID)
public class EventHandler
{
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
