package cy.jdkdigital.dyenamicsandfriends.common.block.create;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.simibubi.create.content.contraptions.components.crank.HandCrankBlock;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.simibubi.create.foundation.utility.Couple;
import cy.jdkdigital.dyenamicsandfriends.compat.CreateCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;

// Create compat
public class DyenamicsValveHandleBlock extends HandCrankBlock
{
    private final DyenamicDyeColor color;

    public DyenamicsValveHandleBlock(Properties properties, DyenamicDyeColor color) {
        super(properties);
        this.color = color;
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        DyenamicDyeColor color = DyenamicDyeColor.getColor(heldItem);
        if (color != null && color != this.color) {
            if (!world.isClientSide) {
                if (color.getId() > 15) {
                    BlockState newState = BlockHelper.copyProperties(state, CreateCompat.VALVE_HANDLES.get(color).get().defaultBlockState());
                    world.setBlockAndUpdate(pos, newState);
                } else {
                    BlockState newState = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("create:" + color.getSerializedName() + "_valve_handle")).defaultBlockState();
                    world.setBlockAndUpdate(pos, newState);
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.use(state, world, pos, player, hand, hit);
        }
    }

    public int getRotationSpeed() {
        return 16;
    }

    public static Couple<Integer> getSpeedRange() {
        return Couple.create(16, 16);
    }
}
