package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.chromacarvings.client.renderer.entity.ColoredSnowGolemRenderer;
import cy.jdkdigital.chromacarvings.common.block.ColoredCarvedPumpkinBlock;
import cy.jdkdigital.chromacarvings.common.block.ColoredEquipableCarvedPumpkinBlock;
import cy.jdkdigital.chromacarvings.common.block.ColoredPumpkinBlock;
import cy.jdkdigital.chromacarvings.common.entity.ColoredSnowGolem;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;

public class ChromaCarvingsCompat
{
    public static final Map<DyenamicDyeColor, DeferredHolder<EntityType<?>, EntityType<ColoredSnowGolem>>> SNOW_GOLEMS = new HashMap<>();

    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> PUMPKINS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> CARVED_PUMPKINS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> JACKOS = new HashMap<>();

    public static void registerEntities(DyenamicDyeColor color) {
        String prefix = "chromacarvings_" + color.getSerializedName();
        SNOW_GOLEMS.put(color, DyenamicRegistry.createEntity(prefix + "_snow_golem", EntityType.Builder.of(ColoredSnowGolem::new, MobCategory.MISC).immuneTo(Blocks.POWDER_SNOW).sized(0.7F, 1.9F).eyeHeight(1.7F).clientTrackingRange(8)));
    }

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "chromacarvings_" + color.getSerializedName();
        PUMPKINS.put(color, DyenamicRegistry.registerBlock(prefix + "_pumpkin", () -> new ColoredPumpkinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN).mapColor(state -> color.getMapColor()).lightLevel(state -> color.getLightValue())), true));
        CARVED_PUMPKINS.put(color, DyenamicRegistry.registerBlock(prefix + "_carved_pumpkin", () -> new ColoredEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARVED_PUMPKIN).mapColor(state -> color.getMapColor()).lightLevel(state -> color.getLightValue())), true));
        JACKOS.put(color, DyenamicRegistry.registerBlock(prefix + "_jack_o_lantern", () -> new ColoredCarvedPumpkinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN).mapColor(state -> color.getMapColor())), true));
    }

    public static void buildTabContents(net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.COLORED_BLOCKS)) {
            PUMPKINS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            CARVED_PUMPKINS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
            JACKOS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject.get()));
        }
    }

    public static void createEntityAttributes(EntityAttributeCreationEvent event) {
        SNOW_GOLEMS.forEach((dyenamicDyeColor, entity) -> {
            event.put(entity.get(), SnowGolem.createAttributes().build());
        });
    }

    public static class Client
    {
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            SNOW_GOLEMS.forEach((dyenamicDyeColor, entity) -> {
                event.registerEntityRenderer(entity.get(), ColoredSnowGolemRenderer::new);
            });
        }
    }
}
