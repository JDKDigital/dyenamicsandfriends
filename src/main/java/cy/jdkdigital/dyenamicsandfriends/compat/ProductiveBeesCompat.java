package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.common.items.DyenamicDyeItem;
import cy.jdkdigital.productivebees.common.block.entity.CanvasBeehiveBlockEntity;
import cy.jdkdigital.productivebees.common.block.entity.CanvasExpansionBoxBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ProductiveBeesCompat
{
    public static void blockInteract(PlayerInteractEvent event) {
        if (!event.getLevel().isClientSide()) {
            ItemStack stack = event.getEntity().getItemInHand(event.getHand());
            if (stack.getItem() instanceof DyenamicDyeItem dyeItem) {
                if (event.getLevel().getBlockEntity(event.getPos()) instanceof CanvasBeehiveBlockEntity canvasBlockEntity) {
                    canvasBlockEntity.setColor(dyeItem.getDyeColor().getColorValue());
                }
                if (event.getLevel().getBlockEntity(event.getPos()) instanceof CanvasExpansionBoxBlockEntity canvasBlockEntity) {
                    canvasBlockEntity.setColor(dyeItem.getDyeColor().getColorValue());
                }
            }
        }
    }
}
