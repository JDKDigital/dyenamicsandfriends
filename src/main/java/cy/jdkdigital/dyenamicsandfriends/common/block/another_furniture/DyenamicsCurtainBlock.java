//package cy.jdkdigital.dyenamicsandfriends.common.block.another_furniture;
//
//import cofh.dyenamics.core.util.DyenamicDyeColor;
//import com.starfish_studios.another_furniture.block.CurtainBlock;
//import com.starfish_studios.another_furniture.block.entity.CurtainBlockEntity;
//import cy.jdkdigital.dyenamicsandfriends.common.block.entity.another_furniture.DyenamicsCurtainBlockEntity;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.item.DyeColor;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//
//import java.util.function.Supplier;
//
//public class DyenamicsCurtainBlock extends CurtainBlock
//{
//    private final DyenamicDyeColor color;
//    private final Supplier<BlockEntityType<CurtainBlockEntity>> blockEntitySupplier;
//
//    public DyenamicsCurtainBlock(DyenamicDyeColor color, Properties properties, Supplier<BlockEntityType<CurtainBlockEntity>> blockEntitySupplier) {
//        super(DyeColor.WHITE, properties);
//        this.color = color;
//        this.blockEntitySupplier = blockEntitySupplier;
//    }
//
//    @Override
//    public String getColor() {
//        return this.color.getSerializedName();
//    }
//
//    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new DyenamicsCurtainBlockEntity(this, pos, state);
//    }
//
//    public Supplier<BlockEntityType<CurtainBlockEntity>> getBlockEntitySupplier() {
//        return blockEntitySupplier;
//    }
//}
