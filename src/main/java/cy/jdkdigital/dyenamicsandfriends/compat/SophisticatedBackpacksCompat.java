package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.crafting.StorageDyeRecipeBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SophisticatedBackpacksCompat
{
    public final static RegistryObject<SimpleCraftingRecipeSerializer<?>> BACKPACK_DYE_RECIPE_SERIALIZER = DyenamicsAndFriends.RECIPE_SERIALIZERS.register("sophisticated_backpackdye", () -> new SimpleCraftingRecipeSerializer<>(BackpackDyeRecipe::new));;
    public static final RegistryObject<RecipeType<BackpackDyeRecipe>> BACKPACK_DYE_RECIPE_TYPE = DyenamicsAndFriends.RECIPE_TYPES.register("sophisticated_backpackdye", () -> new RecipeType<>() {});

    public static void postRegister() {};

    public static class BackpackDyeRecipe extends StorageDyeRecipeBase
    {
        public BackpackDyeRecipe(ResourceLocation registryName, CraftingBookCategory category) {
            super(registryName, category);
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return BACKPACK_DYE_RECIPE_SERIALIZER.get();
        }

        // https://github.com/P3pp3rF1y/SophisticatedCore/blob/1.20.x/src/main/java/net/p3pp3rf1y/sophisticatedcore/crafting/StorageDyeRecipeBase.java
        @Override
        public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
            Map<Integer, List<DyenamicDyeColor>> columnDyes = new HashMap<>();
            Tuple<Integer, ItemStack> columnStorage = null;

            for (int slot = 0; slot < inv.getContainerSize(); slot++) {
                ItemStack slotStack = inv.getItem(slot);
                if (slotStack.isEmpty()) {
                    continue;
                }
                int column = slot % inv.getWidth();
                if (isDyeableStorageItem(slotStack)) {
                    if (columnStorage != null) {
                        return ItemStack.EMPTY;
                    }

                    columnStorage = new Tuple<>(column, slotStack);
                } else if (slotStack.is(Tags.Items.DYES)) {
                    DyenamicDyeColor dyeColor = DyenamicDyeColor.getColor(slotStack);
                    if (dyeColor == null) {
                        return ItemStack.EMPTY;
                    }
                    columnDyes.computeIfAbsent(column, c -> new ArrayList<>()).add(dyeColor);
                } else {
                    return ItemStack.EMPTY;
                }
            }
            if (columnStorage == null) {
                return ItemStack.EMPTY;
            }

            ItemStack coloredStorage = columnStorage.getB().copy();
            coloredStorage.setCount(1);
            int storageColumn = columnStorage.getA();

            applyTintColors(columnDyes, coloredStorage, storageColumn);

            return coloredStorage;
        }

        private void applyTintColors(Map<Integer, List<DyenamicDyeColor>> columnDyes, ItemStack coloredStorage, int storageColumn) {
            List<DyenamicDyeColor> mainDyes = new ArrayList<>();
            List<DyenamicDyeColor> trimDyes = new ArrayList<>();

            for (Map.Entry<Integer, List<DyenamicDyeColor>> entry : columnDyes.entrySet()) {
                if (entry.getKey() <= storageColumn) {
                    mainDyes.addAll(entry.getValue());
                }
                if (entry.getKey() >= storageColumn) {
                    trimDyes.addAll(entry.getValue());
                }
            }

            applyDyenamicsColors(coloredStorage, mainDyes, trimDyes);
        }

        @Override
        protected boolean isDyeableStorageItem(ItemStack stack) {
            return stack.getItem() instanceof BackpackItem;
        }

        @Override
        protected void applyColors(ItemStack itemStack, List<DyeColor> list, List<DyeColor> list1) {
            // stub
        }

        private void applyDyenamicsColors(ItemStack coloredStorage, List<DyenamicDyeColor> mainDyes, List<DyenamicDyeColor> trimDyes) {
            coloredStorage.getCapability(CapabilityBackpackWrapper.getCapabilityInstance())
                    .ifPresent(coloredWrapper -> coloredWrapper.setColors(
                            calculateColor(coloredWrapper.getMainColor(), BackpackWrapper.DEFAULT_CLOTH_COLOR, mainDyes),
                            calculateColor(coloredWrapper.getAccentColor(), BackpackWrapper.DEFAULT_BORDER_COLOR, trimDyes)
                    ));
        }

        // https://github.com/P3pp3rF1y/SophisticatedCore/blob/1.20.x/src/main/java/net/p3pp3rf1y/sophisticatedcore/util/ColorHelper.java
        public static int calculateColor(int baseColor, int defaultColor, List<DyenamicDyeColor> dyes) {
            if (dyes.isEmpty()) {
                return baseColor;
            }

            int[] rgb = new int[3];
            int sumMaxComponent = 0;
            int numberOfColors = 0;
            if (baseColor != defaultColor) {
                float baseRed = (baseColor >> 16 & 255);
                float baseGreen = (baseColor >> 8 & 255);
                float baseBlue = (baseColor & 255);
                sumMaxComponent = (int) (sumMaxComponent + Math.max(baseRed, Math.max(baseGreen, baseBlue)));
                rgb[0] = (int) (rgb[0] + baseRed);
                rgb[1] = (int) (rgb[1] + baseGreen);
                rgb[2] = (int) (rgb[2] + baseBlue);
                ++numberOfColors;
            }

            for (DyenamicDyeColor dye : dyes) {
                float[] dyeRgb = dye.getColorComponentValues();
                int dyeRed = (int) (dyeRgb[0] * 255.0F);
                int dyeGreen = (int) (dyeRgb[1] * 255.0F);
                int dyeBlue = (int) (dyeRgb[2] * 255.0F);
                sumMaxComponent += Math.max(dyeRed, Math.max(dyeGreen, dyeBlue));
                rgb[0] += dyeRed;
                rgb[1] += dyeGreen;
                rgb[2] += dyeBlue;
                ++numberOfColors;
            }

            int avgRed = rgb[0] / numberOfColors;
            int avgGreen = rgb[1] / numberOfColors;
            int avgBlue = rgb[2] / numberOfColors;
            float avgMaxComponent = (float) sumMaxComponent / (float) numberOfColors;
            float maxAvgComponent = Math.max(avgRed, Math.max(avgGreen, avgBlue));
            avgRed = (int) (avgRed * avgMaxComponent / maxAvgComponent);
            avgGreen = (int) (avgGreen * avgMaxComponent / maxAvgComponent);
            avgBlue = (int) (avgBlue * avgMaxComponent / maxAvgComponent);
            int finalColor = (avgRed << 8) + avgGreen;
            finalColor = (finalColor << 8) + avgBlue;

            return finalColor;
        }
    }
}
