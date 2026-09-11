package com.walterwei314.extension.item.armor.recipe;

import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ArmorItemRecipeProvider extends RecipeProvider {

    public ArmorItemRecipeProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries
    ) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        generateArmorRecipes(output, Items.DIRT, ModArmorItems.DIRT_ARMOR_SET);
    }

    private void generateArmorRecipes(
            RecipeOutput output,
            Item material,
            List<DeferredItem<ArmorItem>> armorSet
    ) {

        // Helmet
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.COMBAT,
                        armorSet.get(0).get()
                )
                .define('M', material)
                .pattern("MMM")
                .pattern("M M")
                .unlockedBy("has_material", has(material))
                .save(output);

        // Chestplate
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.COMBAT,
                        armorSet.get(1).get()
                )
                .define('M', material)
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .unlockedBy("has_material", has(material))
                .save(output);

        // Leggings
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.COMBAT,
                        armorSet.get(2).get()
                )
                .define('M', material)
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .unlockedBy("has_material", has(material))
                .save(output);

        // Boots
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.COMBAT,
                        armorSet.get(3).get()
                )
                .define('M', material)
                .pattern("M M")
                .pattern("M M")
                .unlockedBy("has_material", has(material))
                .save(output);
    }
}