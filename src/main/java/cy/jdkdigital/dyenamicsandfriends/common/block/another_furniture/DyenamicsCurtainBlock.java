package cy.jdkdigital.dyenamicsandfriends.common.block.another_furniture;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.starfish_studios.another_furniture.block.CurtainBlock;

public class DyenamicsCurtainBlock extends CurtainBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsCurtainBlock(DyenamicDyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }
}
