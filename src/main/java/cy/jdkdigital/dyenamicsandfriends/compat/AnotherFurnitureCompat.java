package cy.jdkdigital.dyenamicsandfriends.compat;

import com.starfish_studios.another_furniture.block.*;
import com.starfish_studios.another_furniture.block.properties.ModBlockStateProperties;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class AnotherFurnitureCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CURTAINS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> LAMPS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> SOFAS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "another_furniture_" + color.getSerializedName();
        DyenamicRegistry.registerBlock(prefix + "_lamp", () -> new LampBlock(DyeColor.WHITE, BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.METAL).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? (blockState.getValue(ModBlockStateProperties.LEVEL_1_3) * 5) : color.getLightValue())), true);
        DyenamicRegistry.registerBlock(prefix + "_curtain", () -> new CurtainBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(0.1F).sound(SoundType.WOOL).noOcclusion().lightLevel(state -> color.getLightValue())), true);
        DyenamicRegistry.registerBlock(prefix + "_sofa", () -> new SofaBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(1.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> color.getLightValue())), true);
        DyenamicRegistry.registerBlock(prefix + "_stool", () -> new StoolBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(1.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> color.getLightValue())), true);
        DyenamicRegistry.registerBlock(prefix + "_tall_stool", () -> new TallStoolBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(1.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> color.getLightValue())), true);
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("another_furniture:another_furniture"));
        if (event.getTabKey().equals(key)) {
            CURTAINS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
            LAMPS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
            SOFAS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
        }
    }
}
