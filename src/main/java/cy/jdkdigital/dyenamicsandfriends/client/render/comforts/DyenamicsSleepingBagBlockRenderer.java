package cy.jdkdigital.dyenamicsandfriends.client.render.comforts;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.illusivesoulworks.comforts.client.renderer.SleepingBagBlockEntityRenderer;
import com.illusivesoulworks.comforts.common.block.entity.BaseComfortsBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.DyenamicsSleepingBagBlockEntity;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.comforts.DyenamicsSleepingBagBlockEntity;
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

public class DyenamicsSleepingBagBlockRenderer extends SleepingBagBlockEntityRenderer
{
    public static final Material[] SLEEPING_BAG_TEXTURES = Arrays.stream(DyenamicDyeColor.values()).sorted(Comparator.comparingInt(DyenamicDyeColor::getId)).map((dyeColor) -> {
        return new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(DyenamicsAndFriends.MODID, "entity/comforts/sleeping_bag/" + dyeColor.getSerializedName()));
    }).toArray(Material[]::new);

    public DyenamicsSleepingBagBlockRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BaseComfortsBlockEntity blockEntity, float partialTicks, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        if (blockEntity instanceof DyenamicsSleepingBagBlockEntity dyenamicsSleepingBagBlockEntity) {
            Material material = SLEEPING_BAG_TEXTURES[dyenamicsSleepingBagBlockEntity.getDyenamicColor().getId()];
            Level level = blockEntity.getLevel();
            if (level != null) {
                BlockState blockstate = blockEntity.getBlockState();
                DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> icallbackwrapper = DoubleBlockCombiner.combineWithNeigbour(BlockEntityType.BED, BedBlock::getBlockType, BedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, level, blockEntity.getBlockPos(), (p_228846_0_, p_228846_1_) -> false);
                int i = ((Int2IntFunction)icallbackwrapper.apply(new BrightnessCombiner<>())).get(combinedLightIn);
                this.renderPiece(matrixStack, buffer, blockstate.getValue(BedBlock.PART) == BedPart.HEAD, (Direction)blockstate.getValue(BedBlock.FACING), material, i, combinedOverlayIn, false);
            } else {
                this.renderPiece(matrixStack, buffer, true, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, false);
                this.renderPiece(matrixStack, buffer, false, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, true);
            }
        }
    }
}
