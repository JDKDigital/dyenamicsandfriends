package cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish;

import cy.jdkdigital.dyenamicsandfriends.common.block.furnish.DyenamicsAmphora;
import io.github.wouink.furnish.block.tileentity.AmphoraTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsAmphoraBlockEntity extends AmphoraTileEntity
{
    private final DyenamicsAmphora amphora;

    public DyenamicsAmphoraBlockEntity(BlockPos pos, BlockState state, DyenamicsAmphora amphora) {
        super(pos, state);
        this.amphora = amphora;
    }

    @Override
    public BlockEntityType<?> getType() {
        return amphora.getBlockEntitySupplier().get();
    }
}