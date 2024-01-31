package cy.jdkdigital.dyenamicsandfriends.common.block.furnish;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import io.github.wouink.furnish.block.CarpetOnStairs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsCarpetOnStairs extends CarpetOnStairs
{
    private final DyenamicDyeColor color;

    public DyenamicsCarpetOnStairs(Properties p, DyenamicDyeColor color) {
        super(p, null);
        this.color = color;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(DyenamicRegistry.getDyenamicsBlock(color, "carpet"));
    }
}
