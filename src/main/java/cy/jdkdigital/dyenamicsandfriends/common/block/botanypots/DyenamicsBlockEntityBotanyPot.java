package cy.jdkdigital.dyenamicsandfriends.common.block.botanypots;

import net.darkhax.botanypots.block.BlockEntityBotanyPot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsBlockEntityBotanyPot extends BlockEntityBotanyPot
{
    private final DyenamicsBotanyPot block;

    public DyenamicsBlockEntityBotanyPot(DyenamicsBotanyPot block, BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
        this.block = block;
    }

    @Override
    public BlockEntityType<?> getType() {
        return block.getBlockEntitySupplier().get();
    }
}
