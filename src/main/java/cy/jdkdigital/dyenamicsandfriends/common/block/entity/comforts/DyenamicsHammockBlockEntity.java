package cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.illusivesoulworks.comforts.common.block.entity.HammockBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.DyenamicsHammockBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.comforts.DyenamicsHammockBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsHammockBlockEntity extends HammockBlockEntity
{
    private final DyenamicsHammockBlock block;

    public DyenamicsHammockBlockEntity(DyenamicsHammockBlock block, BlockPos pos, BlockState state) {
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
