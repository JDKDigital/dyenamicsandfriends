//package cy.jdkdigital.dyenamicsandfriends.common.block.furnish;
//
//import cy.jdkdigital.dyenamicsandfriends.common.block.entity.furnish.DyenamicsAmphoraBlockEntity;
//import io.github.wouink.furnish.block.Amphora;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//
//import java.util.function.Supplier;
//
//public class DyenamicsAmphora extends Amphora
//{
//    private final Supplier<BlockEntityType<DyenamicsAmphoraBlockEntity>> blockEntitySupplier;
//
//    public DyenamicsAmphora(Properties properties, Supplier<BlockEntityType<DyenamicsAmphoraBlockEntity>> blockEntitySupplier) {
//        super(properties);
//        this.blockEntitySupplier = blockEntitySupplier;
//    }
//
//    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new DyenamicsAmphoraBlockEntity(pos, state, this);
//    }
//
//    public Supplier<BlockEntityType<DyenamicsAmphoraBlockEntity>> getBlockEntitySupplier() {
//        return blockEntitySupplier;
//    }
//}
