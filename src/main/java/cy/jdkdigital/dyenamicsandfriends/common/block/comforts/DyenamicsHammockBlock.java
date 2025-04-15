package cy.jdkdigital.dyenamicsandfriends.common.block.comforts;

import com.illusivesoulworks.comforts.common.block.HammockBlock;
import com.illusivesoulworks.comforts.common.block.entity.BaseComfortsBlockEntity;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsHammockBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.compat.ComfortsCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsHammockBlock extends HammockBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsHammockBlock(DyenamicDyeColor color) {
        super(DyeColor.WHITE);
        this.color = color;
    }

    @Override
    public BlockEntityType<? extends BaseComfortsBlockEntity> getBlockEntityType() {
        return ComfortsCompat.HAMMOCK_BLOCK_ENTITY.get();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyenamicsHammockBlockEntity(pos, state);
    }

    public DyenamicDyeColor getDyenamicColor() {
        return color;
    }
}
