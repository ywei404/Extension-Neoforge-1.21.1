package com.walterwei314.extension.language;

import com.walterwei314.extension.Extensionneoforge1211;
import com.walterwei314.extension.util.ReflectionUtils;
import com.walterwei314.extension.util.StringUtils;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.lang.reflect.Modifier;

public final class ModTooltips {
    private ModTooltips(){}

    public static final String FULLY_AWAKENED = "fully_awakened";

    public static void register(LanguageProvider provider){
        ReflectionUtils.fieldForEach(ModTooltips.class, field -> {
            if (!Modifier.isStatic(field.getModifiers())) {
                return;
            }

            if (field.getType() != String.class) {
                return;
            }

            try {
                String path = (String) field.get(null);

                provider.add(
                        "tooltip." + Extensionneoforge1211.MODID + "." + path,
                        "★ " + StringUtils.fromPathToDisplayName(path).toUpperCase() + " ★"
                );
            } catch (IllegalAccessException e) {
                throw new RuntimeException(
                        "Failed to access field: " + field.getName(),
                        e
                );
            }
        });
    }
}
