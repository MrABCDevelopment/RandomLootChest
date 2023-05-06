package me.dreamdevs.github.randomlootchest.managers.tasks;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.CombatEndPlayerEvent;
import me.dreamdevs.github.randomlootchest.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CombatTask extends BukkitRunnable {

    public CombatTask() {
        runTaskTimer(RandomLootChestMain.getInstance(), 20L, 20L);
    }

    @Override
    public void run() {
        if (RandomLootChestMain.getInstance().getCombatManager().getCombatTimers().isEmpty()) return;
        for(Map.Entry<UUID, AtomicInteger> map : RandomLootChestMain.getInstance().getCombatManager().getCombatTimers().entrySet()) {
            int value = RandomLootChestMain.getInstance().getCombatManager().getCombatTimers().get(map.getKey()).decrementAndGet();
            if(Bukkit.getPlayer(map.getKey()) != null && Bukkit.getPlayer(map.getKey()).isOnline())
                ReflectionUtils.sendActionBar(Bukkit.getPlayer(map.getKey()), "You are in combat for "+value+" seconds!");
            if (value <= 0) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(map.getKey());
                RandomLootChestMain.getInstance().getCombatManager().removeCombat(player.getPlayer());
                CombatEndPlayerEvent event = new CombatEndPlayerEvent(player.getUniqueId());
                Bukkit.getPluginManager().callEvent(event);
            }
        }
    }
}