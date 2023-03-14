package cy.jdkdigital.dyenamicsandfriends.client.render.sleep_tight;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.sleep_tight.DyenamicsHammockBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.compat.SleepTightCompat;
import net.mehvahdjukaar.sleep_tight.SleepTightClient;
import net.mehvahdjukaar.sleep_tight.client.renderers.HammockBlockTileRenderer;
import net.mehvahdjukaar.sleep_tight.common.HammockPart;
import net.mehvahdjukaar.sleep_tight.common.blocks.HammockBlock;
import net.mehvahdjukaar.sleep_tight.common.tiles.HammockTile;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.Comparator;

public class DyenamicsHammockBlockTileRenderer extends HammockBlockTileRenderer
{
    public static final ResourceLocation BED_SHEET = new ResourceLocation("textures/atlas/beds.png");
    public static final Material[] HAMMOCK_TEXTURES = Arrays.stream(DyenamicDyeColor.values()).sorted(Comparator.comparingInt(DyenamicDyeColor::getId)).map((dyeColor) -> {
        return new Material(BED_SHEET, new ResourceLocation(DyenamicsAndFriends.MODID, "entity/sleep_tight/hammocks/" + dyeColor.getSerializedName()));
    }).toArray(Material[]::new);

    private final ModelPart model;
    private final ModelPart ropeF;
    private final ModelPart ropeB;

    public DyenamicsHammockBlockTileRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.model = context.bakeLayer(SleepTightClient.HAMMOCK);
        this.ropeF = this.model.getChild("rope_f");
        this.ropeB = this.model.getChild("rope_b");
    }

    @Override
    public void render(HammockTile blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity instanceof DyenamicsHammockBlockEntity hammockBlockEntity) {
            BlockState state = hammockBlockEntity.getBlockState();
            HammockPart value = state.getValue(HammockBlock.PART);
            boolean onRope = value.isOnFence();
            double dy = value.getPivotOffset();
            float zOffset;
            if (onRope) {
                zOffset = -0.5F;
                this.ropeB.xRot = -2.6179938F;
                this.ropeF.xRot = -0.5235988F;
            } else {
                zOffset = 0.0F;
                this.ropeB.xRot = -2.268928F;
                this.ropeF.xRot = -0.8726647F;
            }

            poseStack.pushPose();
            poseStack.translate(0.5D, 0.5D + dy, 0.5D);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(-state.getValue(HammockBlock.FACING).toYRot()));
            float yaw = hammockBlockEntity.getRoll(partialTick);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F + yaw));
            poseStack.translate(0.0D, 0.5D + dy, (double) zOffset);
            Material material = HAMMOCK_TEXTURES[hammockBlockEntity.getDyenamicColor().getId()];
            VertexConsumer vertexConsumer = material.buffer(bufferSource, RenderType::entityCutoutNoCull);
            this.model.render(poseStack, vertexConsumer, packedLight, packedOverlay);
            poseStack.popPose();
        }
    }
}
