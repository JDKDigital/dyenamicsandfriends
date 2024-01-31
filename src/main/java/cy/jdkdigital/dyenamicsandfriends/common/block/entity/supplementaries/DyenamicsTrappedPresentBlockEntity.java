//package cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries;
//
//import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsTrappedPresentBlock;
//import net.mehvahdjukaar.supplementaries.common.block.tiles.TrappedPresentBlockTile;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//
//public class DyenamicsTrappedPresentBlockEntity extends TrappedPresentBlockTile
//{
//    private final DyenamicsTrappedPresentBlock block;
//
//    public DyenamicsTrappedPresentBlockEntity(DyenamicsTrappedPresentBlock block, BlockPos pos, BlockState state) {
//        super(pos, state);
//        this.block = block;
//    }
//
//    @Override
//    public BlockEntityType<?> getType() {
//        return block != null ? block.getBlockEntitySupplier().get() : null;
//    }
//}
