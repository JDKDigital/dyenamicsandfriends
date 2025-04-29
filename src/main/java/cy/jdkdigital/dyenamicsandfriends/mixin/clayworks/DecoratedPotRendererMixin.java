package cy.jdkdigital.dyenamicsandfriends.mixin.clayworks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.clayworks.core.other.ClayworksMaterials;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.compat.ClayworksCompat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

// https://github.com/team-abnormals/clayworks/blob/1.21.1/src/main/java/com/teamabnormals/clayworks/core/mixin/DecoratedPotRendererMixin.java
@Mixin(DecoratedPotRenderer.class)
public abstract class DecoratedPotRendererMixin
{
//    @Shadow
//    private static Material getSideMaterial(Optional<Item> item) {
//        return null;
//    }
//
//    @ModifyVariable(
//            method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
//            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/resources/model/Material;buffer(Lnet/minecraft/client/renderer/MultiBufferSource;Ljava/util/function/Function;)Lcom/mojang/blaze3d/vertex/VertexConsumer;", shift = At.Shift.AFTER)
//    )
//    private VertexConsumer render(VertexConsumer consumer, DecoratedPotBlockEntity entity, float p_273103_, PoseStack poseStack, MultiBufferSource buffer, int p_273407_, int p_273059_) {
//        if (ModList.get().isLoaded("clayworks")) {
//            DyenamicDyeColor color = ClayworksCompat.getDyeColorFromPot(entity.getBlockState().getBlock());
//            if (color != null) {
//                return ClayworksMaterials.DECORATED_POT_BASE_MATERIALS.get(color).buffer(buffer, RenderType::entitySolid);
//            }
//        }
//
//        return consumer;
//    }
//
//    @Redirect(method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/DecoratedPotRenderer;getSideMaterial(Ljava/util/Optional;)Lnet/minecraft/client/resources/model/Material;"))
//    private Material render(Optional<Item> optional, DecoratedPotBlockEntity entity) {
//        if (ModList.get().isLoaded("clayworks")) {
//            Item item = optional.orElse(Items.BRICK);
//            Material material = Sheets.getDecoratedPotMaterial(DecoratedPotPatterns.getPatternFromItem(item));
//            DyenamicDyeColor color = ClayworksCompat.getDyeColorFromPot(entity.getBlockState().getBlock());
//            if (color != null) {
//                return ClayworksMaterials.getDecoratedPotMaterial(DecoratedPotPatterns.getPatternFromItem(material == null ? Items.BRICK : item), color);
//            }
//        }
//
//        return getSideMaterial(optional);
//    }
}
