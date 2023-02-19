package cy.jdkdigital.dyenamicsandfriends.common.block;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import io.github.wouink.furnish.block.CarpetOnTrapdoor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsCarpetOnTrapdoor extends CarpetOnTrapdoor
{
    private final DyenamicDyeColor color;

    public DyenamicsCarpetOnTrapdoor(Properties p, DyenamicDyeColor color) {
        super(p, null);
        this.color = color;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter reader, BlockPos pos, BlockState state) {
        return new ItemStack(DyenamicRegistry.getDyenamicsBlock(color, "carpet"));
    }
}
