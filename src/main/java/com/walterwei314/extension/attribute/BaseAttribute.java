package com.walterwei314.extension.attribute;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class BaseAttribute extends RangedAttribute {
    public BaseAttribute(String descriptionId, double defaultValue, double min, double max) {
        super(descriptionId, defaultValue, min, max);
    }
}
