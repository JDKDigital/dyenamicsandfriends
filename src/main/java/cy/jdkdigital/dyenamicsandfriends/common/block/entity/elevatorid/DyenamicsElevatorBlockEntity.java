package cy.jdkdigital.dyenamicsandfriends.common.block.entity.elevatorid;

import com.vsngarcia.level.ElevatorBlockEntityBase;
import com.vsngarcia.level.ElevatorContainer;
import com.vsngarcia.neoforge.client.render.ElevatorBakedModel;
import com.vsngarcia.neoforge.init.Registry;
import cy.jdkdigital.dyenamicsandfriends.compat.ElevatoridCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.world.AuxiliaryLightManager;
import org.jetbrains.annotations.Nullable;

public class DyenamicsElevatorBlockEntity extends ElevatorBlockEntityBase
{
    public DyenamicsElevatorBlockEntity(BlockPos pos, BlockState state) {
        super(ElevatoridCompat.ELEVATOR_BLOCK_ENTITY.get(), pos, state);
    }

    protected SoundEvent camouflageSound() {
        return (SoundEvent) Registry.CAMOUFLAGE_SOUND.get();
    }

    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new ElevatorContainer(Registry.ELEVATOR_CONTAINER.get(), id, this.worldPosition, player);
    }

    @Override
    public ModelData getModelData() {
        return ModelData.builder().with(ElevatorBakedModel.HELD_STATE, this.heldState).build();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.requestModelDataUpdate();
        AuxiliaryLightManager auxLightManager = this.level != null ? this.level.getAuxLightManager(this.worldPosition) : null;
        if (auxLightManager != null) {
            auxLightManager.setLightAt(this.worldPosition, this.heldState != null ? this.heldState.getLightEmission(this.level, this.worldPosition) : 0);
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        this.setChanged();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider provider) {
        this.handleUpdateTag(pkt.getTag(), provider);
    }
}
