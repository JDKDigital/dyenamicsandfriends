package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class SupplementariesCompat
{
    private static final Map<DyenamicDyeColor, Map<String, RegistryObject<? extends Block>>> BOTANY_POTS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "supplementaries_" + color.getSerializedName();

    }

    public static void registerBlockRendering() {
    }

    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    }
}
