package cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish;

import cy.jdkdigital.dyenamicsandfriends.compat.FurnishCompat;
import io.github.wouink.furnish.block.blockentity.FurnishInventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsAmphoraBlockEntity extends FurnishInventoryBlockEntity
{
    public DyenamicsAmphoraBlockEntity(BlockPos pos, BlockState state) {
        super(FurnishCompat.AMPHORA_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public int getCapacity() {
        return 9;
    }

    @Override
    public AbstractContainerMenu getMenu(int syncId, Inventory playerInventory) {
        return new ChestMenu(MenuType.GENERIC_3x3, syncId, playerInventory, this, 1);
    }
}