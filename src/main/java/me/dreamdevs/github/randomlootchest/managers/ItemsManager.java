package me.dreamdevs.github.randomlootchest.managers;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.objects.RandomItem;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class ItemsManager {

    private HashMap<String, RandomItem> items;

    public ItemsManager() {
        items = new HashMap<>();
        FileConfiguration config = RandomLootChestMain.getInstance().getConfigManager().getConfig("items.yml");
    }

}