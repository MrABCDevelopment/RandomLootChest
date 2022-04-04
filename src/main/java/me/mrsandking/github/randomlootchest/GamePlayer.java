package me.mrsandking.github.randomlootchest;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.UUID;

public class GamePlayer {

    private Player player;

    public GamePlayer(Player player) {
        this.player = player;
    }

    public boolean restoreCooldowns() {
        if(RandomLootChestMain.getInstance().getCooldownManager().getPlayerCooldowns(player.getUniqueId()).isEmpty()) return false;
        for(HashMap<UUID, Location> map : RandomLootChestMain.getInstance().getCooldownManager().getPlayerCooldowns(player.getUniqueId()).keySet()) {
            if(RandomLootChestMain.getInstance().getCooldownManager().getChestCooldowns().get(map) > System.currentTimeMillis()) {
                continue;
            } else {
                RandomLootChestMain.getInstance().getCooldownManager().getChestCooldowns().remove(map);
            }
        }
        return true;
    }

    public Player getPlayer() {
        return player;
    }
}