package me.dreamdevs.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.events.CombatStartPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CombatManager {

    private final @Getter Map<UUID, AtomicInteger> combatTimers;

    public CombatManager() {
        combatTimers = new ConcurrentHashMap<>();
    }

    public void applyCombat(Player player, int time) {
        combatTimers.put(player.getUniqueId(), new AtomicInteger(time));
        Bukkit.getPluginManager().callEvent(new CombatStartPlayerEvent(player.getUniqueId()));
    }

    public void removeCombat(Player player) {
        combatTimers.remove(player.getUniqueId());
    }

    public boolean isInCombat(Player player) {
        return combatTimers.containsKey(player.getUniqueId()) && combatTimers.get(player.getUniqueId()).get() > 0;
    }

}