package cy.jdkdigital.dyenamicsandfriends.common.block.ceramics;

import cy.jdkdigital.dyenamicsandfriends.common.block.entity.ceramics.DyenamicsCisternBlockEntity;
import knightminer.ceramics.blocks.FluidCisternBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class DyenamicsFluidCisternBlock extends FluidCisternBlock
{
    private final Supplier<BlockEntityType<DyenamicsCisternBlockEntity>> blockEntitySupplier;

    public DyenamicsFluidCisternBlock(Properties properties, boolean crackable, Supplier<BlockEntityType<DyenamicsCisternBlockEntity>> blockEntitySupplier) {
        super(properties, crackable);
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsCisternBlockEntity(this, pos, state, isCrackable());
    }

    public Supplier<BlockEntityType<DyenamicsCisternBlockEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }
}
