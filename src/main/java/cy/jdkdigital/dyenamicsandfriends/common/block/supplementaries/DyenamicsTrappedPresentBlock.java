package cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsTrappedPresentBlockEntity;
import net.mehvahdjukaar.supplementaries.common.block.blocks.TrappedPresentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class DyenamicsTrappedPresentBlock extends TrappedPresentBlock
{
    private final Supplier<BlockEntityType<DyenamicsTrappedPresentBlockEntity>> blockEntitySupplier;

    public DyenamicsTrappedPresentBlock(DyenamicDyeColor color, Properties properties, Supplier<BlockEntityType<DyenamicsTrappedPresentBlockEntity>> blockEntitySupplier) {
        super(color.getVanillaColor(), properties);
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsTrappedPresentBlockEntity(this, pos, state);
    }

    public Supplier<BlockEntityType<DyenamicsTrappedPresentBlockEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }
}
