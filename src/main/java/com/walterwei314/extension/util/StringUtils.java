package com.walterwei314.extension.util;

import java.util.Arrays;

public final class StringUtils {
    private StringUtils(){}

    public static String fromPathToDisplayName(String path){
        if (org.apache.commons.lang3.StringUtils.isBlank(path)){
            return "";
        }

        String[] words = org.apache.commons.lang3.StringUtils.split(path, "_");
        String[] capitalizedWords = Arrays.stream(words).map(org.apache.commons.lang3.StringUtils::capitalize).toArray(String[]::new);

        return String.join(" ", capitalizedWords);
    }
}
