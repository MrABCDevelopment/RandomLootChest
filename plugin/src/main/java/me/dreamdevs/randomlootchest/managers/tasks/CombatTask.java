package me.dreamdevs.randomlootchest.managers.tasks;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.events.CombatEndPlayerEvent;
import me.dreamdevs.randomlootchest.utils.TimeUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
            Player player = Bukkit.getPlayer(map.getKey());
            if(Bukkit.getPlayer(map.getKey()) != null && Bukkit.getPlayer(map.getKey()).isOnline()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(RandomLootChestMain.getInstance().getMessagesManager().getMessage("combat-info").replaceAll("%TIME%", TimeUtil.formattedTime(value))));
            }
            if (value <= 0) {
                RandomLootChestMain.getInstance().getCombatManager().removeCombat(player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(RandomLootChestMain.getInstance().getMessagesManager().getMessage("combat-expired")));
                CombatEndPlayerEvent event = new CombatEndPlayerEvent(player.getUniqueId());
                Bukkit.getPluginManager().callEvent(event);
            }
        }
    }
}