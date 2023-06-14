package cy.jdkdigital.dyenamicsandfriends.common.item.chalk;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.google.common.base.Preconditions;
import cy.jdkdigital.dyenamicsandfriends.compat.ChalkCompat;
import io.github.mortuusars.chalk.core.IDrawingTool;
import io.github.mortuusars.chalk.core.Mark;
import io.github.mortuusars.chalk.core.MarkSymbol;
import io.github.mortuusars.chalk.core.SymbolOrientation;
import io.github.mortuusars.chalk.items.ChalkBox;
import io.github.mortuusars.chalk.items.ChalkBoxItem;
import io.github.mortuusars.chalk.items.ChalkItem;
import io.github.mortuusars.chalk.render.ChalkColors;
import io.github.mortuusars.chalk.utils.MarkDrawingContext;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyenamicsChalkBoxItem extends ChalkBoxItem
{
    public DyenamicsChalkBoxItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMarkColorValue(ItemStack chalkBoxStack) {
        ItemStack selectedChalk = ChalkBox.getItemInSlot(chalkBoxStack, getSelectedChalkIndex(chalkBoxStack));
        return selectedChalk.getItem() instanceof IDrawingTool drawingTool ? drawingTool.getMarkColorValue(selectedChalk) : -1;
    }

    @Override
    public Mark getMark(ItemStack itemInHand, MarkDrawingContext drawingContext, MarkSymbol symbol) {
        Preconditions.checkArgument(itemInHand.getItem() instanceof ChalkBoxItem, "ChalkBox expected in player's hand.");

        int selectedChalkIndex = getSelectedChalkIndex(itemInHand);

        if (selectedChalkIndex == -1)
            return null;

        ItemStack selectedChalk = ChalkBox.getItemInSlot(itemInHand, selectedChalkIndex);
        int color = ChalkColors.fromDyeColor(DyeColor.WHITE);
        if (selectedChalk.getItem() instanceof ChalkItem chalkItem) {
            color = ChalkColors.fromDyeColor(chalkItem.getColor());
        }
        boolean isGlowing = getGlowing(itemInHand);
        Mark mark = drawingContext.createMark(color, symbol, isGlowing);
        if (selectedChalk.getItem() instanceof DyenamicsChalkItem dyenamicsChalkItem) {
            boolean isGlowingDye = dyenamicsChalkItem.getDyenamicsColor().equals(DyenamicDyeColor.FLUORESCENT);
            // Don't use up glow if the dye is already glowing
            if (isGlowingDye) {
                isGlowing = false;
            }
            mark = dyenamicsChalkItem.getMark(itemInHand, drawingContext, symbol);
        }
        return selectedChalk.getItem() instanceof DyenamicsChalkItem chalkItem ? ChalkCompat.convertMark(mark, chalkItem.getDyenamicsColor(), isGlowing) : mark;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level pLevel, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, pLevel, tooltipComponents, isAdvanced);

        tooltipComponents.add(Component.translatable("item.dyenamicsandfriends.chalk_box.tooltip.dyenamics").withStyle(ChatFormatting.GOLD));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        ItemStack chalkBox = context.getItemInHand();
        if (!chalkBox.is(this)) {
            return InteractionResult.FAIL;
        } else {
            Player player = context.getPlayer();
            if (player == null) {
                return InteractionResult.FAIL;
            } else if (context.getHand() == InteractionHand.OFF_HAND && (player.getMainHandItem().getItem() instanceof ChalkItem || player.getMainHandItem().is(this))) {
                return InteractionResult.FAIL;
            } else {
                int selectedChalkIndex = this.getSelectedChalkIndex(chalkBox);
                if (selectedChalkIndex == -1) {
                    openGUI(player, chalkBox);
                    return InteractionResult.SUCCESS;
                } else {
                    MarkDrawingContext drawingContext = this.createDrawingContext(context);
                    if (!drawingContext.canDraw()) {
                        return InteractionResult.FAIL;
                    } else {
                        if (player.isSecondaryUseActive()) {
                            drawingContext.openSymbolSelectionScreen();
                            return InteractionResult.CONSUME;
                        } else {
                            Mark mark = getMark(chalkBox, drawingContext, drawingContext.getInitialOrientation() == SymbolOrientation.CENTER ? MarkSymbol.CENTER : MarkSymbol.ARROW);
                            return this.drawMark(drawingContext, mark) ? InteractionResult.sidedSuccess(context.getLevel().isClientSide) : InteractionResult.FAIL;
                        }
                    }
                }
            }
        }
    }

    public ItemStack getSelectedChalkItem(ItemStack chalkBox) {
        return ChalkBox.getItemInSlot(chalkBox, getSelectedChalkIndex(chalkBox));
    }

    private int getSelectedChalkIndex(ItemStack chalkBoxStack) {
        for(int slot = 0; slot < 8; ++slot) {
            ItemStack itemInSlot = ItemStack.of(getItemsListTag(chalkBoxStack).getCompound(slot));
            if (itemInSlot.getItem() instanceof ChalkItem) {
                return slot;
            }
        }

        return -1;
    }

    private static ListTag getItemsListTag(ItemStack chalkBoxStack) {
        CompoundTag compoundTag = chalkBoxStack.getOrCreateTag();
        if (!compoundTag.contains("Items")) {
            ListTag itemTags = new ListTag();

            for(int index = 0; index < 9; ++index) {
                itemTags.add(ItemStack.EMPTY.serializeNBT());
            }

            compoundTag.put("Items", itemTags);
        }

        return compoundTag.getList("Items", 10);
    }

    @Override
    public float getSelectedChalkColor(ItemStack stack) {
        if (stack.hasTag()) {
            for(int i = 0; i < 8; ++i) {
                ItemStack chalkStack = ItemStack.of(getItemsListTag(stack).getCompound(i));
                if (!chalkStack.isEmpty()) {
                    if (chalkStack.getItem() instanceof DyenamicsChalkItem dyenamicsChalkItem) {
                        return (float)dyenamicsChalkItem.getDyenamicsColor().getId() + 1;
                    }
                    return (float)(((ChalkItem)chalkStack.getItem()).getColor().getId() + 1);
                }
            }
        }

        return 0.0F;
    }
}
