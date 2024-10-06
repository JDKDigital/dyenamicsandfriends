package cy.jdkdigital.dyenamicsandfriends.compat;

import appeng.api.ids.AECreativeTabIds;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import appeng.api.parts.PartModels;
import appeng.items.parts.ColoredPartItem;
import appeng.items.parts.PartModelsHelper;
import appeng.parts.AEBasePart;
import appeng.parts.networking.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import cy.jdkdigital.dyenamics.core.init.BlockInit;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.item.ae2.DyenamicsColorApplicatorItem;
import cy.jdkdigital.dyenamicsandfriends.common.item.ae2.DyenamicsColoredPartItem;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;
import java.util.function.Function;

public class Ae2Compat
{
    public static MethodHandle setPartItemHandle;
    static RegistryObject<? extends Item> COLOR_APPLICATOR;
    public static Map<DyenamicDyeColor, RegistryObject<ColoredPartItem<SmartCablePart>>> SMART_CABLES = new HashMap<>();
    public static Map<DyenamicDyeColor, RegistryObject<ColoredPartItem<CoveredCablePart>>> COVERED_CABLES = new HashMap<>();
    public static Map<DyenamicDyeColor, RegistryObject<ColoredPartItem<GlassCablePart>>> GLASS_CABLES = new HashMap<>();
    public static Map<DyenamicDyeColor, RegistryObject<ColoredPartItem<CoveredDenseCablePart>>> COVERED_DENSE_CABLES = new HashMap<>();
    public static Map<DyenamicDyeColor, RegistryObject<ColoredPartItem<SmartDenseCablePart>>> SMART_DENSE_CABLES = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "ae2_" + color.getSerializedName();

