package cy.jdkdigital.dyenamicsandfriends.common.block.furnish;

import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsAmphoraBlockEntity;
import io.github.wouink.furnish.block.Amphora;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsAmphora extends Amphora
{
    public DyenamicsAmphora(Properties properties) {
        super(properties);
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsAmphoraBlockEntity(pos, state);
    }
}
