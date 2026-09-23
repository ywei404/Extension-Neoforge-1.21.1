package com.walterwei314.extension.item.armor.data;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.item.armor.model.ArmorItemModelProvider;
import com.walterwei314.extension.item.armor.recipe.ArmorItemRecipeProvider;
import com.walterwei314.extension.item.armor.tag.ArmorItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Extensionneoforge1211.MODID)
public final class ArmorItemDataGenerator {

    private ArmorItemDataGenerator() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        CompletableFuture<TagsProvider.TagLookup<Block>> blockTags =
                CompletableFuture.completedFuture(TagsProvider.TagLookup.empty());

        generator.addProvider(event.includeServer(), new ArmorItemRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeClient(), new ArmorItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new ArmorItemTagsProvider(output, lookupProvider, blockTags, existingFileHelper));
    }
}