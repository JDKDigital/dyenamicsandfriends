package cy.jdkdigital.dyenamicsandfriends.event;

import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DyenamicsAndFriends.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup
{
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        DyenamicRegistry.registerBlockRendering(event);
    }
    
    @SubscribeEvent
    public static void registerBlockColorHandlers(final RegisterColorHandlersEvent.Block event) {
        DyenamicRegistry.registerBlockColorHandlers(event);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(RegisterRenderers event) {
        DyenamicRegistry.registerBlockEntityRenderers(event);
    }

    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre event) {
        DyenamicRegistry.onTextureStitch(event);
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.BakingCompleted event) {
        DyenamicRegistry.onModelBake(event);
    }
}