        // smart cable, glass cable, dense covered cable, covered cable, dense smart, paint ball, lumen paint ball
    }


    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(AECreativeTabIds.MAIN)) {
            event.accept(COLOR_APPLICATOR);
        }
    }

    public static void postRegister() {
        COLOR_APPLICATOR = DyenamicRegistry.registerItem("color_applicator", () -> new DyenamicsColorApplicatorItem(new Item.Properties().stacksTo(1)));
        SMART_CABLES = constructColoredDefinition("smart_cable", SmartCablePart.class, SmartCablePart::new);
        COVERED_CABLES = constructColoredDefinition("covered_cable", CoveredCablePart.class, CoveredCablePart::new);
        GLASS_CABLES = constructColoredDefinition("glass_cable", GlassCablePart.class, GlassCablePart::new);
        COVERED_DENSE_CABLES = constructColoredDefinition("covered_dense_cable", CoveredDenseCablePart.class, CoveredDenseCablePart::new);
        SMART_DENSE_CABLES = constructColoredDefinition("smart_dense_cable", SmartDenseCablePart.class, SmartDenseCablePart::new);

        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandles.Lookup targetLookup = MethodHandles.privateLookupIn(AEBasePart.class, lookup);
            MethodType mt = MethodType.methodType(void.class, IPartItem.class);
            setPartItemHandle = targetLookup.findVirtual(AEBasePart.class, "setPartItem", mt);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static <T extends IPart> Map<DyenamicDyeColor, RegistryObject<ColoredPartItem<T>>> constructColoredDefinition(
            String idSuffix,
            Class<T> partClass,
            Function<ColoredPartItem<T>, T> factory) {

        PartModels.registerModels(PartModelsHelper.createModels(partClass));

        var definition = new HashMap<DyenamicDyeColor, RegistryObject<ColoredPartItem<T>>>();
        for (DyenamicDyeColor color : DyenamicDyeColor.dyenamicValues()) {
            var id = "ae2_" + color.getSerializedName() + '_' + idSuffix;

            definition.put(color, (RegistryObject<ColoredPartItem<T>>) DyenamicRegistry.registerItem(id, () -> new DyenamicsColoredPartItem(new Item.Properties(), partClass, factory, color)));
        }

        return definition;
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(BlockRecolorer::register);
    }

    public final class BlockRecolorer
    {
        private static BiMap<DyenamicDyeColor, Block> STAINED_GLASS_BY_COLOR;
        private static BiMap<DyenamicDyeColor, Block> STAINED_GLASS_PANE_BY_COLOR;
        private static BiMap<DyenamicDyeColor, Block> WOOL_BY_COLOR;
        private static BiMap<DyenamicDyeColor, Block> BANNER_BY_COLOR;
        private static BiMap<DyenamicDyeColor, Block> WALL_BANNER_BY_COLOR;
        private static BiMap<DyenamicDyeColor, Block> CARPET_BY_COLOR;
        private static BiMap<DyenamicDyeColor, Block> TERRACOTTA_BY_COLOR;
        private static BiMap<DyenamicDyeColor, Block> GLAZED_TERRACOTTA_BY_COLOR;
        private static BiMap<DyenamicDyeColor, Block> CONCRETE_BY_COLOR;
        private static List<RecolorableBlockGroup> BLOCK_GROUPS;

        public static void register() {
            STAINED_GLASS_BY_COLOR = Arrays.stream(DyenamicDyeColor.dyenamicValues()).collect(HashBiMap::create, (bm, c) -> bm.put(c, BlockInit.DYED_BLOCKS.get(c.getSerializedName()).get("stained_glass").get()), BiMap::putAll);
            STAINED_GLASS_PANE_BY_COLOR = Arrays.stream(DyenamicDyeColor.dyenamicValues()).collect(HashBiMap::create, (bm, c) -> bm.put(c, BlockInit.DYED_BLOCKS.get(c.getSerializedName()).get("stained_glass_pane").get()), BiMap::putAll);
            WOOL_BY_COLOR = Arrays.stream(DyenamicDyeColor.dyenamicValues()).collect(HashBiMap::create, (bm, c) -> bm.put(c, BlockInit.DYED_BLOCKS.get(c.getSerializedName()).get("wool").get()), BiMap::putAll);
            BANNER_BY_COLOR = Arrays.stream(DyenamicDyeColor.dyenamicValues()).collect(HashBiMap::create, (bm, c) -> bm.put(c, BlockInit.DYED_BLOCKS.get(c.getSerializedName()).get("banner").get()), BiMap::putAll);
            WALL_BANNER_BY_COLOR = Arrays.stream(DyenamicDyeColor.dyenamicValues()).collect(HashBiMap::create, (bm, c) -> bm.put(c, BlockInit.DYED_BLOCKS.get(c.getSerializedName()).get("wall_banner").get()), BiMap::putAll);
            CARPET_BY_COLOR = Arrays.stream(DyenamicDyeColor.dyenamicValues()).collect(HashBiMap::create, (bm, c) -> bm.put(c, BlockInit.DYED_BLOCKS.get(c.getSerializedName()).get("carpet").get()), BiMap::putAll);
            TERRACOTTA_BY_COLOR = Arrays.stream(DyenamicDyeColor.dyenamicValues()).collect(HashBiMap::create, (bm, c) -> bm.put(c, BlockInit.DYED_BLOCKS.get(c.getSerializedName()).get("terracotta").get()), BiMap::putAll);
            GLAZED_TERRACOTTA_BY_COLOR = Arrays.stream(DyenamicDyeColor.dyenamicValues()).collect(HashBiMap::create, (bm, c) -> bm.put(c, BlockInit.DYED_BLOCKS.get(c.getSerializedName()).get("glazed_terracotta").get()), BiMap::putAll);
            CONCRETE_BY_COLOR = Arrays.stream(DyenamicDyeColor.dyenamicValues()).collect(HashBiMap::create, (bm, c) -> bm.put(c, BlockInit.DYED_BLOCKS.get(c.getSerializedName()).get("concrete").get()), BiMap::putAll);

            BLOCK_GROUPS = ImmutableList.of(
                    new RecolorableBlockGroup(Blocks.GLASS, STAINED_GLASS_BY_COLOR),
                    new RecolorableBlockGroup(Blocks.GLASS_PANE, STAINED_GLASS_PANE_BY_COLOR),
                    new RecolorableBlockGroup(Blocks.WHITE_WOOL, WOOL_BY_COLOR),
                    new RecolorableBlockGroup(Blocks.WHITE_BANNER, BANNER_BY_COLOR),
                    new RecolorableBlockGroup(Blocks.WHITE_WALL_BANNER, WALL_BANNER_BY_COLOR),
                    new RecolorableBlockGroup(Blocks.WHITE_CARPET, CARPET_BY_COLOR),
                    new RecolorableBlockGroup(Blocks.TERRACOTTA, TERRACOTTA_BY_COLOR),
                    new RecolorableBlockGroup(null, GLAZED_TERRACOTTA_BY_COLOR),
                    new RecolorableBlockGroup(null, CONCRETE_BY_COLOR)
            );
        }

        public static Block recolor(Block block, DyenamicDyeColor newColor) {
            Objects.requireNonNull(block);

            for (RecolorableBlockGroup group : BLOCK_GROUPS) {
                if (group.uncoloredVariant == block || group.coloredVariants.containsValue(block)) {
                    var newBlock = group.coloredVariants.get(newColor);
                    if (newBlock == null) {
                        if (group.uncoloredVariant != null) {
                            newBlock = group.uncoloredVariant;
                        } else {
                            newBlock = block;
                        }
                    }
                    return newBlock;
                }
            }

            return block;
        }

        private static class RecolorableBlockGroup {
            final Block uncoloredVariant;
            final BiMap<DyenamicDyeColor, Block> coloredVariants;

            public RecolorableBlockGroup(Block uncoloredVariant, BiMap<DyenamicDyeColor, Block> coloredVariants) {
                this.uncoloredVariant = uncoloredVariant;
                this.coloredVariants = coloredVariants;
            }
        }
    }
}
