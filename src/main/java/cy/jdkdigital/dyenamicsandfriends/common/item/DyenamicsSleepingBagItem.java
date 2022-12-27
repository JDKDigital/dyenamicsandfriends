package cy.jdkdigital.dyenamicsandfriends.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.comforts.common.ComfortsConfig;
import top.theillusivec4.comforts.common.block.RopeAndNailBlock;
import top.theillusivec4.comforts.common.capability.CapabilitySleepData;
import top.theillusivec4.comforts.common.network.ComfortsNetwork;
import top.theillusivec4.comforts.common.network.SPacketAutoSleep;

import javax.annotation.Nonnull;

import static top.theillusivec4.comforts.common.block.RopeAndNailBlock.HORIZONTAL_FACING;
import static top.theillusivec4.comforts.common.block.RopeAndNailBlock.SUPPORTING;

public class DyenamicsSleepingBagItem extends BlockItem
{
    public DyenamicsSleepingBagItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    // Recreate mod item because Comforts did not do good code
    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        final InteractionResult result = super.useOn(context);
        final Player player = context.getPlayer();

        if (player instanceof ServerPlayer && result.consumesAction() && ComfortsConfig.SERVER.autoUse.get() && !player.isCrouching()) {
            final BlockPos pos = context.getClickedPos().above();
            CapabilitySleepData.getCapability(player).ifPresent(sleepdata -> sleepdata.setAutoSleepPos(pos));
            ComfortsNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new SPacketAutoSleep(player.getId(), pos));
        }
        return result;
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, @Nonnull BlockState state) {
        return context.getLevel().setBlock(context.getClickedPos(), state, 26);
    }
}
