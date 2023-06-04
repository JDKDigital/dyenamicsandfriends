package cy.jdkdigital.dyenamicsandfriends.common.item.chalk;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.mojang.datafixers.util.Pair;
import cy.jdkdigital.dyenamicsandfriends.compat.ChalkCompat;
import io.github.mortuusars.chalk.core.Mark;
import io.github.mortuusars.chalk.data.Lang;
import io.github.mortuusars.chalk.items.ChalkBox;
import io.github.mortuusars.chalk.items.ChalkBoxItem;
import io.github.mortuusars.chalk.items.ChalkItem;
import io.github.mortuusars.chalk.render.ChalkColors;
import io.github.mortuusars.chalk.utils.MarkDrawingContext;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
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
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level pLevel, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        int selectedChalkIndex = this.getSelectedChalkIndex(stack);
        if (selectedChalkIndex != -1) {
            ItemStack selectedChalk = ChalkBox.getItemInSlot(stack, selectedChalkIndex);
            Item var9 = selectedChalk.getItem();
            Style var10000;
            if (var9 instanceof ChalkItem) {
                ChalkItem chalkItem = (ChalkItem)var9;
                var10000 = Style.EMPTY.withColor(ChalkColors.fromDyeColor(chalkItem.getColor()));
            } else {
                var10000 = Style.EMPTY.withColor(ChatFormatting.WHITE);
            }

            Style style = var10000;
            tooltipComponents.add(Lang.CHALK_BOX_DRAWING_WITH_TOOLTIP.translate().withStyle(ChatFormatting.GRAY).append(((MutableComponent)selectedChalk.getHoverName()).withStyle(style)));
        }

        if (Minecraft.getInstance().player != null) {
            Screen var11 = Minecraft.getInstance().screen;
            if (var11 instanceof AbstractContainerScreen) {
                AbstractContainerScreen<?> screen = (AbstractContainerScreen)var11;
                if (screen instanceof CreativeModeInventoryScreen) {
                    CreativeModeInventoryScreen creativeScreen = (CreativeModeInventoryScreen)screen;
                    if (creativeScreen.getSelectedTab() != CreativeModeTab.TAB_INVENTORY.getId()) {
                        return;
                    }
                }

                Slot slotUnderMouse = screen.getSlotUnderMouse();
                if (slotUnderMouse != null && slotUnderMouse.container instanceof Inventory) {
                    tooltipComponents.add(Lang.CHALK_BOX_OPEN_TOOLTIP.translate().withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.DARK_GRAY));
                }
            }
        }

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
                        ItemStack selectedChalk = ChalkBox.getItemInSlot(chalkBox, selectedChalkIndex);
                        if (player.isSecondaryUseActive()) {
                            drawingContext.openSymbolSelectionScreen();
                            return InteractionResult.CONSUME;
                        } else {
                            boolean isGlowing = ChalkBox.getGlowLevel(chalkBox) > 0;
                            DyeColor chalkColor = ((ChalkItem) selectedChalk.getItem()).getColor();
                            Mark mark = drawingContext.createRegularMark(chalkColor, isGlowing);
                            if (selectedChalk.getItem() instanceof DyenamicsChalkItem dyenamicsChalkItem) {
                                boolean isGlowingDye = dyenamicsChalkItem.getDyenamicsColor().equals(DyenamicDyeColor.FLUORESCENT);
                                // Don't use up glow if the dye is already glowing
                                if (isGlowingDye) {
                                    isGlowing = false;
                                }
                                mark = ChalkCompat.convertMark(mark, dyenamicsChalkItem.getDyenamicsColor(), isGlowing);
                            }
                            return this.drawMark(drawingContext, mark) ? InteractionResult.sidedSuccess(context.getLevel().isClientSide) : InteractionResult.FAIL;
                        }
                    }
                }
            }
        }
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
