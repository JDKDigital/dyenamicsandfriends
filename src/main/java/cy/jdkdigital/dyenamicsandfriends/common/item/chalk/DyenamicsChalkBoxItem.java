package cy.jdkdigital.dyenamicsandfriends.common.item.chalk;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.mojang.datafixers.util.Pair;
import cy.jdkdigital.dyenamicsandfriends.compat.ChalkCompat;
import io.github.mortuusars.chalk.blocks.MarkSymbol;
import io.github.mortuusars.chalk.core.ChalkMark;
import io.github.mortuusars.chalk.items.ChalkBox;
import io.github.mortuusars.chalk.items.ChalkBoxItem;
import io.github.mortuusars.chalk.items.ChalkItem;
import io.github.mortuusars.chalk.setup.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyenamicsChalkBoxItem extends ChalkBoxItem
{
    public DyenamicsChalkBoxItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("item.dyenamicsandfriends.chalk_box.tooltip.dyenamics").withStyle(ChatFormatting.GOLD));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        ItemStack chalkBoxStack = context.getItemInHand();
        if (!chalkBoxStack.is(this)) {
            return InteractionResult.FAIL;
        } else {
            Player player = context.getPlayer();
            if (player == null) {
                return InteractionResult.PASS;
            } else if (context.getHand() != InteractionHand.OFF_HAND || !player.getMainHandItem().is(ModTags.Items.CHALK) && !player.getMainHandItem().is(this)) {
                Level level = context.getLevel();
                BlockPos clickedPos = context.getClickedPos();
                Direction clickedFace = context.getClickedFace();
                Pair<ItemStack, Integer> chalkStack = this.getFirstChalkStack(chalkBoxStack);
                if (chalkStack != null && ChalkMark.canBeDrawnAt(clickedPos.relative(clickedFace), clickedPos, clickedFace, level)) {
                    MarkSymbol symbol = context.isSecondaryUseActive() ? MarkSymbol.CROSS : MarkSymbol.NONE;

                    boolean hasDrawn = false;
                    boolean isGlowing = ChalkBox.getGlowingUses(chalkBoxStack) > 0;
                    if (chalkStack.getFirst().getItem() instanceof DyenamicsChalkItem dyenamicsChalkItem) {
                        boolean isGlowingDye = dyenamicsChalkItem.getDyenamicsColor().equals(DyenamicDyeColor.FLUORESCENT);
                        hasDrawn = ChalkCompat.draw(symbol, dyenamicsChalkItem.getDyenamicsColor(), isGlowing || isGlowingDye, context.getClickedPos(), context.getClickedFace(), context.getClickLocation(), level) == InteractionResult.SUCCESS;
                        // Don't use up glow if the dye is already glowing
                        if (isGlowingDye) {
                            isGlowing = false;
                        }
                    } else {
                        DyeColor chalkColor = ((ChalkItem) chalkStack.getFirst().getItem()).getColor();
                        hasDrawn = ChalkMark.draw(symbol, chalkColor, isGlowing, clickedPos, clickedFace, context.getClickLocation(), level) == InteractionResult.SUCCESS;
                    }

                    if (hasDrawn) {
                        if (!player.isCreative()) {
                            ItemStack chalkItemStack = chalkStack.getFirst();
                            chalkItemStack.setDamageValue(chalkItemStack.getDamageValue() + 1);
                            if (chalkItemStack.getDamageValue() >= chalkItemStack.getMaxDamage()) {
                                chalkItemStack = ItemStack.EMPTY;
                                Vec3 playerPos = player.position();
                                level.playSound(player, playerPos.x, playerPos.y, playerPos.z, SoundEvents.GRAVEL_BREAK, SoundSource.BLOCKS, 0.9F, 0.9F + level.random.nextFloat() * 0.2F);
                            }

                            ChalkBox.setSlot(chalkBoxStack, chalkStack.getSecond(), chalkItemStack);
                            if (isGlowing) {
                                ChalkBox.useGlow(chalkBoxStack);
                            }
                        }

                        return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
                    }
                }
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public float getSelectedChalkColor(ItemStack stack) {
        if (stack.hasTag()) {
            for(int i = 0; i < 8; ++i) {
                ItemStack chalkStack = ChalkBox.getItemInSlot(stack, i);
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

    private Pair<ItemStack, Integer> getFirstChalkStack(ItemStack chalkBoxStack) {
        for(int slot = 0; slot < 8; ++slot) {
            ItemStack itemInSlot = ChalkBox.getItemInSlot(chalkBoxStack, slot);
            if (itemInSlot.is(ModTags.Items.CHALK)) {
                return Pair.of(itemInSlot, slot);
            }
        }

        return null;
    }
}
