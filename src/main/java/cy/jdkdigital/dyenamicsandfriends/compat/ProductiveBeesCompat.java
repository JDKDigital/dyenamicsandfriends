package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.common.items.DyenamicDyeItem;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.common.block.elevatorid.DyenamicsElevatorBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.elevatorid.DyenamicsElevatorBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import cy.jdkdigital.productivebees.ProductiveBees;
import cy.jdkdigital.productivebees.common.block.entity.CanvasBeehiveBlockEntity;
import cy.jdkdigital.productivebees.common.block.entity.CanvasExpansionBoxBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ProductiveBeesCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> PETRIFIED_HONEY_BLOCKS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "productivebees_" + color.getSerializedName();
        PETRIFIED_HONEY_BLOCKS.put(color, DyenamicRegistry.registerBlock(prefix + "_petrified_honey", () -> new Block(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).lightLevel((state) -> color.getLightValue()).strength(0.3F).noOcclusion().sound(SoundType.BONE_BLOCK)), true));
    }

    public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(ProductiveBees.TAB_KEY)) {
            PETRIFIED_HONEY_BLOCKS.forEach((dyenamicDyeColor, registryObject) -> event.accept(registryObject));
        }
    }

    public static void blockInteract(PlayerInteractEvent event) {
        if (!event.getLevel().isClientSide()) {
            ItemStack stack = event.getEntity().getItemInHand(event.getHand());
            if (stack.getItem() instanceof DyenamicDyeItem dyeItem) {
                if (event.getLevel().getBlockEntity(event.getPos()) instanceof CanvasBeehiveBlockEntity canvasBlockEntity) {
                    canvasBlockEntity.setColor(dyeItem.getDyeColor().getColorValue());
                }
                if (event.getLevel().getBlockEntity(event.getPos()) instanceof CanvasExpansionBoxBlockEntity canvasBlockEntity) {
                    canvasBlockEntity.setColor(dyeItem.getDyeColor().getColorValue());
                }
            }
        }
    }
}
