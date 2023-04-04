package cy.jdkdigital.dyenamicsandfriends.client.render;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;

public class DyenamicsBannerRenderer
{
    /**
     *
     * @param pBanner @param banner if {@code true}, uses banner material; otherwise if {@code false} uses shield
     * material
     */
    public static void renderPatterns(PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, ModelPart pFlagPart, Material pFlagMaterial, boolean pBanner, List<Pair<BannerPattern, DyenamicDyeColor>> pPatterns, boolean pGlint) {
        pFlagPart.render(pPoseStack, pFlagMaterial.buffer(pBufferSource, RenderType::entitySolid, pGlint), pPackedLight, pPackedOverlay);

        for(int i = 0; i < 17 && i < pPatterns.size(); ++i) {
            Pair<BannerPattern, DyenamicDyeColor> pair = pPatterns.get(i);
            float[] afloat = pair.getSecond().getColorComponentValues();
            BannerPattern bannerpattern = pair.getFirst();
            Material material = pBanner ? Sheets.getBannerMaterial(bannerpattern) : Sheets.getShieldMaterial(bannerpattern);
            pFlagPart.render(pPoseStack, material.buffer(pBufferSource, RenderType::entityNoOutline), pPackedLight, pPackedOverlay, afloat[0], afloat[1], afloat[2], 1.0F);
        }
    }
}
