package cy.jdkdigital.dyenamicsandfriends.common.block.create;

import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;

public class DyenamicsSeatBlock extends SeatBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsSeatBlock(Properties properties, DyenamicDyeColor color) {
        super(properties, color.getAnalogue());
        this.color = color;
    }
}
