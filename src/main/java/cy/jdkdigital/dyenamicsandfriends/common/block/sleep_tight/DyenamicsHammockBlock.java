package cy.jdkdigital.dyenamicsandfriends.common.block.sleep_tight;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.sleep_tight.DyenamicsHammockBlockEntity;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.mehvahdjukaar.sleep_tight.common.blocks.HammockBlock;
import net.mehvahdjukaar.sleep_tight.common.tiles.HammockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DyenamicsHammockBlock extends HammockBlock
{
    private final DyenamicDyeColor color;
    private final Supplier<BlockEntityType<HammockTile>> blockEntitySupplier;

    public DyenamicsHammockBlock(DyenamicDyeColor color, Supplier<BlockEntityType<HammockTile>> blockEntitySupplier) {
        super(color.getVanillaColor());
        this.color = color;
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART).isMaster() ? new DyenamicsHammockBlockEntity(this, pos, state) : null;
    }

    public Supplier<BlockEntityType<HammockTile>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }

    public DyenamicDyeColor getDyenamicColor() {
        return color;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? Utils.getTicker(type, getBlockEntitySupplier().get(), DyenamicsHammockBlockEntity::tick) : null;
    }
}
