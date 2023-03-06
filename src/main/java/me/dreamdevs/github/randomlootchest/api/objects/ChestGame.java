package me.dreamdevs.github.randomlootchest.api.objects;

import lombok.Getter;
import lombok.Setter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ChestGame {

    private String id;
    private String title;
    private long time;
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
            } catch (Exception e) {

            }
        }
    }

    public void delete() {
        chestFile.delete();
        RandomLootChestMain.getInstance().getChestsManager().getChests().remove(id);
        Util.sendPluginMessage("&aDeleted chest with ID: "+id);
    }

}