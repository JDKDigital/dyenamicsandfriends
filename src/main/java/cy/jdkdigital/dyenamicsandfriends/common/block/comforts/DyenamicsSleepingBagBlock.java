package cy.jdkdigital.dyenamicsandfriends.common.block.comforts;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.illusivesoulworks.comforts.common.block.BaseComfortsBlock;
import com.illusivesoulworks.comforts.common.block.entity.BaseComfortsBlockEntity;
import com.illusivesoulworks.comforts.common.block.entity.SleepingBagBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsSleepingBagBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class DyenamicsSleepingBagBlock extends BaseComfortsBlock
{
    private static final VoxelShape SLEEPING_BAG_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    private final DyenamicDyeColor color;
    private final Supplier<BlockEntityType<SleepingBagBlockEntity>> blockEntitySupplier;

    public DyenamicsSleepingBagBlock(DyenamicDyeColor color, BlockBehaviour.Properties properties, Supplier<BlockEntityType<SleepingBagBlockEntity>> blockEntitySupplier) {
        super(null, DyeColor.WHITE, properties);
        this.color = color;
        this.blockEntitySupplier = blockEntitySupplier;
    }

    @Override
    public BlockEntityType<? extends BaseComfortsBlockEntity> getBlockEntityType() {
        return this.blockEntitySupplier != null ? this.blockEntitySupplier.get() : null;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsSleepingBagBlockEntity(this, pos, state);
    }

    public Supplier<BlockEntityType<SleepingBagBlockEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }

    public DyenamicDyeColor getDyenamicColor() {
        return color;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SLEEPING_BAG_SHAPE;
    }
}
