package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.darkhax.botanypots.common.impl.block.BotanyPotBlock;
import net.darkhax.botanypots.common.impl.block.PotType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;

public class BotanyPotsCompat
{
    public static final Map<DyenamicDyeColor, Map<String, DeferredHolder<Block, ? extends Block>>> BOTANY_POTS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "botanypots_" + color.getSerializedName();
        final BlockBehaviour.Properties properties = Block.Properties.of().mapColor(color.getMapColor()).strength(1.25F, 4.2F).noOcclusion().lightLevel(state -> color.getLightValue());

        Map<String, DeferredHolder<Block, ? extends Block>> blocks = new HashMap<>();
        BOTANY_POTS.put(color, blocks);

        blocks.put("terracotta",
                DyenamicRegistry.registerBlock(
                        prefix + "_terracotta_botany_pot",
                        () -> new BotanyPotBlock(color.getMapColor(), PotType.BASIC),
                        () -> new BlockItem(BOTANY_POTS.get(color).get("terracotta").get(), new Item.Properties())
                )
        );
        blocks.put("terracotta_hopper",
                DyenamicRegistry.registerBlock(
                        prefix + "_terracotta_hopper_botany_pot",
                        () -> new BotanyPotBlock(properties, PotType.HOPPER),
                        () -> new BlockItem(BOTANY_POTS.get(color).get("terracotta_hopper").get(), new Item.Properties())
                )
        );
        blocks.put("terracotta_waxed",
                DyenamicRegistry.registerBlock(
                        prefix + "_terracotta_waxed_botany_pot",
                        () -> new BotanyPotBlock(properties, PotType.WAXED),
                        () -> new BlockItem(BOTANY_POTS.get(color).get("terracotta_waxed").get(), new Item.Properties())
                )
        );
        blocks.put("concrete",
                DyenamicRegistry.registerBlock(
                        prefix + "_concrete_botany_pot",
                        () -> new BotanyPotBlock(properties, PotType.BASIC),
                        () -> new BlockItem(BOTANY_POTS.get(color).get("concrete").get(), new Item.Properties())
                )
        );
        blocks.put("concrete_hopper",
                DyenamicRegistry.registerBlock(
                        prefix + "_concrete_hopper_botany_pot",
                        () -> new BotanyPotBlock(properties, PotType.HOPPER),
                        () -> new BlockItem(BOTANY_POTS.get(color).get("concrete_hopper").get(), new Item.Properties())
                )
        );
        blocks.put("concrete_waxed",
                DyenamicRegistry.registerBlock(
                        prefix + "_concrete_waxed_botany_pot",
                        () -> new BotanyPotBlock(properties, PotType.WAXED),
                        () -> new BlockItem(BOTANY_POTS.get(color).get("concrete_waxed").get(), new Item.Properties())
                )
        );
        blocks.put("glazed_terracotta",
                DyenamicRegistry.registerBlock(
                        prefix + "_glazed_terracotta_botany_pot",
                        () -> new BotanyPotBlock(properties, PotType.BASIC),
                        () -> new BlockItem(BOTANY_POTS.get(color).get("glazed_terracotta").get(), new Item.Properties())
                )
        );
        blocks.put("glazed_terracotta_hopper",
                DyenamicRegistry.registerBlock(
                        prefix + "_glazed_terracotta_hopper_botany_pot",
                        () -> new BotanyPotBlock(properties, PotType.HOPPER),
                        () -> new BlockItem(BOTANY_POTS.get(color).get("glazed_terracotta_hopper").get(), new Item.Properties())
                )
        );
        blocks.put("glazed_terracotta_waxed",
                DyenamicRegistry.registerBlock(
                        prefix + "_glazed_terracotta_waxed_botany_pot",
                        () -> new BotanyPotBlock(properties, PotType.WAXED),
                        () -> new BlockItem(BOTANY_POTS.get(color).get("glazed_terracotta_waxed").get(), new Item.Properties())
                )
        );
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.parse("botanypots:tab"));
        if (event.getTabKey().equals(key)) {
            BOTANY_POTS.forEach((dyenamicDyeColor, map) -> {
                map.forEach((c, registryObject) -> event.accept(registryObject.get()));
            });
        }
    }

    public static void addBlocks(BlockEntityTypeAddBlocksEvent event) {
        var BLOCK_ENTITY = BuiltInRegistries.BLOCK_ENTITY_TYPE.get(ResourceLocation.parse("botanypots:botany_pot"));
        BOTANY_POTS.forEach((dyenamicDyeColor, map) -> {
            event.modify(BLOCK_ENTITY, map.values().stream().map(DeferredHolder::get).toList().toArray(new Block[0]));
        });
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        }
    }
}
