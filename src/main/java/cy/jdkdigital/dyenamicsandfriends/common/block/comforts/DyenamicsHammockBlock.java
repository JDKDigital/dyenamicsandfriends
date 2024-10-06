package cy.jdkdigital.dyenamicsandfriends.common.block.comforts;

import com.illusivesoulworks.comforts.common.block.HammockBlock;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import com.illusivesoulworks.comforts.common.block.entity.BaseComfortsBlockEntity;
import com.illusivesoulworks.comforts.common.block.entity.HammockBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsHammockBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class DyenamicsHammockBlock extends HammockBlock
{
    private final DyenamicDyeColor color;
    private final Supplier<BlockEntityType<HammockBlockEntity>> blockEntitySupplier;

    public DyenamicsHammockBlock(DyenamicDyeColor color, BlockBehaviour.Properties properties, Supplier<BlockEntityType<HammockBlockEntity>> blockEntitySupplier) {
        super(DyeColor.WHITE);
        this.color = color;
        this.blockEntitySupplier = blockEntitySupplier;
    }

    @Override
    public BlockEntityType<? extends BaseComfortsBlockEntity> getBlockEntityType() {
        return this.blockEntitySupplier != null ? this.blockEntitySupplier.get() : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsHammockBlockEntity(this, pos, state);
    }

    public Supplier<BlockEntityType<HammockBlockEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }

    public DyenamicDyeColor getDyenamicColor() {
        return color;
    }
}
