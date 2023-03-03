package cy.jdkdigital.dyenamicsandfriends.common.block.entity;

import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsFluidCisternBlock;
import knightminer.ceramics.blocks.entity.CisternBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsCisternBlockEntity extends CisternBlockEntity
{
    private final DyenamicsFluidCisternBlock block;

    public DyenamicsCisternBlockEntity(DyenamicsFluidCisternBlock block, BlockPos pos, BlockState state, boolean crackable) {
        super(pos, state, crackable);
        this.block = block;
    }

    @Override
    public BlockEntityType<?> getType() {
        return block.getBlockEntitySupplier().get();
    }
}
