package cy.jdkdigital.dyenamicsandfriends.common.block;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.simibubi.create.content.contraptions.components.actors.SeatBlock;
import net.minecraft.world.item.DyeColor;

// Create compat
public class DyenamicsSeatBlock extends SeatBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsSeatBlock(Properties properties, DyenamicDyeColor color) {
        super(properties, DyeColor.WHITE, true);
        this.color = color;
    }
}
