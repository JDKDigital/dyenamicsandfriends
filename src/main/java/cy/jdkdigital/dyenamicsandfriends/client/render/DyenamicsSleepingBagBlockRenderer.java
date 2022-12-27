package cy.jdkdigital.dyenamicsandfriends.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsSleepingBagBlockEntity;
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
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.comforts.client.renderer.SleepingBagTileEntityRenderer;
import top.theillusivec4.comforts.common.tileentity.ComfortsBaseTileEntity;

public class DyenamicsSleepingBagBlockRenderer extends SleepingBagTileEntityRenderer
{
    public DyenamicsSleepingBagBlockRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ComfortsBaseTileEntity tileEntityIn, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn instanceof DyenamicsSleepingBagBlockEntity blockEntity) {
            Material material = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(DyenamicsAndFriends.MODID, "entity/comforts/sleeping_bag/" + blockEntity.getDyenamicColor().getSerializedName()));
            final Level level = tileEntityIn.getLevel();

            if (level != null) {
                final BlockState blockstate = tileEntityIn.getBlockState();
                DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> icallbackwrapper = DoubleBlockCombiner.combineWithNeigbour(BlockEntityType.BED, BedBlock::getBlockType, BedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, level, tileEntityIn.getBlockPos(), (l, p) -> false);
                final int i = icallbackwrapper.apply(new BrightnessCombiner<>()).get(combinedLightIn);
                this.renderPiece(matrixStackIn, bufferIn, blockstate.getValue(BedBlock.PART) == BedPart.HEAD, blockstate.getValue(BedBlock.FACING), material, i, combinedOverlayIn, false);
            } else {
                this.renderPiece(matrixStackIn, bufferIn, true, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, false);
                this.renderPiece(matrixStackIn, bufferIn, false, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, true);
            }
        }
    }
}
