package cy.jdkdigital.dyenamicsandfriends.common.block.chalk;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.mojang.math.Vector3f;
import cy.jdkdigital.dyenamicsandfriends.compat.ChalkCompat;
import io.github.mortuusars.chalk.Chalk;
import io.github.mortuusars.chalk.block.ChalkMarkBlock;
import io.github.mortuusars.chalk.utils.PositionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
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
        this.removeMarkWithEffects(world, pos);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        BlockPos relative = pos.relative(state.getValue(FACING).getOpposite());
        if (relative.equals(fromPos)) {
            this.removeMarkWithEffects(world, pos);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return this.removeMarkWithEffects(world, pos);
    }

    private boolean removeMarkWithEffects(Level level, BlockPos pos) {
        Direction facing = level.getBlockState(pos).getValue(FACING); // Get facing before removing the block.

        if (level.removeBlock(pos, false)) {
            level.playSound(null, pos, Chalk.SoundEvents.MARK_REMOVED.get(), SoundSource.BLOCKS, 0.5f, new Random().nextFloat() * 0.2f + 0.8f);

            if (level instanceof ServerLevel serverLevel) {
                int colorValue = this.color.getColorValue();
                float R = (colorValue & 0x00FF0000) >> 16;
                float G = (colorValue & 0x0000FF00) >> 8;
                float B = (colorValue & 0x000000FF);

                Vector3f centerOffset = PositionUtils.blockCenterOffsetToFace(pos, facing, 0.25f);
                serverLevel.sendParticles(new DustParticleOptions(new Vector3f(R / 255, G / 255, B / 255), 2f),
                        centerOffset.x(), centerOffset.y(), centerOffset.z(),
                        1, 0.1, 0.1, 0.1, 0.02);
            } else {
                ChalkCompat.spawnColorDustParticles(color, level, pos, facing);
            }

            return true;
        }
        return false;
    }
}
