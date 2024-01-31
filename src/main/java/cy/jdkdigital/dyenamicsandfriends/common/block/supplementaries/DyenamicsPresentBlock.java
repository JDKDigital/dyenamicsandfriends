//package cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries;
//
//import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
//import cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries.DyenamicsPresentBlockEntity;
//import net.mehvahdjukaar.supplementaries.common.block.blocks.PresentBlock;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//
//import java.util.function.Supplier;
//
//public class DyenamicsPresentBlock extends PresentBlock
//{
//    private final Supplier<BlockEntityType<DyenamicsPresentBlockEntity>> blockEntitySupplier;
//
//    public DyenamicsPresentBlock(DyenamicDyeColor color, Properties properties, Supplier<BlockEntityType<DyenamicsPresentBlockEntity>> blockEntitySupplier) {
//        super(color.getAnalogue(), properties);
//        this.blockEntitySupplier = blockEntitySupplier;
//    }
//
//    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new DyenamicsPresentBlockEntity(this, pos, state);
//    }
//
//    public Supplier<BlockEntityType<DyenamicsPresentBlockEntity>> getBlockEntitySupplier() {
//        return blockEntitySupplier;
//    }
//}
