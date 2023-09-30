package me.dreamdevs.randomlootchest.managers.tasks;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Language;
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
        if (RandomLootChestMain.getInstance().getCombatManager().getCombatTimers().isEmpty())
            return;
        for(Map.Entry<UUID, AtomicInteger> map : RandomLootChestMain.getInstance().getCombatManager().getCombatTimers().entrySet()) {
            int value = RandomLootChestMain.getInstance().getCombatManager().getCombatTimers().get(map.getKey()).decrementAndGet();
            Player player = Bukkit.getPlayer(map.getKey());
            assert player != null;
            if (player.isOnline()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Language.GENERAL_COMBAT_MESSAGE.toString().replace("%TIME%", TimeUtil.formattedTime(value))));
            }
            if (value <= 0) {
                RandomLootChestMain.getInstance().getCombatManager().removeCombat(player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Language.GENERAL_COMBAT_EXPIRED.toString()));
                CombatEndPlayerEvent event = new CombatEndPlayerEvent(player.getUniqueId());
                Bukkit.getPluginManager().callEvent(event);
            }
        }
    }
}