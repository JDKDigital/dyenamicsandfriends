package cy.jdkdigital.dyenamicsandfriends.event;

import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = DyenamicsAndFriends.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup
{
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        DyenamicRegistry.clientRegister();
        DyenamicRegistry.registerBlockRendering(event);
    }
    
    @SubscribeEvent
    public static void registerBlockColorHandlers(final RegisterColorHandlersEvent.Block event) {
        DyenamicRegistry.registerBlockColorHandlers(event);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        DyenamicRegistry.registerBlockEntityRenderers(event);
    }

//    @SubscribeEvent
//    public static void textureStitch(TextureStitchEvent.Pre event) {
//        DyenamicRegistry.onTextureStitch(event);
//    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        DyenamicRegistry.onModelBake(event);
    }
}
