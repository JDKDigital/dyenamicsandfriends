package cy.jdkdigital.dyenamicsandfriends.common.block.create;

import com.simibubi.create.content.contraptions.bearing.SailBlock;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.placement.IPlacementHelper;
import cy.jdkdigital.dyenamicsandfriends.compat.CreateCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        ItemStack heldItem = player.getItemInHand(hand);

        DyenamicDyeColor color = DyenamicDyeColor.getColor(heldItem);
        if (color != null && color.getId() > 15) {
            if (!level.isClientSide) {
                applyDye(state, level, pos, ray.getLocation(), color);
            }
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, ray);
    }

    public static void applyDye(BlockState state, Level world, BlockPos pos, Vec3 hit, DyenamicDyeColor color) {
        BlockState newState = CreateCompat.SAILS.get(color).get().defaultBlockState();
        // https://github.com/Creators-of-Create/Create/blob/mc1.20.1/dev/src/main/java/com/simibubi/create/content/contraptions/bearing/SailBlock.java#L110C3-L167C4
        newState = BlockHelper.copyProperties(state, newState);

        // Dye the block itself
        if (state != newState) {
            world.setBlockAndUpdate(pos, newState);
            return;
        }

        // Dye all adjacent
        List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, hit, state.getValue(FACING)
                .getAxis());
        for (Direction d : directions) {
            BlockPos offset = pos.relative(d);
            BlockState adjacentState = world.getBlockState(offset);
            Block block = adjacentState.getBlock();
            if (!(block instanceof DyenamicsSailBlock) || ((DyenamicsSailBlock) block).frame)
                continue;
            if (state.getValue(FACING) != adjacentState.getValue(FACING))
                continue;
            if (state == adjacentState)
                continue;
            world.setBlockAndUpdate(offset, newState);
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

            BlockPos currentPos = frontier.remove(0);
            visited.add(currentPos);

            for (Direction d : Iterate.directions) {
                if (d.getAxis() == state.getValue(FACING).getAxis())
                    continue;
                BlockPos offset = currentPos.relative(d);
                if (visited.contains(offset))
                    continue;
                BlockState adjacentState = world.getBlockState(offset);
                Block block = adjacentState.getBlock();
                if (!(block instanceof DyenamicsSailBlock) || ((DyenamicsSailBlock) block).frame && color != null)
                    continue;
                if (adjacentState.getValue(FACING) != state.getValue(FACING))
                    continue;
                if (state != adjacentState)
                    world.setBlockAndUpdate(offset, newState);
                frontier.add(offset);
                visited.add(offset);
            }
        }
    }
}
