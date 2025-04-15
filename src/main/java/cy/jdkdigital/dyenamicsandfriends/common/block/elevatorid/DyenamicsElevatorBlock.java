package cy.jdkdigital.dyenamicsandfriends.common.block.elevatorid;

import com.vsngarcia.level.ElevatorBlockEntityBase;
import com.vsngarcia.neoforge.ElevatorBlock;
import com.vsngarcia.util.FakeUseContext;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.elevatorid.DyenamicsElevatorBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.compat.ElevatoridCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class DyenamicsElevatorBlock extends ElevatorBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsElevatorBlock(DyenamicDyeColor color) {
        super(DyeColor.WHITE);
        this.color = color;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsElevatorBlockEntity(pos, state);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return color.getLightValue();
    }

    @Override
    public ItemInteractionResult useItemOn(
            ItemStack itemStack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand handIn,
            BlockHitResult hit
    ) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        return getElevatorBlockEntity(level, pos).map(tile -> {
            Block handBlock = Block.byItem(player.getItemInHand(handIn).getItem());
            BlockState stateToApply = handBlock.getStateForPlacement(new FakeUseContext(player, handIn, hit));
            if (stateToApply != null && tile.setCamoAndUpdate(stateToApply)) {
                return ItemInteractionResult.SUCCESS; // If we successfully set camo, don't open the menu
            }
            // Remove camo
            if (player.isCrouching() && tile.getHeldState() != null) {
                tile.setCamoAndUpdate(null);
                return ItemInteractionResult.SUCCESS;
            }

            openMenu(player, tile, pos);
            return ItemInteractionResult.SUCCESS;
        }).orElse(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
    }

    private Optional<? extends ElevatorBlockEntityBase> getElevatorBlockEntity(BlockGetter level, BlockPos pos) {
        if (level == null || pos == null) {
            return Optional.empty();
        }

        return level.getBlockEntity(pos, ElevatoridCompat.ELEVATOR_BLOCK_ENTITY.get());
    }
}
