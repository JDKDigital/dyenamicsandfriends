package cy.jdkdigital.dyenamicsandfriends.common.item.chalk;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.compat.ChalkCompat;
import io.github.mortuusars.chalk.items.ChalkItem;
import io.github.mortuusars.chalk.utils.MarkDrawingContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DyenamicsChalkItem extends ChalkItem
{
    private final DyenamicDyeColor color;

    public DyenamicsChalkItem(DyenamicDyeColor dyeColor, Properties properties) {
        super(dyeColor.getAnalogue(), properties);
        this.color = dyeColor;
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionHand hand = context.getHand();
        ItemStack itemStack = context.getItemInHand();
        Player player = context.getPlayer();
        if (player != null && itemStack.getItem() instanceof DyenamicsChalkItem) {
            if (hand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof DyenamicsChalkItem) {
                return InteractionResult.FAIL;
            } else {
                MarkDrawingContext drawingContext = this.createDrawingContext(player, context.getClickedPos(), context.getClickLocation(), context.getClickedFace(), hand);
                if (!drawingContext.canDraw()) {
                    return InteractionResult.FAIL;
                } else if (player.isSecondaryUseActive()) {
                    drawingContext.openSymbolSelectionScreen();
                    return InteractionResult.CONSUME;
                } else if (this.drawMark(drawingContext, ChalkCompat.convertMark(drawingContext.createRegularMark(this.color.getVanillaColor(), false), this.color, false))) {
                    return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
                } else {
                    return drawingContext.hasExistingMark() ? InteractionResult.PASS : InteractionResult.FAIL;
                }
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    private void damageAndConsumeItems(InteractionHand hand, ItemStack itemStack, Player player, Level level, boolean isGlowing) {
        itemStack.setDamageValue(itemStack.getDamageValue() + 1);
        if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) {
            player.setItemInHand(hand, ItemStack.EMPTY);
            Vec3 playerPos = player.position();
            level.playSound(player, playerPos.x, playerPos.y, playerPos.z, SoundEvents.GRAVEL_BREAK, SoundSource.BLOCKS, 0.9F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        if (isGlowing) {
            player.getOffhandItem().shrink(1);
        }
    }

    public DyenamicDyeColor getDyenamicsColor() {
        return color;
    }
}
