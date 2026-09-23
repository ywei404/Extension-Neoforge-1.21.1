package com.walterwei314.extension;

import com.walterwei314.extension.attribute.ModAttributes;
import com.walterwei314.extension.item.armor.ModArmorItems;
import com.walterwei314.extension.item.armor.util.ArmorItemUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class Test {
    @SubscribeEvent
    public static void test(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player player && !entity.level().isClientSide() && entity.level().getGameTime() % 20 == 0) {
//            AttributeInstance instance = player.getAttribute(ModAttributes.HEAL_MULTIPLIER);
//
//            if (instance != null) {
//                System.out.println("heal_multiplier: " + instance.getValue());
//            }

            System.out.println(player.getAttribute(ModAttributes.INVULNERABILITY_TICKS.getDelegate()).getValue());
        }
    }
}
