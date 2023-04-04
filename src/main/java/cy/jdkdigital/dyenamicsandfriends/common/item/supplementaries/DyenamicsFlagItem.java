package cy.jdkdigital.dyenamicsandfriends.common.item.supplementaries;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.client.render.supplementaries.DyenamicsFlagItemRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsFlagBlock;
import net.mehvahdjukaar.selene.items.WoodBasedBlockItem;
import net.mehvahdjukaar.supplementaries.setup.ClientRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class DyenamicsFlagItem extends WoodBasedBlockItem
{
    public DyenamicsFlagItem(Block block, Properties properties) {
        super(block, properties, 300);
    }

    public DyenamicDyeColor getDyenamicsColor() {
        return ((DyenamicsFlagBlock)this.getBlock()).getDyenamicsColor();
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        ClientRegistry.registerISTER(consumer, DyenamicsFlagItemRenderer::new);
    }
}
