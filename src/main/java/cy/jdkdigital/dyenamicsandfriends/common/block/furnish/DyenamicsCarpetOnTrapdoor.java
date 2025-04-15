package cy.jdkdigital.dyenamicsandfriends.common.block.furnish;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import io.github.wouink.furnish.block.CarpetOnTrapdoor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsCarpetOnTrapdoor extends CarpetOnTrapdoor
{
    private final DyenamicDyeColor color;

    public DyenamicsCarpetOnTrapdoor(Properties p, DyenamicDyeColor color) {
        super(p, null);
        this.color = color;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(DyenamicRegistry.getDyenamicsBlock(color, "carpet"));
    }
}
