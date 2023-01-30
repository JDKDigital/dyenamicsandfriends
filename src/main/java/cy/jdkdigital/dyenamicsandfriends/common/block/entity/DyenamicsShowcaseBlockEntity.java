package cy.jdkdigital.dyenamicsandfriends.common.block.entity;

import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsShowcase;
import io.github.wouink.furnish.block.tileentity.ShowcaseTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsShowcaseBlockEntity extends ShowcaseTileEntity
{
    private final DyenamicsShowcase showcase;

    public DyenamicsShowcaseBlockEntity(BlockPos pos, BlockState state, DyenamicsShowcase showcase) {
        super(pos, state);
        this.showcase = showcase;
    }

    @Override
    public BlockEntityType<?> getType() {
        return showcase.getBlockEntitySupplier().get();
    }
}