package com.walterwei314.extension.item.armor.recipe;

import com.walterwei314.extension.item.armor.ModArmorItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
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

        // Tag 类型
        generateArmorRecipes(output, ItemTags.DIRT, ModArmorItems.DIRT_ARMOR_MAP);
        generateArmorRecipes(output, ItemTags.LOGS, ModArmorItems.WOODEN_ARMOR_MAP);
        generateArmorRecipes(output, ItemTags.STONE_TOOL_MATERIALS, ModArmorItems.STONE_ARMOR_MAP);

        // ItemLike 类型以后也可以这样：
        //
        // generateArmorRecipes(
        //         output,
        //         Items.OBSIDIAN,
        //         ModArmorItems.OBSIDIAN_ARMOR_MAP
        // );
    }

    /*
     * 单个 Item / Block
     *
     * 例如：
     * Items.OBSIDIAN
     * Items.EMERALD
     */
    private void generateArmorRecipes(
            RecipeOutput output,
            ItemLike material,
            Map<ArmorItem.Type, DeferredItem<ArmorItem>> armorSet
    ) {
        generateArmorRecipes(
                output,
                Ingredient.of(material),
                has(material),
                armorSet
        );
    }

    /*
     * Item Tag
     *
     * 例如：
     * #extension:dirt_armor_materials
     */
    private void generateArmorRecipes(
            RecipeOutput output,
            TagKey<Item> materialTag,
            Map<ArmorItem.Type, DeferredItem<ArmorItem>> armorSet
    ) {
        generateArmorRecipes(
                output,
                Ingredient.of(materialTag),
                has(materialTag),
                armorSet
        );
    }

    /*
     * 真正负责生成配方的底层方法。
     *
     * 上面两种参数最终全部转换成 Ingredient。
     */
    private void generateArmorRecipes(
            RecipeOutput output,
            Ingredient material,
            Criterion<?> unlockCriterion,
            Map<ArmorItem.Type, DeferredItem<ArmorItem>> armorSet
    ) {

        // Helmet
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.COMBAT,
                        armorSet.get(ArmorItem.Type.HELMET).get()
                )
                .define('M', material)
                .pattern("MMM")
                .pattern("M M")
                .unlockedBy("has_material", unlockCriterion)
                .save(output);

        // Chestplate
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.COMBAT,
                        armorSet.get(ArmorItem.Type.CHESTPLATE).get()
                )
                .define('M', material)
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .unlockedBy("has_material", unlockCriterion)
                .save(output);

        // Leggings
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.COMBAT,
                        armorSet.get(ArmorItem.Type.LEGGINGS).get()
                )
                .define('M', material)
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .unlockedBy("has_material", unlockCriterion)
                .save(output);

        // Boots
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.COMBAT,
                        armorSet.get(ArmorItem.Type.BOOTS).get()
                )
                .define('M', material)
                .pattern("M M")
                .pattern("M M")
                .unlockedBy("has_material", unlockCriterion)
                .save(output);
    }
}