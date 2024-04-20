package me.dreamdevs.randomlootchest.managers.tasks;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.Language;
import me.dreamdevs.randomlootchest.api.event.player.PlayerCombatEndEvent;
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

            if (player == null) {
                RandomLootChestMain.getInstance().getCombatManager().removeCombat(map.getKey());
                continue;
            }

            if (player.isOnline() && Config.USE_COMBAT_ACTION_BAR.toBoolean()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Language.GENERAL_COMBAT_MESSAGE.toString().replace("%TIME%", TimeUtil.formattedTime(value))));
            }
            if (value <= 0) {
                RandomLootChestMain.getInstance().getCombatManager().removeCombat(player.getUniqueId());
                if (Config.USE_COMBAT_ACTION_BAR.toBoolean()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Language.GENERAL_COMBAT_EXPIRED.toString()));
                }
                PlayerCombatEndEvent event = new PlayerCombatEndEvent(player.getUniqueId());
                Bukkit.getPluginManager().callEvent(event);
            }
        }
    }
}