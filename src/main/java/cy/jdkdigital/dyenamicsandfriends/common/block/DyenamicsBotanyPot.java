package cy.jdkdigital.dyenamicsandfriends.common.block;

import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsBlockEntityBotanyPot;
import net.darkhax.botanypots.block.BlockBotanyPot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class DyenamicsBotanyPot extends BlockBotanyPot
{
    private final Supplier<BlockEntityType<DyenamicsBlockEntityBotanyPot>> blockEntitySupplier;

    public DyenamicsBotanyPot(Properties properties, boolean hasInventory, Supplier<BlockEntityType<DyenamicsBlockEntityBotanyPot>> blockEntitySupplier) {
        super(properties, hasInventory);
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsBlockEntityBotanyPot(this, pos, state);
    }

    public Supplier<BlockEntityType<DyenamicsBlockEntityBotanyPot>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }
}
