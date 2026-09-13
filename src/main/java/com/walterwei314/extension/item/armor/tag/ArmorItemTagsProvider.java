package com.walterwei314.extension.item.armor.tag;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.item.armor.BaseArmor;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ArmorItemTagsProvider extends ItemTagsProvider {

    public ArmorItemTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagsProvider.TagLookup<Block>> blockTags,
            ExistingFileHelper existingFileHelper
    ) {
        super(
                output,
                lookupProvider,
                blockTags,
                Extensionneoforge1211.MODID,
                existingFileHelper
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        BuiltInRegistries.ITEM.forEach(item -> {
            if (!(item instanceof BaseArmor armor)) {
                return;
            }

            var key = BuiltInRegistries.ITEM.getKey(item);

            if (!key.getNamespace().equals(Extensionneoforge1211.MODID)) {
                return;
            }

            // 所有护甲通用
            tag(ItemTags.ARMOR_ENCHANTABLE).add(armor);
            // 耐久类：耐久、经验修补等
            tag(ItemTags.DURABILITY_ENCHANTABLE).add(armor);
            // 装备类：绑定诅咒等
            tag(ItemTags.EQUIPPABLE_ENCHANTABLE).add(armor);
            // 消失诅咒
            tag(ItemTags.VANISHING_ENCHANTABLE).add(armor);

            // 各部位专属附魔
            switch (armor.getType()) {
                case HELMET -> tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(armor);
                case CHESTPLATE -> tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(armor);
                case LEGGINGS -> tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(armor);
                case BOOTS -> tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(armor);
                default -> {}
            }
        });
    }
}