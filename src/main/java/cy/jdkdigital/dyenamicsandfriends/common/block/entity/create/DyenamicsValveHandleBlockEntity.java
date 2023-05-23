package cy.jdkdigital.dyenamicsandfriends.common.block.entity.create;

import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicReference;

public class DyenamicsValveHandleBlockEntity extends HandCrankBlockEntity
{
    public DyenamicsValveHandleBlockEntity(AtomicReference<BlockEntityType<?>> type, BlockPos pos, BlockState state) {
        super(type.get(), pos, state);
    }
}
