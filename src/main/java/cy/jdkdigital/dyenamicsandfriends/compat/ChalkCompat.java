package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.mojang.math.Vector3f;
import cy.jdkdigital.dyenamicsandfriends.common.block.chalk.DyenamicsChalkMarkBlock;
import cy.jdkdigital.dyenamicsandfriends.common.item.chalk.DyenamicsChalkBoxItem;
import cy.jdkdigital.dyenamicsandfriends.common.item.chalk.DyenamicsChalkItem;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import io.github.mortuusars.chalk.Chalk;
import io.github.mortuusars.chalk.block.ChalkMarkBlock;
import io.github.mortuusars.chalk.core.Mark;
import io.github.mortuusars.chalk.core.MarkSymbol;
import io.github.mortuusars.chalk.core.SymbolOrientation;
import io.github.mortuusars.chalk.items.ChalkBoxItem;
import io.github.mortuusars.chalk.render.ChalkMarkBakedModel;
import io.github.mortuusars.chalk.utils.ParticleUtils;
import io.github.mortuusars.chalk.utils.PositionUtils;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ChalkCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CHALK_MARK_BLOCKS = new HashMap<>();
    public static final Map<DyenamicDyeColor, RegistryObject<? extends Item>> CHALKS = new HashMap<>();
    public static RegistryObject<? extends Item> CHALK_BOX = null;

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "chalk_" + color.getSerializedName();

        CHALK_MARK_BLOCKS.put(color, DyenamicRegistry.registerBlock(prefix + "_chalk_mark", () -> new DyenamicsChalkMarkBlock(color, BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT, color.getMapColor()).instabreak().noOcclusion().noCollission().sound(SoundType.GRAVEL).lightLevel(state -> color.getLightValue())), null, false));
    }

    public static void registerItems(DyenamicDyeColor color) {
        String prefix = "chalk_" + color.getSerializedName();
        CHALKS.put(color, DyenamicRegistry.registerItem(prefix + "_chalk", () -> new DyenamicsChalkItem(color, new Item.Properties())));

        if (color.equals(DyenamicDyeColor.PEACH)) {
            CHALK_BOX = DyenamicRegistry.registerItem("chalk_box", () -> new DyenamicsChalkBoxItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)));
        }
    }

    public static class Client
    {
        public static void registerBlockRendering() {
            CHALK_MARK_BLOCKS.values().forEach(registryObject -> {
                ItemBlockRenderTypes.setRenderLayer(registryObject.get(), RenderType.cutout());
            });

            var chalkBox = (DyenamicsChalkBoxItem)CHALK_BOX.get();
            ItemProperties.register(chalkBox, ChalkBoxItem.SELECTED_PROPERTY, (stack, level, entity, damage) -> {
                return chalkBox.getSelectedChalkColor(stack);
            });
        }

        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            event.register(
                CHALK_MARK_BLOCK_COLOR,
                CHALK_MARK_BLOCKS.values().stream().map(RegistryObject::get).toArray(Block[]::new)
            );
        }

        public static void bakeModel(ModelEvent.BakingCompleted event) {
            CHALK_MARK_BLOCKS.forEach((color, block) -> {
                for (BlockState blockState : block.get().getStateDefinition().getPossibleStates()) {
                    ModelResourceLocation variantMRL = BlockModelShaper.stateToModelLocation(blockState);
                    BakedModel existingModel = event.getModels().get(variantMRL);
                    if (existingModel == null) {
                        Chalk.LOGGER.warn("Did not find the expected vanilla baked model(s) for " + block + " in registry");
                    } else if (existingModel instanceof ChalkMarkBakedModel) {
                        Chalk.LOGGER.warn("Tried to replace " + block + " twice");
                    } else {
                        ChalkMarkBakedModel customModel = new ChalkMarkBakedModel(existingModel);
                        event.getModels().put(variantMRL, customModel);
                    }
                }
            });
        }

        public static final BlockColor CHALK_MARK_BLOCK_COLOR = (blockState, blockAndTintGetter, blockPos, index) -> {
            if (!(blockState.getBlock() instanceof DyenamicsChalkMarkBlock)) {
                return 16777215;
            } else {
                DyenamicDyeColor blockColor = ((DyenamicsChalkMarkBlock)blockState.getBlock()).getDyenamicColor();
                return blockColor.getColorValue();
            }
        };
    }

    public static Mark convertMark(Mark mark, DyenamicDyeColor color, boolean glowing) {
        return new DyenamicsMark(mark.facing, color.getColorValue(), mark.symbol, mark.orientation, glowing);
    }

    public static void spawnColorDustParticles(DyenamicDyeColor color, Level level, BlockPos pos, Direction face) {
        int colorValue = color.getColorValue();
        float R = (float)((colorValue & 16711680) >> 16);
        float G = (float)((colorValue & '\uff00') >> 8);
        float B = (float)(colorValue & 255);
        ParticleUtils.spawnParticle(level, new DustParticleOptions(new Vector3f(R / 255.0F, G / 255.0F, B / 255.0F), 2.0F), PositionUtils.blockCenterOffsetToFace(pos, face, 0.25F), 1);
    }

    static class DyenamicsMark extends Mark {
        public DyenamicsMark(Direction facing, int color, MarkSymbol symbol, SymbolOrientation orientation, boolean glowing) {
            super(facing, color, symbol, orientation, glowing);
        }

        @Override
        public BlockState createBlockState(ItemStack drawingItem) {
            DyenamicDyeColor color = null;
            if (drawingItem.getItem() instanceof DyenamicsChalkBoxItem chalkBox) {
                ItemStack selectedChalk = chalkBox.getSelectedChalkItem(drawingItem);
                color = selectedChalk.getItem() instanceof DyenamicsChalkItem chalkItem ? chalkItem.getDyenamicsColor() : null;
            } else if (drawingItem.getItem() instanceof DyenamicsChalkItem chalkItem) {
                color = chalkItem.getDyenamicsColor();
            }
            if (color != null) {
                return ChalkCompat.CHALK_MARK_BLOCKS.get(color).get().defaultBlockState()
                    .setValue(ChalkMarkBlock.FACING, facing)
                    .setValue(ChalkMarkBlock.SYMBOL, symbol)
                    .setValue(ChalkMarkBlock.ORIENTATION, orientation)
                    .setValue(ChalkMarkBlock.GLOWING, glowing);
            }
            return super.createBlockState(drawingItem);
        }
    }

}
