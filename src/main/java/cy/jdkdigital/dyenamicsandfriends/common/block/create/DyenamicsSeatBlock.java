package cy.jdkdigital.dyenamicsandfriends.common.block.create;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;

// Create compat
public class DyenamicsSeatBlock extends SeatBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsSeatBlock(Properties properties, DyenamicDyeColor color) {
        super(properties, color.getVanillaColor(), true);
        this.color = color;
    }
}
