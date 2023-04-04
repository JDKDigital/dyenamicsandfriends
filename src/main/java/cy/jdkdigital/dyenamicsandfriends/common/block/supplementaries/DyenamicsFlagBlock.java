package cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsFlagBlockEntity;
import net.mehvahdjukaar.selene.blocks.WaterBlock;
import net.mehvahdjukaar.selene.map.ExpandedMapData;
import net.mehvahdjukaar.supplementaries.common.block.blocks.FlagBlock;
import net.mehvahdjukaar.supplementaries.common.block.blocks.StickBlock;
import net.mehvahdjukaar.supplementaries.common.block.tiles.FlagBlockTile;
import net.mehvahdjukaar.supplementaries.configs.ServerConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class DyenamicsFlagBlock extends WaterBlock implements EntityBlock
{
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private final Supplier<BlockEntityType<DyenamicsFlagBlockEntity>> blockEntitySupplier;
    private final DyenamicDyeColor color;

    public DyenamicsFlagBlock(DyenamicDyeColor color, Properties properties, Supplier<BlockEntityType<DyenamicsFlagBlockEntity>> blockEntitySupplier) {
        super(properties);
        this.blockEntitySupplier = blockEntitySupplier;
        this.color = color;
        this.registerDefaultState(((this.stateDefinition.any()).setValue(FlagBlock.FACING, Direction.NORTH)).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsFlagBlockEntity(this, pos, state);
    }

    public Supplier<BlockEntityType<DyenamicsFlagBlockEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }

    public DyenamicDyeColor getDyenamicsColor() {
        return color;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 60;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        BlockEntity var7 = world.getBlockEntity(pos);
        ItemStack var10000;
        if (var7 instanceof FlagBlockTile) {
            FlagBlockTile tile = (FlagBlockTile) var7;
            var10000 = tile.getItem(state);
        } else {
            var10000 = super.getCloneItemStack(state, target, world, pos, player);
        }

        return var10000;
    }

    @Override
    public boolean isPossibleToRespawnInThis() {
        return true;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FlagBlock.FACING, rot.rotate(state.getValue(FlagBlock.FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FlagBlock.FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FlagBlock.FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FlagBlock.FACING, WATERLOGGED);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity var7 = world.getBlockEntity(pos);
            if (var7 instanceof DyenamicsFlagBlockEntity tile) {
                tile.setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity var8 = world.getBlockEntity(pos);
        if (var8 instanceof FlagBlockTile tile) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.getItem() instanceof MapItem) {
                if (!world.isClientSide) {
                    MapItemSavedData var10 = MapItem.getSavedData(itemstack, world);
                    if (var10 instanceof ExpandedMapData) {
                        ExpandedMapData data = (ExpandedMapData) var10;
                        data.toggleCustomDecoration(world, pos);
                    }
                }

                return InteractionResult.sidedSuccess(world.isClientSide);
            }

            if (itemstack.isEmpty() && hand == InteractionHand.MAIN_HAND && ServerConfigs.cached.STICK_POLE) {
                if (world.isClientSide) {
                    return InteractionResult.SUCCESS;
                }

                Direction moveDir = player.isShiftKeyDown() ? Direction.DOWN : Direction.UP;
                StickBlock.findConnectedFlag(world, pos.below(), Direction.UP, moveDir, 0);
                StickBlock.findConnectedFlag(world, pos.above(), Direction.DOWN, moveDir, 0);
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }
}
