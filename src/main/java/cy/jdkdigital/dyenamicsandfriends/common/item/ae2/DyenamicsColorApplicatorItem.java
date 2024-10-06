package cy.jdkdigital.dyenamicsandfriends.common.item.ae2;

import appeng.api.implementations.parts.ICablePart;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.StorageCell;
import appeng.api.util.AECableType;
import appeng.api.util.AEColor;
import appeng.block.networking.CableBusBlock;
import appeng.blockentity.networking.CableBusBlockEntity;
import appeng.core.definitions.AEParts;
import appeng.items.tools.powered.ColorApplicatorItem;
import appeng.parts.networking.CablePart;
import appeng.util.Platform;
import cy.jdkdigital.dyenamics.common.items.DyenamicDyeItem;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.compat.Ae2Compat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class DyenamicsColorApplicatorItem extends ColorApplicatorItem
{
    public DyenamicsColorApplicatorItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack is, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        // TODO sheep coloring
        return super.interactLivingEntity(is, player, interactionTarget, usedHand);
    }

    @Override
    public Component getName(ItemStack is) {
        // TODO
        return super.getName(is);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack is = context.getItemInHand();
        Direction side = context.getClickedFace();
        Player p = context.getPlayer();
        if (p == null && level instanceof ServerLevel) {
            p = Platform.getFakePlayer((ServerLevel)level, null);
        }

        ItemStack paintBall = this.getColor(is);

        if (!paintBall.isEmpty() && paintBall.getItem() instanceof DyenamicDyeItem dyenamicDyeItem) {
            AEItemKey paintBallKey = AEItemKey.of(paintBall);
            StorageCell inv = StorageCells.getCellInventory(is, null);
            if (inv != null) {
                Block blk = level.getBlockState(pos).getBlock();
                DyenamicDyeColor color = dyenamicDyeItem.getDyeColor();
                if (color != null && this.getAECurrentPower(is) > 100.0D && this.recolourBlock(blk, side, level, pos, color, p)) {
                    this.consumeItem(is, paintBallKey, false);
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
            }
        }

        return super.useOn(context);
    }

    private boolean recolourBlock(Block blk, Direction side, Level level, BlockPos pos, DyenamicDyeColor newColor, @Nullable Player player) {
        var state = level.getBlockState(pos);
        Block recolored = Ae2Compat.BlockRecolorer.recolor(blk, newColor);

        if (recolored != blk) {
            BlockState newState = recolored.defaultBlockState();
            for (Property<?> prop : newState.getProperties()) {
                newState = copyProp(state, newState, prop);
            }

            return level.setBlockAndUpdate(pos, newState);
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof CableBusBlockEntity cableBusBlockEntity && player != null) {
            IPart cable = cableBusBlockEntity.getCableBus().getPart(null);
            if (cable instanceof CablePart pc) {
                return changeCableColor(pc, newColor, player);
            }
        }

        // TODO
//        BlockEntity be = level.getBlockEntity(pos);
//        if (be instanceof IColorableBlockEntity ct) {
//            if (ct.getColor() != newColor) {
//                ct.recolourBlock(side, newColor, p);
//                return true;
//            }
//        }

        return false;
    }

    private static <T extends Comparable<T>> BlockState copyProp(BlockState oldState, BlockState newState, Property<T> prop) {
        if (newState.hasProperty(prop)) {
            return newState.setValue(prop, oldState.getValue(prop));
        }
        return newState;
    }

    @Override
    public boolean isBlackListed(ItemStack cellItem, AEKey requestedAddition) {
        if (requestedAddition instanceof AEItemKey itemKey) {
            if (itemKey.getItem() instanceof DyenamicDyeItem) {
                return false;
            }
        }
        return super.isBlackListed(cellItem, requestedAddition);
    }

    static boolean changeCableColor(CablePart cable, DyenamicDyeColor newColor, Player who) {
        if (!(cable.getPartItem() instanceof DyenamicsColoredPartItem partItem) || partItem.color != newColor) {
            IPartItem<?> newPart = null;
            if (cable.getCableConnectionType() == AECableType.GLASS) {
                newPart = Ae2Compat.GLASS_CABLES.get(newColor).get();
            } else if (cable.getCableConnectionType() == AECableType.COVERED) {
                newPart = Ae2Compat.COVERED_CABLES.get(newColor).get();
            } else if (cable.getCableConnectionType() == AECableType.SMART) {
                newPart = Ae2Compat.SMART_CABLES.get(newColor).get();
            } else if (cable.getCableConnectionType() == AECableType.DENSE_COVERED) {
                newPart = Ae2Compat.COVERED_DENSE_CABLES.get(newColor).get();
            } else if (cable.getCableConnectionType() == AECableType.DENSE_SMART) {
                newPart = Ae2Compat.SMART_DENSE_CABLES.get(newColor).get();
            }

            if (newPart != null) {
                if (cable.isClientSide()) {
                    return true;
                }

                try {
                    Ae2Compat.setPartItemHandle.invokeWithArguments(cable, newPart);
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                cable.getMainNode().setGridColor(cable.getCableColor());
                cable.getHost().markForUpdate();
                cable.getHost().markForSave();
                return true;
            }
        }

        return false;
    }
}
