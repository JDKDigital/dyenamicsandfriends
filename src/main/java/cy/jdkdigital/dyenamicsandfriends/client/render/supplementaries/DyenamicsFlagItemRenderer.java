package cy.jdkdigital.dyenamicsandfriends.client.render.supplementaries;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsFlagBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.item.supplementaries.DyenamicsFlagItem;
import cy.jdkdigital.dyenamicsandfriends.compat.SupplementariesCompat;
import net.mehvahdjukaar.supplementaries.client.renderers.RotHlpr;
import net.mehvahdjukaar.supplementaries.client.renderers.items.FlagItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.List;

public class DyenamicsFlagItemRenderer extends FlagItemRenderer
{
    private static final BlockState state = SupplementariesCompat.FLAGS.get(DyenamicDyeColor.AQUAMARINE).get().defaultBlockState();

    public DyenamicsFlagItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        matrixStackIn.pushPose();
        matrixStackIn.translate(-0.71875, 0, 0);

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
        CompoundTag com = stack.getTagElement("BlockEntityTag");
        ListTag listnbt = null;
        if (com != null && com.contains("Patterns")) {
            listnbt = com.getList("Patterns", 10);
        }
        List<Pair<BannerPattern, DyenamicDyeColor>> patterns = DyenamicsFlagBlockEntity.createPatterns(((DyenamicsFlagItem) stack.getItem()).getDyenamicsColor(), listnbt);
        matrixStackIn.translate(0.5 + 0.0625, 0, 0.5);
        matrixStackIn.mulPose(RotHlpr.Y90);
        DyenamicsFlagBlockRenderer.renderDyenamicPatterns(matrixStackIn, bufferIn, patterns, combinedLightIn);

        matrixStackIn.popPose();

    }
}
