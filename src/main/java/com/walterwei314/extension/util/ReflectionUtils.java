package com.walterwei314.extension.util;

import net.minecraft.resources.ResourceKey;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Consumer;

public final class ReflectionUtils {
    private ReflectionUtils() {}

    public static void fieldForEach(
            Class<?> clazz,
            Consumer<Field> action
    ) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(action);

        for (Field field : clazz.getDeclaredFields()) {
            action.accept(field);
        }
    }

    public static void staticResourceKeyForEach(
            Class<?> clazz,
            Consumer<ResourceKey<?>> action
    ) {
        fieldForEach(clazz, field -> {
            if (!Modifier.isStatic(field.getModifiers())) {
                return;
            }

            if (!ResourceKey.class.isAssignableFrom(field.getType())) {
                return;
            }

            try {
                Object value = field.get(null);

                if (value instanceof ResourceKey<?> key) {
                    action.accept(key);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(
                        "Failed to access field: " + field.getName(),
                        e
                );
            }
        });
    }
}
