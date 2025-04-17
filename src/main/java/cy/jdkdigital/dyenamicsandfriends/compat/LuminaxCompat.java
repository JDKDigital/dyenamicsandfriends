package cy.jdkdigital.dyenamicsandfriends.compat;

import com.satherov.luminax.content.LuminaxRegistry;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LuminaxCompat
{
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> BLOCKS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> STAIRS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> SLABS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> WALLS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> PRESSURE_PLATES = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> BUTTONS = new HashMap<>();

    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> DIM_BLOCKS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> DIM_STAIRS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> DIM_SLABS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> DIM_WALLS = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> DIM_PRESSURE_PLATES = new HashMap<>();
    public static Map<DyenamicDyeColor, DeferredHolder<Block, ? extends Block>> DIM_BUTTONS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "luminax_" + color.getSerializedName();
        BLOCKS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).lightLevel((state) -> 15)), true));
        STAIRS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_stairs", () -> new StairBlock(BLOCKS.get(color).get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS).lightLevel((state) -> 15)), true));
        SLABS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB).lightLevel((state) -> 15)), true));
        WALLS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).lightLevel((state) -> 15)), true));
        PRESSURE_PLATES.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_pressure_plate", () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE).lightLevel((state) -> 15)), true));
        BUTTONS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_button", () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON).lightLevel((state) -> 15)), true));

        prefix = "luminax_dim_" + color.getSerializedName();
        DIM_BLOCKS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)), true));
        DIM_STAIRS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_stairs", () -> new StairBlock(BLOCKS.get(color).get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS)), true));
        DIM_SLABS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB)), true));
        DIM_WALLS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL)), true));
        DIM_PRESSURE_PLATES.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_pressure_plate", () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE)), true));
        DIM_BUTTONS.put(color, DyenamicRegistry.registerBlock(prefix + "_luminax_button", () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON)), true));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(LuminaxRegistry.CREATIVE_TAB.getKey())) {
            Arrays.stream(DyenamicDyeColor.dyenamicValues()).forEach(dyenamicDyeColor -> {
                event.accept(BLOCKS.get(dyenamicDyeColor).get());
                event.accept(STAIRS.get(dyenamicDyeColor).get());
                event.accept(SLABS.get(dyenamicDyeColor).get());
                event.accept(WALLS.get(dyenamicDyeColor).get());
                event.accept(PRESSURE_PLATES.get(dyenamicDyeColor).get());
                event.accept(BUTTONS.get(dyenamicDyeColor).get());
                event.accept(DIM_BLOCKS.get(dyenamicDyeColor).get());
                event.accept(DIM_STAIRS.get(dyenamicDyeColor).get());
                event.accept(DIM_SLABS.get(dyenamicDyeColor).get());
                event.accept(DIM_WALLS.get(dyenamicDyeColor).get());
                event.accept(DIM_PRESSURE_PLATES.get(dyenamicDyeColor).get());
                event.accept(DIM_BUTTONS.get(dyenamicDyeColor).get());
            });
        }
    }

    public static void postRegister() {
        Arrays.stream(DyenamicDyeColor.dyenamicValues()).forEach(dyenamicDyeColor -> {
            for (String name: new String[]{"block", "stairs", "slab", "wall", "pressure_plate", "button"}) {
                BuiltInRegistries.BLOCK.addAlias(ResourceLocation.parse("luminax:" + dyenamicDyeColor.getSerializedName() + "_" + name), ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "luminax_" + dyenamicDyeColor.getSerializedName() + "_luminax_" + name));
                BuiltInRegistries.BLOCK.addAlias(ResourceLocation.parse("luminax:dim_" + dyenamicDyeColor.getSerializedName() + "_" + name), ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "luminax_dim_" + dyenamicDyeColor.getSerializedName() + "_luminax_" + name));
                BuiltInRegistries.ITEM.addAlias(ResourceLocation.parse("luminax:" + dyenamicDyeColor.getSerializedName() + "_" + name), ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "luminax_" + dyenamicDyeColor.getSerializedName() + "_luminax_" + name));
                BuiltInRegistries.ITEM.addAlias(ResourceLocation.parse("luminax:dim_" + dyenamicDyeColor.getSerializedName() + "_" + name), ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "luminax_dim_" + dyenamicDyeColor.getSerializedName() + "_luminax_" + name));
            }
        });
    }
}
