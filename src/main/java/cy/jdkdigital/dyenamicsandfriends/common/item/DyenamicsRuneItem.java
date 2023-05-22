//package cy.jdkdigital.dyenamicsandfriends.common.item;
//
//import cofh.dyenamics.core.util.DyenamicDyeColor;
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ICapabilityProvider;
//import net.minecraftforge.common.util.LazyOptional;
//import vazkii.quark.api.IRuneColorProvider;
//import vazkii.quark.api.QuarkCapabilities;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//
//public class DyenamicsRuneItem extends Item implements IRuneColorProvider
//{
//    private final DyenamicDyeColor color;
//
//    public DyenamicsRuneItem(DyenamicDyeColor color, Properties properties) {
//        super(properties);
//        this.color = color;
//    }
//
//    @Override
//    public int getRuneColor(ItemStack itemStack) {
//        return color.getColorValue();
//    }
//
//    @Override
//    public boolean isFoil(@Nonnull ItemStack stack) {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        final LazyOptional<IRuneColorProvider> holder = LazyOptional.of(() -> this);
//        return new ICapabilityProvider() {
//            @Nonnull
//            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//                return QuarkCapabilities.RUNE_COLOR.orEmpty(cap, holder);
//            }
//        };
//    }
//}
