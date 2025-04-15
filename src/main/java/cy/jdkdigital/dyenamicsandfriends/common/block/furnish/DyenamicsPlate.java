package cy.jdkdigital.dyenamicsandfriends.common.block.furnish;

import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsPlateBlockEntity;
import io.github.wouink.furnish.block.Plate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsPlate extends Plate
{
    public DyenamicsPlate(Properties properties) {
        super(properties);
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsPlateBlockEntity(pos, state);
    }
}
