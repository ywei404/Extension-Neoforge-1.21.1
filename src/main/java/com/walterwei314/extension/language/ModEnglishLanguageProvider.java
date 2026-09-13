package com.walterwei314.extension.language;

import com.walterwei314.extension.Extensionneoforge1211;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class ModEnglishLanguageProvider extends LanguageProvider {
    public ModEnglishLanguageProvider(PackOutput output) {
        super(output, Extensionneoforge1211.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        BuiltInRegistries.ITEM.forEach(item -> {
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
            String namespace = key.getNamespace();

            if (!namespace.equals(Extensionneoforge1211.MODID)){
                return;
            }

            String path = key.getPath();
            String[] words = StringUtils.split(path, "_");
            String[] capitalizedWords = Arrays.stream(words).map(StringUtils::capitalize).toArray(String[]::new);
            String capitalizedPath = String.join(" ", capitalizedWords);

            add(item, capitalizedPath);
        });
    }
}
