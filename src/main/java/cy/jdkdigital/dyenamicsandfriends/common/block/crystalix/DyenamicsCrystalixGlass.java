package cy.jdkdigital.dyenamicsandfriends.common.block.crystalix;

import com.satherov.crystalix.content.block.CrystalixGlass;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DyenamicsCrystalixGlass extends CrystalixGlass
{
    private final DyenamicDyeColor dyeColor;

    public DyenamicsCrystalixGlass(DyenamicDyeColor dyeColor) {
        super(dyeColor.getAnalogue());
        this.dyeColor = dyeColor;
    }

    @Override
    public @Nullable Integer getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        return dyeColor.getColorComponentValue();
    }
}
