package me.mrsandking.randomlootchest.util;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class ItemsManager {

    private HashMap<String, RandomItem> items;

    public ItemsManager() {
        items = new HashMap<>();
        FileConfiguration config = RandomLootChestMain.getInstance().getConfigManager().getConfig("test/items.yml");
    }

}