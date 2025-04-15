package cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish;

import cy.jdkdigital.dyenamicsandfriends.compat.FurnishCompat;
import io.github.wouink.furnish.block.blockentity.StackHoldingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsShowcaseBlockEntity extends StackHoldingBlockEntity
{
    public DyenamicsShowcaseBlockEntity(BlockPos pos, BlockState state) {
        super(FurnishCompat.SHOWCASE_BLOCK_ENTITY.get(), pos, state);
    }
}