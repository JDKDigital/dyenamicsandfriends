package cy.jdkdigital.dyenamicsandfriends.common.block.entity.sleep_tight;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.sleep_tight.DyenamicsHammockBlock;
import net.mehvahdjukaar.sleep_tight.common.tiles.HammockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsHammockBlockEntity extends HammockTile
{
    private final DyenamicsHammockBlock block;

    public DyenamicsHammockBlockEntity(DyenamicsHammockBlock block, BlockPos pos, BlockState state) {
        super(pos, state);
        this.block = block;
    }

    public DyenamicDyeColor getDyenamicColor() {
        return block.getDyenamicColor();
    }

    @Override
    public BlockEntityType<?> getType() {
        return block != null ? block.getBlockEntitySupplier().get() : null;
    }
}
