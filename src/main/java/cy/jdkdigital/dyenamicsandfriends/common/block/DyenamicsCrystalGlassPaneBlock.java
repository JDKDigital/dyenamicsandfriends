package cy.jdkdigital.dyenamicsandfriends.common.block;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import galena.oreganized.content.block.CrystalGlassPaneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class DyenamicsCrystalGlassPaneBlock extends CrystalGlassPaneBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsCrystalGlassPaneBlock(DyenamicDyeColor color, Properties properties) {
        super(color.getVanillaColor(), properties);
        this.color = color;
    }

    @Override
    public float[] getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        return this.color.getColorComponentValues();
    }
}
