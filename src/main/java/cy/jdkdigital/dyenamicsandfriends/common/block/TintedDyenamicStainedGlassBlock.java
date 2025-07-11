package cy.jdkdigital.dyenamicsandfriends.common.block;

import cy.jdkdigital.dyenamics.common.blocks.DyenamicStainedGlassBlock;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class TintedDyenamicStainedGlassBlock extends DyenamicStainedGlassBlock
{
    public TintedDyenamicStainedGlassBlock(DyenamicDyeColor colorIn, Properties properties) {
        super(colorIn, properties);
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return level.getMaxLightLevel();
    }
}