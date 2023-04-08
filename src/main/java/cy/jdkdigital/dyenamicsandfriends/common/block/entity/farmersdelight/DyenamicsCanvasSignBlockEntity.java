package cy.jdkdigital.dyenamicsandfriends.common.block.entity.farmersdelight;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.common.block.farmersdelight.DyenamicsStandingCanvasSignBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.farmersdelight.DyenamicsWallCanvasSignBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.entity.CanvasSignBlockEntity;
import vectorwing.farmersdelight.common.block.state.CanvasSign;

public class DyenamicsCanvasSignBlockEntity extends CanvasSignBlockEntity
{
    private final CanvasSign block;
    private DyenamicDyeColor textColor = DyenamicDyeColor.BLACK;

    public DyenamicsCanvasSignBlockEntity(BlockPos pos, BlockState state, CanvasSign block) {
        super(pos, state);
        this.block = block;
    }

    public DyenamicDyeColor getDyenamicsColor() {
        if (block instanceof DyenamicsStandingCanvasSignBlock standingCanvasSignBlock) {
            return standingCanvasSignBlock.getDyenamicColor();
        }
        if (block instanceof DyenamicsWallCanvasSignBlock wallCanvasSignBlock) {
            return wallCanvasSignBlock.getDyenamicColor();
        }
        throw new RuntimeException("Invalid block type");
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        if (block instanceof DyenamicsStandingCanvasSignBlock standingCanvasSignBlock) {
            return standingCanvasSignBlock.getBlockEntitySupplier().get();
        }
        if (block instanceof DyenamicsWallCanvasSignBlock wallCanvasSignBlock) {
            return wallCanvasSignBlock.getBlockEntitySupplier().get();
        }
        throw new RuntimeException("Invalid block type");
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.textColor = pTag.contains("DyenamicColor") ? DyenamicDyeColor.byId(pTag.getInt("DyenamicColor")) : DyenamicDyeColor.BLACK;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (this.textColor != null) {
            pTag.putInt("DyenamicColor", this.textColor.getId());
        }
    }

    @Override
    public boolean setColor(DyeColor pColor) {
        return setTextColor(DyenamicDyeColor.byId(pColor.getId()));
    }

    public boolean setTextColor(DyenamicDyeColor dyeColor) {
        super.setColor(dyeColor.getAnalogue());
        if (dyeColor != this.getTextColor()) {
            this.textColor = dyeColor;
            this.markUpdated();
            return true;
        }
        return false;
    }

    public DyenamicDyeColor getTextColor() {
        return textColor;
    }

    private void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}
