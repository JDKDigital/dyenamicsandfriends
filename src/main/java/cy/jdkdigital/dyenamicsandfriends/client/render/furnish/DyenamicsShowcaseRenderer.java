package cy.jdkdigital.dyenamicsandfriends.client.render.furnish;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsShowcaseBlockEntity;
import io.github.wouink.furnish.block.Showcase;
import io.github.wouink.furnish.block.blockentity.ShowcaseBlockEntity;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DyenamicsShowcaseRenderer implements BlockEntityRenderer<DyenamicsShowcaseBlockEntity>
{
    private final ItemRenderer itemRenderer;

    public DyenamicsShowcaseRenderer(BlockEntityRendererProvider.Context ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        this.itemRenderer = minecraft.getItemRenderer();
    }

    public void render(DyenamicsShowcaseBlockEntity showcase, float partialTicks, PoseStack ps, MultiBufferSource buffer, int light, int overlay) {
        ItemStack stack = showcase.getHeldItem();
        if (!stack.isEmpty()) {
            ps.pushPose();
            Direction dir = ((Direction) showcase.getBlockState().getValue(Showcase.FACING)).getOpposite();
            boolean powered = (Boolean) showcase.getBlockState().getValue(BlockStateProperties.POWERED);
            BakedModel model = this.itemRenderer.getModel(stack, showcase.getLevel(), (LivingEntity) null, 0);
            float angle = powered ? (float) (showcase.getLevel().getGameTime() % 360L) : 0.0F;
            if (model.isGui3d()) {
                this.prepareRenderBlock(ps, dir, angle);
            } else {
                this.prepareRenderItem(ps, dir, angle);
            }

            this.itemRenderer.render(stack, ItemDisplayContext.FIXED, true, ps, buffer, light, overlay, model);
            ps.popPose();
        }

    }

    public void prepareRenderBlock(PoseStack ps, Direction dir, float angleOffset) {
        ps.translate((double) 0.5F, 0.4, (double) 0.5F);
        switch (dir) {
            case NORTH -> {
            }
            case SOUTH -> angleOffset += 180.0F;
            case WEST -> angleOffset += 90.0F;
            default -> angleOffset += 270.0F;
        }

        ps.mulPose(Axis.YP.rotationDegrees(angleOffset));
        ps.scale(0.8F, 0.8F, 0.8F);
    }

    public void prepareRenderItem(PoseStack ps, Direction dir, float angleOffset) {
        dir = dir.getOpposite();
        ps.translate((double) 0.5F, 0.4, (double) 0.5F);
        switch (dir) {
            case NORTH -> {
            }
            case SOUTH -> angleOffset += 180.0F;
            case WEST -> angleOffset += 90.0F;
            default -> angleOffset += 270.0F;
        }

        ps.mulPose(Axis.YP.rotationDegrees(angleOffset));
        ps.mulPose(Axis.XP.rotationDegrees(10.0F));
        ps.scale(1.0F, 1.0F, 1.0F);
    }
}