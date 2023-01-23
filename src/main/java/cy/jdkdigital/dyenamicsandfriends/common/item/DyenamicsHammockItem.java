package cy.jdkdigital.dyenamicsandfriends.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import top.theillusivec4.comforts.common.block.RopeAndNailBlock;

import javax.annotation.Nonnull;

import static top.theillusivec4.comforts.common.block.RopeAndNailBlock.HORIZONTAL_FACING;
import static top.theillusivec4.comforts.common.block.RopeAndNailBlock.SUPPORTING;

public class DyenamicsHammockItem extends BlockItem
{
    public DyenamicsHammockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    // Recreate mod item because Comforts did not do good code
    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        final Level world = context.getLevel();
        final BlockPos pos = context.getClickedPos();
        final BlockState state = world.getBlockState(pos);
        final Player player = context.getPlayer();

        if (state.getBlock() instanceof RopeAndNailBlock) {
            final Direction direction = state.getValue(HORIZONTAL_FACING);
            final BlockPos blockpos = pos.relative(direction, 3);
            final BlockState blockstate = world.getBlockState(blockpos);

            if (hasPartneredRopes(state, blockstate)) {
                InteractionResult result = this.place(BlockPlaceContext.at(new BlockPlaceContext(context), context.getClickedPos().relative(direction), direction));

                if (result.consumesAction()) {
                    world.setBlockAndUpdate(pos, state.setValue(SUPPORTING, true));
                    world.setBlockAndUpdate(blockpos, blockstate.setValue(SUPPORTING, true));
                } else {
                    if (player != null) {
                        player.displayClientMessage(new TranslatableComponent("block.comforts.hammock.no_space"), true);
                    }
                }
                return result;
            } else if (player != null) {
                boolean flag = hasPartneredRopes(state, world.getBlockState(pos.relative(direction, 1)));
                flag = flag || hasPartneredRopes(state, world.getBlockState(pos.relative(direction, 2)));

                if (flag) {
                    player.displayClientMessage(new TranslatableComponent("block.comforts.hammock.no_space"), true);
                } else {
                    player.displayClientMessage(new TranslatableComponent("block.comforts.hammock.missing_rope"), true);
                }
            }
        } else if (player != null) {
            player.displayClientMessage(new TranslatableComponent("block.comforts.hammock.no_rope"), true);
        }
        return InteractionResult.FAIL;
    }

    private boolean hasPartneredRopes(BlockState state, BlockState otherState) {
        return otherState.getBlock() instanceof RopeAndNailBlock && otherState.getValue(HORIZONTAL_FACING) == state.getValue(HORIZONTAL_FACING).getOpposite() && !state.getValue(SUPPORTING) && !otherState.getValue(SUPPORTING);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, @Nonnull BlockState state) {
        return context.getLevel().setBlock(context.getClickedPos(), state, 26);
    }
}
