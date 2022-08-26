package me.dreamdevs.github.randomlootchest.hooks;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class HolographicDisplaysHook {

    private RandomLootChestMain plugin;
    private @Getter HashMap<UUID, Location> maps = new HashMap<>();
    public HolographicDisplaysHook(RandomLootChestMain plugin) {
        this.plugin = plugin;
    }

    public void createTempHolo(Player player, Location location) {
        String text = RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("hologram-on-chest");
        String time = TimeUtil.formattedTime(plugin.getCooldownManager().getPlayerCooldown(player.getUniqueId(), location));
        maps.put(player.getUniqueId(), location);
        Hologram holo = HologramsAPI.createHologram(plugin, location.add(0.5,1.5,0.5));
        holo.appendTextLine(text.replace("{TIME}", time));
        holo.getVisibilityManager().setVisibleByDefault(false);
        holo.getVisibilityManager().showTo(player);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
           @Override
            public void run() {
                holo.delete();
                maps.remove(player.getUniqueId());
            }
        }, 20L);
    }

}