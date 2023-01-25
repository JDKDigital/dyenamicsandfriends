package cy.jdkdigital.dyenamicsandfriends.common.block.entity;

import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsBotanyPot;
import net.darkhax.botanypots.block.BlockEntityBotanyPot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsBlockEntityBotanyPot extends BlockEntityBotanyPot
{
    public DyenamicsBlockEntityBotanyPot(DyenamicsBotanyPot block, BlockPos pPos, BlockState pBlockState) {
        super(block.getBlockEntitySupplier().get(), pPos, pBlockState);
    }
}
