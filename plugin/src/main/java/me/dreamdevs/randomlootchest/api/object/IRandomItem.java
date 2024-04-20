package me.dreamdevs.randomlootchest.api.object;

import org.bukkit.inventory.ItemStack;

public interface IRandomItem {

    ItemStack getItemStack();

    double getChance();

    boolean isRandomDropAmount();

}