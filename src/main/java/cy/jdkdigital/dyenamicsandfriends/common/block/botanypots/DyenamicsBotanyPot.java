package cy.jdkdigital.dyenamicsandfriends.common.block.botanypots;

import net.darkhax.botanypots.block.BlockBotanyPot;
import net.darkhax.botanypots.block.BlockEntityBotanyPot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
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

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldLevel, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, blockEntitySupplier.get(), BlockEntityBotanyPot::tickPot);
    }
}
