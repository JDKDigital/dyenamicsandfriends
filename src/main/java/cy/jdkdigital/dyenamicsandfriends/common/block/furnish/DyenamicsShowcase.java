package cy.jdkdigital.dyenamicsandfriends.common.block.furnish;

import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsShowcaseBlockEntity;
import io.github.wouink.furnish.block.Showcase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class DyenamicsShowcase extends Showcase
{
    private final Supplier<BlockEntityType<DyenamicsShowcaseBlockEntity>> blockEntitySupplier;

    public DyenamicsShowcase(Properties properties, Supplier<BlockEntityType<DyenamicsShowcaseBlockEntity>> blockEntitySupplier) {
        super(properties);
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsShowcaseBlockEntity(pos, state, this);
    }

    public Supplier<BlockEntityType<DyenamicsShowcaseBlockEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }
}
