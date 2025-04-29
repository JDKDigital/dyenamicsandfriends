package cy.jdkdigital.dyenamicsandfriends.common.block.clayworks;

import com.teamabnormals.clayworks.common.block.GlassDoorBlock;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;

public class DyenamicsGlassDoorBlock extends GlassDoorBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsGlassDoorBlock(DyenamicDyeColor color) {
        super(color.getAnalogue());
        this.color = color;
    }
}
