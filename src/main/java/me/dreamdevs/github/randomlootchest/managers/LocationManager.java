package me.dreamdevs.github.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.Util;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class LocationManager {

    private Map<String, String> locations;

    private static final String fileName = "locations.yml";

    public LocationManager() {
        locations = new HashMap<>();
        FileConfiguration config = RandomLootChestMain.getInstance().getConfigManager().getConfig(fileName);
        for(String string : config.getStringList("locations")) {
            String[] splits = string.split(";");
            locations.put(splits[0], splits[1]);
        }
    }

    public void addLocation(String type, Location location) {
        locations.put(Util.getLocationString(location), type);
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
        RandomLootChestMain.getInstance().getConfigManager().getConfig(fileName).set("locations", arrayList);
        RandomLootChestMain.getInstance().getConfigManager().save(fileName);
        RandomLootChestMain.getInstance().getConfigManager().reload(fileName);
    }

    public Map<String, String> getLocations() {
        return locations;
    }

}