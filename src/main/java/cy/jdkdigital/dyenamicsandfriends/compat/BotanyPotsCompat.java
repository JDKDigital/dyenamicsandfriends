package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.botanypots.DyenamicsBlockEntityBotanyPot;
import cy.jdkdigital.dyenamicsandfriends.common.block.botanypots.DyenamicsBotanyPot;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.darkhax.botanypots.block.BotanyPotRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class BotanyPotsCompat
{
    private static final Map<DyenamicDyeColor, Map<String, RegistryObject<? extends Block>>> BOTANY_POTS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "botanypots_" + color.getSerializedName();
        final BlockBehaviour.Properties properties = Block.Properties.of().mapColor(color.getMapColor()).strength(1.25F, 4.2F).noOcclusion().lightLevel(state -> color.getLightValue());

        Map<String, RegistryObject<? extends Block>> blocks = new HashMap<>();
        BOTANY_POTS.put(color, blocks);

        blocks.put("terracotta",
                DyenamicRegistry.registerBlock(prefix + "_terracotta_botany_pot", () -> new DyenamicsBotanyPot(properties, false,
                DyenamicRegistry.registerBlockEntity(prefix + "_terracotta_botany_pot", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsBlockEntityBotanyPot((DyenamicsBotanyPot) BOTANY_POTS.get(color).get("terracotta").get(), pos, state), BOTANY_POTS.get(color).get("terracotta").get()))),
                () -> new BlockItem(BOTANY_POTS.get(color).get("terracotta").get(), new Item.Properties())));
        blocks.put("terracotta_hopper",
                DyenamicRegistry.registerBlock(prefix + "_terracotta_hopper_botany_pot", () -> new DyenamicsBotanyPot(properties, false,
                DyenamicRegistry.registerBlockEntity(prefix + "_terracotta_hopper_botany_pot", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsBlockEntityBotanyPot((DyenamicsBotanyPot) BOTANY_POTS.get(color).get("terracotta_hopper").get(), pos, state), BOTANY_POTS.get(color).get("terracotta_hopper").get()))),
                () -> new BlockItem(BOTANY_POTS.get(color).get("terracotta_hopper").get(), new Item.Properties())));
        blocks.put("concrete",
                DyenamicRegistry.registerBlock(prefix + "_concrete_botany_pot", () -> new DyenamicsBotanyPot(properties, false,
                DyenamicRegistry.registerBlockEntity(prefix + "_concrete_botany_pot", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsBlockEntityBotanyPot((DyenamicsBotanyPot) BOTANY_POTS.get(color).get("concrete").get(), pos, state), BOTANY_POTS.get(color).get("concrete").get()))),
                () -> new BlockItem(BOTANY_POTS.get(color).get("concrete").get(), new Item.Properties())));
        blocks.put("concrete_hopper",
                DyenamicRegistry.registerBlock(prefix + "_concrete_hopper_botany_pot", () -> new DyenamicsBotanyPot(properties, false,
                DyenamicRegistry.registerBlockEntity(prefix + "_concrete_hopper_botany_pot", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsBlockEntityBotanyPot((DyenamicsBotanyPot) BOTANY_POTS.get(color).get("concrete_hopper").get(), pos, state), BOTANY_POTS.get(color).get("concrete_hopper").get()))),
                () -> new BlockItem(BOTANY_POTS.get(color).get("concrete_hopper").get(), new Item.Properties())));
        blocks.put("glazed_terracotta",
                DyenamicRegistry.registerBlock(prefix + "_glazed_terracotta_botany_pot", () -> new DyenamicsBotanyPot(properties, false,
                DyenamicRegistry.registerBlockEntity(prefix + "_glazed_terracotta_botany_pot", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsBlockEntityBotanyPot((DyenamicsBotanyPot) BOTANY_POTS.get(color).get("glazed_terracotta").get(), pos, state), BOTANY_POTS.get(color).get("glazed_terracotta").get()))),
                () -> new BlockItem(BOTANY_POTS.get(color).get("glazed_terracotta").get(), new Item.Properties())));
        blocks.put("glazed_terracotta_hopper",
                DyenamicRegistry.registerBlock(prefix + "_glazed_terracotta_hopper_botany_pot", () -> new DyenamicsBotanyPot(properties, false,
                DyenamicRegistry.registerBlockEntity(prefix + "_glazed_terracotta_hopper_botany_pot", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsBlockEntityBotanyPot((DyenamicsBotanyPot) BOTANY_POTS.get(color).get("glazed_terracotta_hopper").get(), pos, state), BOTANY_POTS.get(color).get("glazed_terracotta_hopper").get()))),
                () -> new BlockItem(BOTANY_POTS.get(color).get("glazed_terracotta_hopper").get(), new Item.Properties())));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("botanypots:creative_tab"));
        if (event.getTabKey().equals(key)) {
            BOTANY_POTS.forEach((dyenamicDyeColor, map) -> {
                map.forEach((c, registryObject) -> event.accept(registryObject));
            });
        }
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            BOTANY_POTS.values().forEach(map -> {
                map.values().forEach(registryObject -> {
                    if (registryObject.get() instanceof DyenamicsBotanyPot botanyPot) {
                        event.registerBlockEntityRenderer(botanyPot.getBlockEntitySupplier().get(), BotanyPotRenderer::new);
                    }
                });
            });
        }
    }
}
