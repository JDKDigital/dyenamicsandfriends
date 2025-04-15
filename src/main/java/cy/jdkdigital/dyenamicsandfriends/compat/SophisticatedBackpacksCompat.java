package cy.jdkdigital.dyenamicsandfriends.compat;

import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import cy.jdkdigital.dyenamicsandfriends.DyenamicsAndFriends;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.crafting.StorageDyeRecipeBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SophisticatedBackpacksCompat
{
    public final static DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<?>> BACKPACK_DYE_RECIPE_SERIALIZER = DyenamicsAndFriends.RECIPE_SERIALIZERS.register("sophisticated_backpackdye", () -> new SimpleCraftingRecipeSerializer<>(BackpackDyeRecipe::new));;
    public static final DeferredHolder<RecipeType<?>, RecipeType<BackpackDyeRecipe>> BACKPACK_DYE_RECIPE_TYPE = DyenamicsAndFriends.RECIPE_TYPES.register("sophisticated_backpackdye", () -> new RecipeType<>() {});

    public static void postRegister() {};

    public static class BackpackDyeRecipe extends StorageDyeRecipeBase
    {
        public BackpackDyeRecipe(CraftingBookCategory category) {
            super(category);
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return BACKPACK_DYE_RECIPE_SERIALIZER.get();
        }

        @Override
        protected boolean isDyeableStorageItem(ItemStack stack) {
            return stack.getItem() instanceof BackpackItem;
        }

        // https://github.com/P3pp3rF1y/SophisticatedCore/blob/1.21.x/src/main/java/net/p3pp3rf1y/sophisticatedcore/crafting/StorageDyeRecipeBase.java
        @Override
        public ItemStack assemble(CraftingInput inv, HolderLookup.Provider registries) {
            Map<Integer, List<DyenamicDyeColor>> columnDyes = new HashMap<>();
            Tuple<Integer, ItemStack> columnStorage = null;

            for (int slot = 0; slot < inv.size(); slot++) {
                ItemStack slotStack = inv.getItem(slot);
                if (slotStack.isEmpty()) {
                    continue;
                }
                int column = slot % inv.width();
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

            applyDyenamicColors(coloredStorage, mainDyes, trimDyes);
        }

        private void applyDyenamicColors(ItemStack coloredStorage, List<DyenamicDyeColor> mainDyes, List<DyenamicDyeColor> trimDyes) {
            IBackpackWrapper coloredWrapper = BackpackWrapper.fromStack(coloredStorage);
            coloredWrapper.setColors(ColorHelper.calculateColor(coloredWrapper.getMainColor(), -3382982, mainDyes), ColorHelper.calculateColor(coloredWrapper.getAccentColor(), -10342886, trimDyes));
        }

        @Override
        protected void applyColors(ItemStack itemStack, List<DyeColor> list, List<DyeColor> list1) {
            // :shrug:
        }
    }

    static class ColorHelper {
        public static int calculateColor(int baseColor, int defaultColor, List<DyenamicDyeColor> dyes) {
            if (dyes.isEmpty()) {
                return baseColor;
            } else {
                int[] rgb = new int[3];
                int sumMaxComponent = 0;
                int numberOfColors = 0;
                if (baseColor != defaultColor) {
                    float baseRed = (float)(baseColor >> 16 & 255);
                    float baseGreen = (float)(baseColor >> 8 & 255);
                    float baseBlue = (float)(baseColor & 255);
                    sumMaxComponent = (int)((float)sumMaxComponent + Math.max(baseRed, Math.max(baseGreen, baseBlue)));
                    rgb[0] = (int)((float)rgb[0] + baseRed);
                    rgb[1] = (int)((float)rgb[1] + baseGreen);
                    rgb[2] = (int)((float)rgb[2] + baseBlue);
                    ++numberOfColors;
                }

                for(DyenamicDyeColor dye : dyes) {
                    int dyeRgb = dye.getColorValue();
                    int dyeRed = dyeRgb >> 16 & 255;
                    int dyeGreen = dyeRgb >> 8 & 255;
                    int dyeBlue = dyeRgb & 255;
                    sumMaxComponent += Math.max(dyeRed, Math.max(dyeGreen, dyeBlue));
                    rgb[0] += dyeRed;
                    rgb[1] += dyeGreen;
                    rgb[2] += dyeBlue;
                    ++numberOfColors;
                }

                int avgRed = rgb[0] / numberOfColors;
                int avgGreen = rgb[1] / numberOfColors;
                int avgBlue = rgb[2] / numberOfColors;
                float avgMaxComponent = (float)sumMaxComponent / (float)numberOfColors;
                float maxAvgComponent = (float)Math.max(avgRed, Math.max(avgGreen, avgBlue));
                avgRed = (int)((float)avgRed * avgMaxComponent / maxAvgComponent);
                avgGreen = (int)((float)avgGreen * avgMaxComponent / maxAvgComponent);
                avgBlue = (int)((float)avgBlue * avgMaxComponent / maxAvgComponent);
                int finalColor = (avgRed << 8) + avgGreen;
                finalColor = (finalColor << 8) + avgBlue;
                return -16777216 | finalColor;
            }
        }
    }
}
