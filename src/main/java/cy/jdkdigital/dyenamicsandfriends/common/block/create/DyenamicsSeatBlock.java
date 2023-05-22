package cy.jdkdigital.dyenamicsandfriends.common.block.create;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.starfish_studios.another_furniture.block.SeatBlock;

// Create compat
public class DyenamicsSeatBlock extends SeatBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsSeatBlock(Properties properties, DyenamicDyeColor color) {
        super(properties);
        this.color = color;
    }
}
