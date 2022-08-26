package me.dreamdevs.github.randomlootchest.objects;

import lombok.Getter;
import lombok.Setter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.ConsoleUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class ChestGame {

    private String id;
    private String title;
    private int time;
    private int maxItems;
    private int maxItemsInTheSameType;
    private RandomMoney money;
    private File chestFile;
    private String particleType;
    private int particleAmount;
    private boolean particleUse;
    private List<RandomItem> items = new ArrayList<>();

    public ChestGame(String id) {
        this.id = id;
        this.chestFile = new File(RandomLootChestMain.getInstance().getDataFolder(), "chests/"+id+".yml");
        if(!chestFile.exists()) {
            try {
                chestFile.createNewFile();
            } catch (IOException e) {
                ConsoleUtil.sendPluginMessage("&cError while creating chest file...");
            }
        }
        ConsoleUtil.sendPluginMessage("&aLoaded chest with ID: "+id);
    }

    public void delete() {
        chestFile.delete();
        RandomLootChestMain.getInstance().getChestsManager().getChests().remove(id);
        ConsoleUtil.sendPluginMessage("&aDeleted chest with ID: "+id);
    }

}