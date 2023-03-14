package cy.jdkdigital.dyenamicsandfriends.client.render;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.illusivesoulworks.comforts.client.renderer.HammockBlockEntityRenderer;
import com.illusivesoulworks.comforts.common.block.entity.BaseComfortsBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsHammockBlockEntity;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;

public class DyenamicsHammockBlockRenderer extends HammockBlockEntityRenderer
{
    public static final Material[] HAMMOCK_TEXTURES = Arrays.stream(DyenamicDyeColor.values()).sorted(Comparator.comparingInt(DyenamicDyeColor::getId)).map((dyeColor) -> {
        return new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(DyenamicsAndFriends.MODID, "entity/comforts/hammock/" + dyeColor.getSerializedName()));
    }).toArray(Material[]::new);

    public DyenamicsHammockBlockRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    public void render(BaseComfortsBlockEntity blockEntity, float partialTicks, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        if (blockEntity instanceof DyenamicsHammockBlockEntity dyenamicsHammockBlockEntity) {
            Material material = HAMMOCK_TEXTURES[dyenamicsHammockBlockEntity.getDyenamicColor().getId()];
            Level level = blockEntity.getLevel();
            if (level != null) {
                BlockState blockstate = blockEntity.getBlockState();
                DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> icallbackwrapper = DoubleBlockCombiner.combineWithNeigbour(BlockEntityType.BED, BedBlock::getBlockType, BedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, level, blockEntity.getBlockPos(), (p_228846_0_, p_228846_1_) -> {
                    return false;
                });
                int i = ((Int2IntFunction) icallbackwrapper.apply(new BrightnessCombiner<>())).get(combinedLightIn);
                this.renderPiece(matrixStack, buffer, blockstate.getValue(BedBlock.PART) == BedPart.HEAD, blockstate.getValue(BedBlock.FACING), material, i, combinedOverlayIn, false);
            } else {
                this.renderPiece(matrixStack, buffer, true, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, false);
                this.renderPiece(matrixStack, buffer, false, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, true);
            }
        }
    }
}
