package cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish;

import cy.jdkdigital.dyenamicsandfriends.compat.FurnishCompat;
import io.github.wouink.furnish.block.blockentity.StackHoldingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsPlateBlockEntity extends StackHoldingBlockEntity
{
    public DyenamicsPlateBlockEntity(BlockPos pos, BlockState state) {
        super(FurnishCompat.PLATE_BLOCK_ENTITY.get(), pos, state);
    }
}