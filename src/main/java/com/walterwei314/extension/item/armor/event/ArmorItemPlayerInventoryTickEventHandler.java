package com.walterwei314.extension.item.armor.event;

import com.walterwei314.extension.item.armor.ModArmorItems;
import com.walterwei314.extension.item.armor.ability.InventoryTick;
import com.walterwei314.extension.item.armor.util.ArmorItemUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

@EventBusSubscriber
public class ArmorItemPlayerInventoryTickEventHandler {
    @SubscribeEvent
    public static void onWearingFullSetOfArmor(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        Map<ArmorItem.Type, ArmorItem> currentArmorItemMap = ArmorItemUtils.getCurrentArmorItemMap(player);
        Collection<BiConsumer<Player, Long>> fullSetConsumers = InventoryTick.FULL_SET_TICKS.get(currentArmorItemMap);

        if (fullSetConsumers.isEmpty()) {
            return;
        }

        UUID playerId = player.getUUID();

        Map<UUID, Long> playerArmorTickData =
                InventoryTick.TICK_COUNTS.computeIfAbsent(
                        currentArmorItemMap, key -> new HashMap<>()
                );

        long tickCount = playerArmorTickData.getOrDefault(playerId, 0L);
        fullSetConsumers.forEach(consumer -> consumer.accept(player, tickCount));
        playerArmorTickData.put(playerId, tickCount + 1);
    }


    @SubscribeEvent
    public static void clearPlayerArmorTickDataWhenDying(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        UUID playerId = player.getUUID();

        InventoryTick.TICK_COUNTS.forEach(
                (armorSet, playerTickMap) -> playerTickMap.remove(playerId)
        );
    }

    @SubscribeEvent
    public static void absorbDamageByPassArmor(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Player player
                && !player.level().isClientSide()
                && ArmorItemUtils.isWearingFullSet(player, ArmorItemUtils.getArmorItemMap(ModArmorItems.ENCHANTED_GOLDEN_APPLE_ARMOR_MAP))
        ) {
            event.addReductionModifier(DamageContainer.Reduction.ARMOR, (damageContainer, v) -> {
                DamageSource source = damageContainer.getSource();

                if (!source.is(DamageTypeTags.BYPASSES_ARMOR)) {
                    return v;
                }

                float damage = damageContainer.getNewDamage();
                double armorValue = player.getAttributeValue(Attributes.ARMOR);
                double armorToughnessValue = player.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
                float damageAfterAbsorb = CombatRules.getDamageAfterAbsorb(player, damage, source, ((float) armorValue), ((float) armorToughnessValue));
                v = damage - damageAfterAbsorb;

                return v;
            });
        }
    }
}
