package cy.jdkdigital.dyenamicsandfriends.common.block.farmersdelight;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.farmersdelight.DyenamicsCanvasSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.WallCanvasSignBlock;

import java.util.function.Supplier;

public class DyenamicsWallCanvasSignBlock extends WallCanvasSignBlock
{
    private final Supplier<BlockEntityType<DyenamicsCanvasSignBlockEntity>> blockEntitySupplier;
    private final DyenamicDyeColor color;

    public DyenamicsWallCanvasSignBlock(DyenamicDyeColor color, Supplier<BlockEntityType<DyenamicsCanvasSignBlockEntity>> blockEntitySupplier) {
        super(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), color.getAnalogue());
        this.color = color;
        this.blockEntitySupplier = blockEntitySupplier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsCanvasSignBlockEntity(pos, state, this);
    }

    public Supplier<BlockEntityType<DyenamicsCanvasSignBlockEntity>> getBlockEntitySupplier() {
        return blockEntitySupplier;
    }

    public DyenamicDyeColor getDyenamicColor() {
        return color;
    }

    @Nullable
    @Override
    public DyeColor getBackgroundColor() {
        return color.getAnalogue();
    }

    @Override
    public boolean isDarkBackground() {
        return color.equals(DyenamicDyeColor.MAROON) || color.equals(DyenamicDyeColor.AQUAMARINE);
    }
}
