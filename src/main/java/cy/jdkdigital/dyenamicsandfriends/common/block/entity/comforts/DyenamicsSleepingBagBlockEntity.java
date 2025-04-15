package cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts;

import com.illusivesoulworks.comforts.common.block.entity.BaseComfortsBlockEntity;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.comforts.DyenamicsSleepingBagBlock;
import cy.jdkdigital.dyenamicsandfriends.compat.ComfortsCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class DyenamicsSleepingBagBlockEntity extends BaseComfortsBlockEntity
{
    public DyenamicsSleepingBagBlockEntity(BlockPos pos, BlockState state) {
        super(ComfortsCompat.SLEEPING_BAG_BLOCK_ENTITY.get(), pos, state, DyeColor.WHITE);
    }

    public DyenamicDyeColor getDyenamicColor() {
        DyenamicsSleepingBagBlock block = (DyenamicsSleepingBagBlock) this.getBlockState().getBlock();
        return block.getDyenamicColor();
    }

    @Override
    @Nonnull
    public Component getName() {
        return this.name != null ? this.name : Component.translatable("block.dyenamicsandfriends.comforts_" + getDyenamicColor().getSerializedName() + "_sleeping_bag");
    }
}
