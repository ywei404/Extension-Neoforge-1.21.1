package com.walterwei314.extension.language;

import com.walterwei314.extension.Extensionneoforge1211;
import org.apache.commons.lang3.StringUtils;

public final class LanguageUtils {
    private LanguageUtils() {}

    public static String getTooltipsPathByName(String name){
        if (StringUtils.isBlank(name)){
            throw new IllegalArgumentException("name cannot be blank");
        }

        return "tooltip." + Extensionneoforge1211.MODID + "." + name;
    }
}
