package cy.jdkdigital.dyenamicsandfriends.common.block.chalk;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.compat.ChalkCompat;
import io.github.mortuusars.chalk.blocks.ChalkMarkBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.HitResult;

import java.util.Random;

public class DyenamicsChalkMarkBlock extends ChalkMarkBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsChalkMarkBlock(DyenamicDyeColor dyeColor, Properties properties) {
        super(dyeColor.getAnalogue(), properties);
        this.color = dyeColor;
    }

    public DyenamicDyeColor getDyenamicColor() {
        return color;
    }

    @Override
    public MutableComponent getName() {
        return super.getName();
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState blockState) {
        return new ItemStack(ChalkCompat.CHALKS.get(color).get());
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        if (player.isCreative()) {
            return new ItemStack(ChalkCompat.CHALKS.get(color).get());
        } else {
            ItemStack item = this.getMatchingItemStack(player, ChalkCompat.CHALKS.get(color).get());
            return item == ItemStack.EMPTY ? new ItemStack(ChalkCompat.CHALKS.get(color).get()) : item;
        }
    }

    private ItemStack getMatchingItemStack(Player player, Item item) {
        return player.getInventory().items.stream().filter((invItem) -> {
            return invItem.getItem().equals(item);
        }).findFirst().orElse(ItemStack.EMPTY);
    }

    @Override
    public void attack(BlockState blockState, Level world, BlockPos pos, Player player) {
        this.removeMark(world, pos, false);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        BlockPos relative = pos.relative(state.getValue(FACING).getOpposite());
        if (relative.equals(fromPos)) {
            this.removeMark(world, pos, isMoving);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return this.removeMark(world, pos, false);
    }

    private boolean removeMark(Level level, BlockPos pos, boolean isMoving) {
        Direction facing = level.getBlockState(pos).getValue(FACING);
        if (level.removeBlock(pos, isMoving)) {
            if (!level.isClientSide()) {
                level.playSound(null, pos, SoundEvents.WART_BLOCK_HIT, SoundSource.BLOCKS, 0.5F, (new Random()).nextFloat() * 0.2F + 0.8F);
            } else {
                ChalkCompat.spawnColorDustParticles(color, level, pos, facing);
            }
            return true;
        }
        return false;
    }
}
