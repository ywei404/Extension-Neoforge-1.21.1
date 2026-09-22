package com.walterwei314.extension.util;

import com.walterwei314.extension.Extensionneoforge1211;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public final class ModUtils {
    private ModUtils() {
    }

    public static boolean isKeyInThisMod(ResourceLocation key) {
        if (key == null) {
            return false;
        }

        return key.getNamespace().equals(Extensionneoforge1211.MODID);
    }
}
