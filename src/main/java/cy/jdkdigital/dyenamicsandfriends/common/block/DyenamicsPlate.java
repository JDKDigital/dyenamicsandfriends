package cy.jdkdigital.dyenamicsandfriends.common.block;

import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsPlateBlockEntity;
import io.github.wouink.furnish.block.Plate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class DyenamicsPlate extends Plate
{
    private final Supplier<BlockEntityType<DyenamicsPlateBlockEntity>> blockEntitySupplier;

    public DyenamicsPlate(Properties properties, Supplier<BlockEntityType<DyenamicsPlateBlockEntity>> blockEntitySupplier) {
        super(properties);
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsPlateBlockEntity(pos, state, this);
    }

    public Supplier<BlockEntityType<DyenamicsPlateBlockEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }
}
