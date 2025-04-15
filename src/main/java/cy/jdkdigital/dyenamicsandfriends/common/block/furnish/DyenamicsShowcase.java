package cy.jdkdigital.dyenamicsandfriends.common.block.furnish;

import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsShowcaseBlockEntity;
import io.github.wouink.furnish.block.Showcase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsShowcase extends Showcase
{
    public DyenamicsShowcase(Properties properties) {
        super(properties);
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsShowcaseBlockEntity(pos, state);
    }
}
