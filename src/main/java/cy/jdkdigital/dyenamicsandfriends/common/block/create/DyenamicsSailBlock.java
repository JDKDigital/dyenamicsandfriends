package cy.jdkdigital.dyenamicsandfriends.common.block.create;

import com.simibubi.create.content.contraptions.bearing.SailBlock;
import com.simibubi.create.foundation.utility.BlockHelper;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.compat.CreateCompat;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.placement.IPlacementHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Create compat
public class DyenamicsSailBlock extends SailBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsSailBlock(Properties properties, DyenamicDyeColor color) {
        super(properties, false, color.getAnalogue());
        this.color = color;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (frame)
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        DyenamicDyeColor color = DyenamicDyeColor.getColor(stack);
        if (color != null && color.getId() > 15) {
            if (!level.isClientSide) {
                level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0f, 1.1f - level.random.nextFloat() * .2f);
            }
            applyDye(state, level, pos, hitResult.getLocation(), color);
            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public static void applyDye(BlockState state, Level level, BlockPos pos, Vec3 hit, DyenamicDyeColor color) {
        BlockState newState = CreateCompat.SAILS.get(color).get().defaultBlockState();
        newState = BlockHelper.copyProperties(state, newState);

        // Dye the block itself
        if (state != newState) {
            level.setBlockAndUpdate(pos, newState);
            return;
        }

        // Dye all adjacent
        List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, hit, state.getValue(FACING).getAxis());
        for (Direction d : directions) {
            BlockPos offset = pos.relative(d);
            BlockState adjacentState = level.getBlockState(offset);
            Block block = adjacentState.getBlock();
            if (!(block instanceof SailBlock) || ((SailBlock) block).isFrame()) {
                continue;
            }
            if (state.getValue(FACING) != adjacentState.getValue(FACING)) {
                continue;
            }
            if (state == adjacentState) {
                continue;
            }
            level.setBlockAndUpdate(offset, newState);
            return;
        }

        // Dye all the things
        List<BlockPos> frontier = new ArrayList<>();
        frontier.add(pos);
        Set<BlockPos> visited = new HashSet<>();
        int timeout = 100;
        while (!frontier.isEmpty()) {
            if (timeout-- < 0)
                break;

            BlockPos currentPos = frontier.removeFirst();
            visited.add(currentPos);

            for (Direction d : Iterate.directions) {
                if (d.getAxis() == state.getValue(FACING).getAxis()) {
                    continue;
                }
                BlockPos offset = currentPos.relative(d);
                if (visited.contains(offset)) {
                    continue;
                }
                BlockState adjacentState = level.getBlockState(offset);
                Block block = adjacentState.getBlock();
                if (!(block instanceof SailBlock) || ((SailBlock) block).isFrame() && color != null) {
                    continue;
                }
                if (adjacentState.getValue(FACING) != state.getValue(FACING)) {
                    continue;
                }
                if (state != adjacentState) {
                    level.setBlockAndUpdate(offset, newState);
                }
                frontier.add(offset);
                visited.add(offset);
            }
        }
    }
}
