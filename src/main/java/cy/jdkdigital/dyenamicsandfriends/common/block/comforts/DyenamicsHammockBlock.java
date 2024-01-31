package cy.jdkdigital.dyenamicsandfriends.common.block.comforts;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import com.illusivesoulworks.comforts.common.block.BaseComfortsBlock;
import com.illusivesoulworks.comforts.common.block.RopeAndNailBlock;
import com.illusivesoulworks.comforts.common.block.entity.HammockBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsHammockBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class DyenamicsHammockBlock extends BaseComfortsBlock
{
    private static final VoxelShape HAMMOCK_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
    private static final VoxelShape NORTH_SHAPE = Shapes.or(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 1.0D));
    private static final VoxelShape SOUTH_SHAPE = Shapes.or(Block.box(1.0D, 0.0D, 0.0D, 15.0D, 1.0D, 15.0D), Block.box(0.0D, 0.0D, 15.0D, 16.0D, 1.0D, 16.0D));
    private static final VoxelShape WEST_SHAPE = Shapes.or(Block.box(1.0D, 0.0D, 1.0D, 16.0D, 1.0D, 15.0D), Block.box(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 16.0D));
    private static final VoxelShape EAST_SHAPE = Shapes.or(Block.box(1.0D, 0.0D, 1.0D, 16.0D, 1.0D, 15.0D), Block.box(15.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D));
    private final DyenamicDyeColor color;
    private final Supplier<BlockEntityType<HammockBlockEntity>> blockEntitySupplier;

    public DyenamicsHammockBlock(DyenamicDyeColor color, BlockBehaviour.Properties properties, Supplier<BlockEntityType<HammockBlockEntity>> blockEntitySupplier) {
        super(null, DyeColor.WHITE, properties);
        this.color = color;
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsHammockBlockEntity(this, pos, state);
    }

    public Supplier<BlockEntityType<HammockBlockEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }

    public DyenamicDyeColor getDyenamicColor() {
        return color;
    }

    public static Direction getDirectionToOther(BedPart part, Direction facing) {
        return part == BedPart.FOOT ? facing : facing.getOpposite();
    }

    public static void dropRopeSupport(BlockPos pos, Direction direction, boolean isHead, Level level) {
        BlockPos ropePos = isHead ? pos.relative(direction) : pos.relative(direction.getOpposite());
        BlockState ropeState = level.getBlockState(ropePos);
        if (ropeState.getBlock() instanceof RopeAndNailBlock) {
            level.setBlockAndUpdate(ropePos, (BlockState)ropeState.setValue(RopeAndNailBlock.SUPPORTING, false));
        }
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        final Direction direction = getConnectedDirection(state).getOpposite();
        return switch (direction) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> HAMMOCK_SHAPE;
        };
    }

    @Override
    public void playerWillDestroy(Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        final BedPart bedpart = state.getValue(PART);
        final boolean isHead = bedpart == BedPart.HEAD;
        final Direction direction = state.getValue(FACING);
        final BlockPos otherPos = pos.relative(getDirectionToOther(bedpart, direction));
        dropRopeSupport(pos, direction, isHead, worldIn);
        dropRopeSupport(otherPos, direction, !isHead, worldIn);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        final Direction direction = context.getClickedFace();

        if (direction == Direction.UP || direction == Direction.DOWN) {
            return null;
        }
        final BlockPos blockpos = context.getClickedPos();
        final BlockPos blockpos1 = blockpos.relative(direction);
        final FluidState ifluidstate = context.getLevel().getFluidState(blockpos);
        return context.getLevel().getBlockState(blockpos1).canBeReplaced(context) ? this.defaultBlockState().setValue(FACING, direction).setValue(BaseComfortsBlock.WATERLOGGED, ifluidstate.getType() == Fluids.WATER) : null;
    }

}
