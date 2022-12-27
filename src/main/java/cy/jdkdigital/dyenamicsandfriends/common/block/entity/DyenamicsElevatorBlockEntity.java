package cy.jdkdigital.dyenamicsandfriends.common.block.entity;

import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsElevatorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.vsngamer.elevatorid.tile.ElevatorTileEntity;

public class DyenamicsElevatorBlockEntity extends ElevatorTileEntity
{
    private final DyenamicsElevatorBlock block;

    public DyenamicsElevatorBlockEntity(DyenamicsElevatorBlock block, BlockPos pos, BlockState state) {
        super(pos, state);
        this.block = block;
    }

    @Override
    public BlockEntityType<?> getType() {
        return block.getBlockEntitySupplier().get();
    }
}
