package me.mrsandking.github.randomlootchest;

import org.bukkit.entity.Player;

import java.io.File;

public class GamePlayer {

    private Player player;
    private File dataFile;

    public GamePlayer(Player player) {
        this.player = player;
        this.dataFile = new File(RandomLootChestMain.getInstance().getDataFolder(), "users/"+player.getUniqueId()+".yml");
    }

    public File getDataFile() {
        return dataFile;
    }

    public boolean createFile() {
        if(getDataFile().exists()) return false;
        try {
            getDataFile().createNewFile();
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public Player getPlayer() {
        return player;
    }
}