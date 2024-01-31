//package cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries;
//
//import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsPresentBlock;
//import net.mehvahdjukaar.supplementaries.common.block.tiles.PresentBlockTile;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//
//public class DyenamicsPresentBlockEntity extends PresentBlockTile
//{
//    private final DyenamicsPresentBlock block;
//
//    public DyenamicsPresentBlockEntity(DyenamicsPresentBlock block, BlockPos pos, BlockState state) {
//        super(pos, state);
//        this.block = block;
//    }
//
//    @Override
//    public BlockEntityType<?> getType() {
//        return block != null ? block.getBlockEntitySupplier().get() : null;
//    }
//}
