package cy.jdkdigital.dyenamicsandfriends.compat;

import cofh.dyenamics.common.items.DyenamicDyeItem;
import cofh.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import cy.jdkdigital.dyenamicsandfriends.client.render.farmersdelight.DyenamicsCanvasSignRenderer;
import cy.jdkdigital.dyenamicsandfriends.common.block.entity.farmersdelight.DyenamicsCanvasSignBlockEntity;
import cy.jdkdigital.dyenamicsandfriends.common.block.farmersdelight.DyenamicsStandingCanvasSignBlock;
import cy.jdkdigital.dyenamicsandfriends.common.block.farmersdelight.DyenamicsWallCanvasSignBlock;
import cy.jdkdigital.dyenamicsandfriends.registry.DyenamicRegistry;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.HashMap;
import java.util.Map;

public class FarmersDelightCompat
{
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CANVAS_SIGNS = new HashMap<>();
    private static final Map<DyenamicDyeColor, RegistryObject<? extends Block>> CANVAS_WALL_SIGNS = new HashMap<>();

    public static void registerBlocks(DyenamicDyeColor color) {
        String prefix = "farmersdelight_" + color.getSerializedName();
        CANVAS_SIGNS.put(color, DyenamicRegistry.registerBlock(prefix + "_canvas_sign", () -> new DyenamicsStandingCanvasSignBlock(color, DyenamicRegistry.registerBlockEntity(prefix + "_canvas_sign", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsCanvasSignBlockEntity(pos, state, (DyenamicsStandingCanvasSignBlock) CANVAS_SIGNS.get(color).get()), CANVAS_SIGNS.get(color).get()))), () -> new SignItem(new Item.Properties().tab(FarmersDelight.CREATIVE_TAB), CANVAS_SIGNS.get(color).get(), CANVAS_WALL_SIGNS.get(color).get())));
        CANVAS_WALL_SIGNS.put(color, DyenamicRegistry.registerBlock(prefix + "_canvas_wall_sign", () -> new DyenamicsWallCanvasSignBlock(color, DyenamicRegistry.registerBlockEntity(prefix + "_canvas_wall_sign", () -> DyenamicRegistry.createBlockEntityType((pos, state) -> new DyenamicsCanvasSignBlockEntity(pos, state, (DyenamicsWallCanvasSignBlock) CANVAS_WALL_SIGNS.get(color).get()), CANVAS_WALL_SIGNS.get(color).get()))), FarmersDelight.CREATIVE_TAB, false));
    }

    public static void playerRightClick(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof DyenamicDyeItem dyenamicDyeItem) {
            BlockEntity blockentity = event.getWorld().getBlockEntity(event.getPos());
            if (blockentity instanceof DyenamicsCanvasSignBlockEntity signBlockEntity) {
                event.getWorld().playSound(null, event.getPos(), SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                boolean hasUsedDye = signBlockEntity.setTextColor(dyenamicDyeItem.getDyeColor());

                if (hasUsedDye) {
                    event.setCanceled(true);
                    if (!event.getPlayer().isCreative()) {
                        itemStack.shrink(1);
                    }
                    event.getPlayer().awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                }
            }
        }
    }

    public static class Client
    {
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            CANVAS_SIGNS.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsStandingCanvasSignBlock sign) {
                    event.registerBlockEntityRenderer(sign.getBlockEntitySupplier().get(), DyenamicsCanvasSignRenderer::new);
                }
            });
            CANVAS_WALL_SIGNS.values().forEach(registryObject -> {
                if (registryObject.get() instanceof DyenamicsWallCanvasSignBlock sign) {
                    event.registerBlockEntityRenderer(sign.getBlockEntitySupplier().get(), DyenamicsCanvasSignRenderer::new);
                }
            });
        }
    }

    public static void stitchTextures(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location() == Sheets.SIGN_SHEET) {
            for (final DyenamicDyeColor color : DyenamicDyeColor.values()) {
                if (color.getId() > 15) {
                    event.addSprite(new ResourceLocation(DyenamicsAndFriends.MODID, "entity/farmersdelight/canvas_" + color.getSerializedName()));
                }
            }
        }
    }
}
