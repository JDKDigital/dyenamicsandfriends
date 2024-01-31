//package cy.jdkdigital.dyenamicsandfriends.common.item.chalk;
//
//import com.mojang.datafixers.util.Pair;
//import io.github.mortuusars.chalk.items.ChalkBox;
//import io.github.mortuusars.chalk.items.ChalkBoxItem;
//import io.github.mortuusars.chalk.items.ChalkItem;
//import io.github.mortuusars.chalk.render.ChalkColors;
//import io.github.mortuusars.chalk.utils.MarkDrawingContext;
//import net.minecraft.ChatFormatting;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.item.context.UseOnContext;
//import net.minecraft.world.level.Level;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//public class DyenamicsChalkBoxItem extends ChalkBoxItem
//{
//    public DyenamicsChalkBoxItem(Properties properties) {
//        super(properties);
//    }
//
//    @Override
//    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
//        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
//        pTooltipComponents.add(Component.translatable("item.dyenamicsandfriends.chalk_box.tooltip.dyenamics").withStyle(ChatFormatting.GOLD));
//    }
//
////    @Override
////    public @NotNull InteractionResult useOn(UseOnContext context) {
////        ItemStack chalkBox = context.getItemInHand();
////        if (!chalkBox.is(this))
////            return InteractionResult.FAIL;
////
////        Player player = context.getPlayer();
////        if (player == null)
////            return InteractionResult.FAIL;
////
////        if (context.getHand() == InteractionHand.OFF_HAND && (player.getMainHandItem().getItem() instanceof ChalkItem || player.getMainHandItem().is(this)) )
////            return InteractionResult.FAIL; // Skip drawing from offhand if chalks in both hands.
////
////        int selectedChalkIndex = getSelectedChalkIndex(chalkBox);
////        if (selectedChalkIndex == -1) {
////            openGUI(player, chalkBox);
////            return InteractionResult.SUCCESS;
////        }
////
////        MarkDrawingContext drawingContext = createDrawingContext(context);
////
////        if (!drawingContext.canDraw()) {
////            return InteractionResult.FAIL;
////        }
////
////        ItemStack selectedChalk = ChalkBox.getItemInSlot(chalkBox, selectedChalkIndex);
////
////        if (player.isSecondaryUseActive()) {
////            drawingContext.openSymbolSelectionScreen();
////            return InteractionResult.CONSUME;
////        }
////
////        if (drawMark(drawingContext, drawingContext.createRegularMark(ChalkColors.fromDyeColor(((ChalkItem) selectedChalk.getItem()).getColor()), ChalkBox.getGlowLevel(chalkBox) > 0))) {
////            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
////        }
////
////        return InteractionResult.FAIL;
////    }
////
////    private int getSelectedChalkIndex(ItemStack chalkBoxStack) {
////        for(int slot = 0; slot < 8; ++slot) {
////            ItemStack itemInSlot = ChalkBox.getItemInSlot(chalkBoxStack, slot);
////            if (itemInSlot.getItem() instanceof ChalkItem) {
////                return slot;
////            }
////        }
////        return -1;
////    }
//
//    @Override
//    public float getSelectedChalkColor(ItemStack stack) {
//        if (stack.hasTag()) {
//            for(int i = 0; i < 8; ++i) {
//                ItemStack chalkStack = ChalkBox.getItemInSlot(stack, i);
//                if (!chalkStack.isEmpty()) {
//                    if (chalkStack.getItem() instanceof DyenamicsChalkItem dyenamicsChalkItem) {
//                        return (float)dyenamicsChalkItem.getDyenamicsColor().getId() + 1;
//                    }
//                    return (float)(((ChalkItem)chalkStack.getItem()).getColor().getId() + 1);
//                }
//            }
//        }
//
//        return 0.0F;
//    }
//
////    private Pair<ItemStack, Integer> getFirstChalkStack(ItemStack chalkBoxStack) {
////        for(int slot = 0; slot < 8; ++slot) {
////            ItemStack itemInSlot = ChalkBox.getItemInSlot(chalkBoxStack, slot);
////            if (itemInSlot.is(ModTags.Items.CHALK)) {
////                return Pair.of(itemInSlot, slot);
////            }
////        }
////
////        return null;
////    }
//}
