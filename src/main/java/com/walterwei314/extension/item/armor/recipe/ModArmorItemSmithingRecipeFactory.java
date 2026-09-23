package com.walterwei314.extension.item.armor.recipe;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.datacomponent.ModDataComponents;
import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import org.jetbrains.annotations.NotNull;

public final class ModArmorItemSmithingRecipeFactory {
    private ModArmorItemSmithingRecipeFactory(){}
    public static void buildRecipes(@NotNull RecipeOutput output) {
        ModArmorItems.REFINED_DIAMOND_ARMOR_MAP.forEach((type, armor) -> {
            ItemStack result = new ItemStack(armor.get());
            result.set(ModDataComponents.ACTIVATED.get(), true);

            SmithingTransformRecipe recipe = new SmithingTransformRecipe(
                    Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(armor.get()), Ingredient.of(Items.NETHER_STAR),
                    result
            );

            output.accept(
                    ResourceLocation.fromNamespaceAndPath(Extensionneoforge1211.MODID, "activate_" + armor.getId().getPath()),
                    recipe, null
            );
        });
    }
}