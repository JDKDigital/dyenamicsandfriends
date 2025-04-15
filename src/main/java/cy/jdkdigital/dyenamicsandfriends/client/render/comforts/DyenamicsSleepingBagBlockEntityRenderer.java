package cy.jdkdigital.dyenamicsandfriends.client.render.comforts;

import com.illusivesoulworks.comforts.client.renderer.BaseComfortsBlockEntityRenderer;
import com.illusivesoulworks.comforts.common.block.entity.BaseComfortsBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsSleepingBagBlockEntity;
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

public class DyenamicsSleepingBagBlockEntityRenderer extends BaseComfortsBlockEntityRenderer<DyenamicsSleepingBagBlockEntity>
{
    public static final Material[] SLEEPING_BAG_TEXTURES = Arrays.stream(DyenamicDyeColor.values()).sorted(Comparator.comparingInt(DyenamicDyeColor::getId)).map((dyeColor) -> {
        return new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(DyenamicsAndFriends.MODID, "entity/comforts/sleeping_bag/" + dyeColor.getSerializedName()));
    }).toArray(Material[]::new);

    public DyenamicsSleepingBagBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx, "hammock", BaseComfortsBlockEntityRenderer.SLEEPING_BAG_HEAD, BaseComfortsBlockEntityRenderer.SLEEPING_BAG_FOOT);
    }

    public static LayerDefinition createHeadLayer() {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        var1.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 3.0F), PartPose.ZERO);
        return LayerDefinition.create(var0, 64, 64);
    }

    public static LayerDefinition createFootLayer() {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        var1.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 3.0F), PartPose.ZERO);
        return LayerDefinition.create(var0, 64, 64);
    }

    @Override
    public void render(BaseComfortsBlockEntity blockEntity, float partialTicks, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        if (blockEntity instanceof DyenamicsSleepingBagBlockEntity dyenamicsSleepingBagBlockEntity) {
            Material material = SLEEPING_BAG_TEXTURES[dyenamicsSleepingBagBlockEntity.getDyenamicColor().getId()];
            Level level = blockEntity.getLevel();
            if (level != null) {
                BlockState blockstate = blockEntity.getBlockState();
                DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> icallbackwrapper = DoubleBlockCombiner.combineWithNeigbour(BlockEntityType.BED, BedBlock::getBlockType, BedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, level, blockEntity.getBlockPos(), (p_228846_0_, p_228846_1_) -> false);
                int i = icallbackwrapper.apply(new BrightnessCombiner<>()).get(combinedLightIn);
                this.renderPiece(matrixStack, buffer, blockstate.getValue(BedBlock.PART) == BedPart.HEAD, blockstate.getValue(BedBlock.FACING), material, i, combinedOverlayIn, false);
            } else {
                this.renderPiece(matrixStack, buffer, true, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, false);
                this.renderPiece(matrixStack, buffer, false, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, true);
            }
        }
    }
}
