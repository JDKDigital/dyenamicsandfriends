package cy.jdkdigital.dyenamicsandfriends.common.block.entity;

import com.starfish_studios.another_furniture.block.entity.CurtainBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsCurtainBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsCurtainBlockEntity extends CurtainBlockEntity
{
    private final DyenamicsCurtainBlock block;

    public DyenamicsCurtainBlockEntity(DyenamicsCurtainBlock block, BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
        this.block = block;
    }

    @Override
    public BlockEntityType<?> getType() {
        return block.getBlockEntitySupplier().get();
    }
}
