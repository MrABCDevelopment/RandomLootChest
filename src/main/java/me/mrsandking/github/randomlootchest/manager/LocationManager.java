package me.mrsandking.github.randomlootchest.manager;

import lombok.Getter;
import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.util.Util;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class LocationManager {

    private HashMap<String, String> locations;
    private RandomLootChestMain plugin;

    public LocationManager(RandomLootChestMain plugin) {
        locations = new HashMap<>();
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfigManager().getConfig("locations.yml");
        for(String string : config.getStringList("locations")) {
            String[] splits = string.split(";");
            locations.put(splits[0], splits[1]);
        }
    }

    public void addLocation(String type, Location location) {
        locations.put(Util.getLocationString(location), type); //locations.add(Util.getLocationString(location));
    }

    public void removeLocation(Location location) {
        locations.remove(Util.getLocationString(location));
    }

    public void save() {
        if(getLocations().isEmpty()) return;
        ArrayList<String> arrayList = new ArrayList<>();
        for(Map.Entry<String, String> map : getLocations().entrySet()) {
            arrayList.add(map.getKey()+";"+map.getValue());
        }
        plugin.getConfigManager().getConfig("locations.yml").set("locations", arrayList);
        plugin.getConfigManager().save("locations.yml");
        plugin.getConfigManager().reload("locations.yml");
    }

    public HashMap<String, String> getLocations() {
        return locations;
    }

}