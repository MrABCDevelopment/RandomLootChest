package me.dreamdevs.randomlootchest.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.utils.Util;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class LocationManager {

    private final Map<String, String> locations;
    private static final String FILENAME = "locations.yml";

    public LocationManager() {
        locations = new HashMap<>();
        FileConfiguration config = RandomLootChestMain.getInstance().getConfigManager().getConfig(FILENAME);
        for(String string : config.getStringList("locations")) {
            String[] splits = string.split(";");
            locations.put(splits[0], splits[1]);
        }
    }

    public void addLocation(@NotNull String type, @NotNull Location location) {
        locations.put(Util.getLocationString(location), type);
    }

    public void removeLocation(@NotNull Location location) {
        locations.remove(Util.getLocationString(location));
    }

    public void save() {
        if(getLocations().isEmpty()) return;
        ArrayList<String> arrayList = new ArrayList<>();
        for(Map.Entry<String, String> map : getLocations().entrySet()) {
            arrayList.add(map.getKey()+";"+map.getValue());
        }
        RandomLootChestMain.getInstance().getConfigManager().getConfig(FILENAME).set("locations", arrayList);
        RandomLootChestMain.getInstance().getConfigManager().save(FILENAME);
        RandomLootChestMain.getInstance().getConfigManager().reload(FILENAME);
    }

}