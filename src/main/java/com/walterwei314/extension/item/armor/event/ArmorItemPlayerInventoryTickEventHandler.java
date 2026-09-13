package com.walterwei314.extension.item.armor.event;

import com.walterwei314.extension.item.armor.ability.InventoryTick;
import com.walterwei314.extension.item.armor.util.ArmorItemUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;
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
        System.out.println(tickCount);
        fullSetConsumers.forEach(consumer -> consumer.accept(player, tickCount));
        playerArmorTickData.put(playerId, tickCount + 1);
    }


    @SubscribeEvent
    public static void clearPlayerArmorTickDataWhenDying(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player player) || entity.level().isClientSide()) {
            return;
        }

        UUID playerId = player.getUUID();

        InventoryTick.TICK_COUNTS.forEach(
                (armorSet, playerTickMap) -> playerTickMap.remove(playerId)
        );
    }
}
