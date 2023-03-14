package cy.jdkdigital.dyenamicsandfriends.common.block.entity.create;

import com.simibubi.create.content.contraptions.components.crank.HandCrankTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicReference;

public class DyenamicsValveHandleBlockEntity extends HandCrankTileEntity
{
    public DyenamicsValveHandleBlockEntity(AtomicReference<BlockEntityType<?>> type, BlockPos pos, BlockState state) {
        super(type.get(), pos, state);
    }
}
