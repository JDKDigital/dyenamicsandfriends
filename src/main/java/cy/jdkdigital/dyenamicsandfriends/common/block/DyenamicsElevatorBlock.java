package cy.jdkdigital.dyenamicsandfriends.common.block;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsElevatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import xyz.vsngamer.elevatorid.blocks.ElevatorBlock;
import xyz.vsngamer.elevatorid.tile.ElevatorTileEntity;
import xyz.vsngamer.elevatorid.util.FakeUseContext;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class DyenamicsElevatorBlock extends ElevatorBlock
{
    private final DyenamicDyeColor color;
    private final Supplier<BlockEntityType<ElevatorTileEntity>> blockEntitySupplier;

    public DyenamicsElevatorBlock(DyenamicDyeColor color, Supplier<BlockEntityType<ElevatorTileEntity>> blockEntitySupplier) {
        super(DyeColor.WHITE);
        this.color = color;
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsElevatorBlockEntity(this, pos, state);
    }

    public Supplier<BlockEntityType<ElevatorTileEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return color.getLightValue();
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, Level worldIn, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand handIn, @Nonnull BlockHitResult hit) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack handStack = player.getItemInHand(handIn);
            DyenamicsElevatorBlockEntity tile = this.getElevatorTile(worldIn, pos);
            if (tile == null) {
                return InteractionResult.FAIL;
            } else {
                Block handBlock = Block.byItem(handStack.getItem());
                BlockState stateToApply = handBlock.getStateForPlacement(new FakeUseContext(player, handIn, hit));
                if (tile.setCamoAndUpdate(stateToApply)) {
                    return InteractionResult.SUCCESS;
                } else if (player.isCrouching() && tile.getHeldState() != null) {
                    tile.setCamoAndUpdate(null);
                    return InteractionResult.SUCCESS;
                } else {
                    NetworkHooks.openScreen((ServerPlayer)player, tile, pos);
                    return InteractionResult.SUCCESS;
                }
            }
        }
    }

    private DyenamicsElevatorBlockEntity getElevatorTile(BlockGetter world, BlockPos pos) {
        if (world != null && pos != null) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof DyenamicsElevatorBlockEntity && tile.getType().isValid(world.getBlockState(pos))) {
                return (DyenamicsElevatorBlockEntity) tile;
            }
        }
        return null;
    }
}
