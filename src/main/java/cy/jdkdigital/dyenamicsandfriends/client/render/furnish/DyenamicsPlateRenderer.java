package cy.jdkdigital.dyenamicsandfriends.client.render.furnish;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsPlateBlockEntity;
import io.github.wouink.furnish.block.Plate;
import io.github.wouink.furnish.block.blockentity.PlateBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DyenamicsPlateRenderer implements BlockEntityRenderer<DyenamicsPlateBlockEntity>
{
    private final ItemRenderer itemRenderer;

    public DyenamicsPlateRenderer(BlockEntityRendererProvider.Context ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        this.itemRenderer = minecraft.getItemRenderer();
    }

    public void render(DyenamicsPlateBlockEntity plate, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        ItemStack stack = plate.getHeldItem();
        if (!stack.isEmpty()) {
            ms.pushPose();
            BakedModel model = this.itemRenderer.getModel(stack, plate.getLevel(), (LivingEntity)null, 0);
            Direction dir = ((Direction)plate.getBlockState().getValue(Plate.FACING)).getOpposite();
            if (model.isGui3d()) {
                this.prepareRenderBlock(ms, dir);
            } else {
                this.prepareRenderItem(ms, dir);
            }

            this.itemRenderer.render(stack, ItemDisplayContext.FIXED, true, ms, buffer, light, overlay, model);
            ms.popPose();
        }

    }

    public void prepareRenderItem(PoseStack ms, Direction dir) {
        ms.translate((double)0.5F, 0.08, (double)0.5F);
        float angle = -dir.toYRot();
        ms.mulPose(Axis.YP.rotationDegrees(angle));
        ms.mulPose(Axis.XP.rotationDegrees(90.0F));
        ms.scale(0.6F, 0.6F, 0.6F);
    }

    public void prepareRenderBlock(PoseStack ms, Direction dir) {
        ms.translate((double)0.5F, 0.23, (double)0.5F);
        float angle = -dir.toYRot();
        ms.mulPose(Axis.YP.rotationDegrees(angle));
        ms.scale(0.8F, 0.8F, 0.8F);
    }
}