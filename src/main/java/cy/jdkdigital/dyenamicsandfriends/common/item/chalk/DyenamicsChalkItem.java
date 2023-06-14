package cy.jdkdigital.dyenamicsandfriends.common.item.chalk;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.compat.ChalkCompat;
import io.github.mortuusars.chalk.core.Mark;
import io.github.mortuusars.chalk.core.MarkSymbol;
import io.github.mortuusars.chalk.core.SymbolOrientation;
import io.github.mortuusars.chalk.items.ChalkItem;
import io.github.mortuusars.chalk.utils.MarkDrawingContext;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class DyenamicsChalkItem extends ChalkItem
{
    private final DyenamicDyeColor color;

    public DyenamicsChalkItem(DyenamicDyeColor dyeColor, Properties properties) {
        super(dyeColor.getAnalogue(), properties);
        this.color = dyeColor;
    }

    @Override
    public Mark getMark(ItemStack itemInHand, MarkDrawingContext drawingContext, MarkSymbol symbol) {
        return ChalkCompat.convertMark(drawingContext.createMark(color.getColorValue(), symbol, false), color, false);
    }

    @Override
    public int getMarkColorValue(ItemStack stack) {
        return this.color.getColorValue();
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
                } else if (this.drawMark(drawingContext, ChalkCompat.convertMark(getMark(itemStack, drawingContext, drawingContext.getInitialOrientation() == SymbolOrientation.CENTER ? MarkSymbol.CENTER : MarkSymbol.ARROW), this.color, false))) {
                    return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
                } else {
                    return drawingContext.hasExistingMark() ? InteractionResult.PASS : InteractionResult.FAIL;
                }
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    public DyenamicDyeColor getDyenamicsColor() {
        return color;
    }
}
