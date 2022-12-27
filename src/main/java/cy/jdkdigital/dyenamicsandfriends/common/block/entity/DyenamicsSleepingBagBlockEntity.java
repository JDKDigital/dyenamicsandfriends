package cy.jdkdigital.dyenamicsandfriends.common.block.entity;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsSleepingBagBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import top.theillusivec4.comforts.common.tileentity.SleepingBagTileEntity;

public class DyenamicsSleepingBagBlockEntity extends SleepingBagTileEntity
{
    private final DyenamicsSleepingBagBlock block;

    public DyenamicsSleepingBagBlockEntity(DyenamicsSleepingBagBlock block, BlockPos pos, BlockState state) {
        super(pos, state, DyeColor.WHITE);
        this.block = block;
    }

    public DyenamicDyeColor getDyenamicColor() {
        return block.getDyenamicColor();
    }

    @Override
    public BlockEntityType<?> getType() {
        return block.getBlockEntitySupplier().get();
    }
}
