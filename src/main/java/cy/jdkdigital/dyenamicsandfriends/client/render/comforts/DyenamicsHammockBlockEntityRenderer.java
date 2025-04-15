package cy.jdkdigital.dyenamicsandfriends.client.render.comforts;

import com.illusivesoulworks.comforts.client.renderer.BaseComfortsBlockEntityRenderer;
import com.illusivesoulworks.comforts.common.block.entity.BaseComfortsBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsHammockBlockEntity;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
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

public class DyenamicsHammockBlockEntityRenderer extends BaseComfortsBlockEntityRenderer<DyenamicsHammockBlockEntity>
{
    private static final String BOARD = "board";

    public static final Material[] HAMMOCK_TEXTURES = Arrays.stream(DyenamicDyeColor.values()).sorted(Comparator.comparingInt(DyenamicDyeColor::getId)).map((dyeColor) -> {
        return new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "entity/comforts/hammock/" + dyeColor.getSerializedName()));
    }).toArray(Material[]::new);

    public DyenamicsHammockBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx, "hammock", BaseComfortsBlockEntityRenderer.HAMMOCK_HEAD, BaseComfortsBlockEntityRenderer.HAMMOCK_FOOT);
    }

    public static LayerDefinition createHeadLayer() {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        var1.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 1.0F, 2.0F, 14.0F, 15.0F, 1.0F), PartPose.ZERO);
        var1.addOrReplaceChild("board", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.0F, 2.0F, 16.0F, 1.0F, 1.0F), PartPose.ZERO);
        return LayerDefinition.create(var0, 64, 64);
    }

    public static LayerDefinition createFootLayer() {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        var1.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 16).addBox(1.0F, 0.0F, 2.0F, 14.0F, 15.0F, 1.0F), PartPose.ZERO);
        var1.addOrReplaceChild("board", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 15.0F, 2.0F, 16.0F, 1.0F, 1.0F), PartPose.ZERO);
        return LayerDefinition.create(var0, 64, 64);
    }

    public void render(BaseComfortsBlockEntity blockEntity, float partialTicks, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        if (blockEntity instanceof DyenamicsHammockBlockEntity dyenamicsHammockBlockEntity) {
            Material material = HAMMOCK_TEXTURES[dyenamicsHammockBlockEntity.getDyenamicColor().getId()];
            Level level = blockEntity.getLevel();
            if (level != null) {
                BlockState blockstate = blockEntity.getBlockState();
                DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> icallbackwrapper = DoubleBlockCombiner.combineWithNeigbour(BlockEntityType.BED, BedBlock::getBlockType, BedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, level, blockEntity.getBlockPos(), (p_228846_0_, p_228846_1_) -> false);
                int i = ((Int2IntFunction)icallbackwrapper.apply(new BrightnessCombiner())).get(combinedLightIn);
                this.renderPiece(matrixStack, buffer, blockstate.getValue(BedBlock.PART) == BedPart.HEAD, (Direction)blockstate.getValue(BedBlock.FACING), material, i, combinedOverlayIn, false);
            } else {
                this.renderPiece(matrixStack, buffer, true, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, false);
                this.renderPiece(matrixStack, buffer, false, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, true);
            }
        }
    }
}
