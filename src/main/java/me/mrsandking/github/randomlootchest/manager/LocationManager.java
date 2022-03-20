package me.mrsandking.github.randomlootchest.manager;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class LocationManager {

    private ArrayList<String> locations;
    private RandomLootChestMain plugin;

    public LocationManager(RandomLootChestMain plugin) {
        locations = new ArrayList<>();
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfigManager().getConfig("locations.yml");
        locations.addAll(config.getStringList("locations"));
    }

    public boolean addLocation(Location location) {
        return locations.add(Util.getLocationString(location));
    }

    public boolean removeLocation(Location location) {
        return locations.remove(Util.getLocationString(location));
    }

    public void save() {
        if(getLocations().isEmpty()) return;
        plugin.getConfigManager().getConfig("locations.yml").set("locations", getLocations());
        plugin.getConfigManager().save("locations.yml");
        plugin.getConfigManager().reload("locations.yml");
    }

    public ArrayList<String> getLocations() {
        return locations;
    }
}