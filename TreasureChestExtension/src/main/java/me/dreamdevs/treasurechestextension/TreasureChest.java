package me.dreamdevs.treasurechestextension;

import me.dreamdevs.github.randomlootchest.api.IChestGame;
import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.api.objects.RandomMoney;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TreasureChest implements IChestGame {

    @Override
    public String getId() {
        return "treasurechest";
    }

    @Override
    public String getTitle() {
        return "&6&lTreasure Chest";
    }

    @Override
    public long getTime() {
        return 0;
    }

    @Override
    public int getMaxItems() {
        return 3;
    }

    @Override
    public int getMaxItemsInTheSameType() {
        return 1;
    }

    @Override
    public RandomMoney getMoney() {
        return null;
    }

    @Override
    public File getChestFile() {
        return null;
    }

    @Override
    public String getParticleType() {
        return null;
    }

    @Override
    public int getParticleAmount() {
        return 0;
    }

    @Override
    public boolean isParticleUse() {
        return false;
    }

    @Override
    public List<RandomItem> getItems() {
        List<RandomItem> randomItems = new ArrayList<>();
        randomItems.add(new RandomItem(new ItemStack(Material.IRON_SWORD), 0.5));
        randomItems.add(new RandomItem(new ItemStack(Material.IRON_INGOT, 16), 0.5));
        randomItems.add(new RandomItem(new ItemStack(Material.IRON_INGOT, 32), 0.4));
        randomItems.add(new RandomItem(new ItemStack(Material.IRON_INGOT, 48), 0.3));
        randomItems.add(new RandomItem(new ItemStack(Material.IRON_INGOT, 64), 0.2));
        randomItems.add(new RandomItem(new ItemStack(Material.GOLDEN_APPLE), 0.7));
        return randomItems;
    }

}