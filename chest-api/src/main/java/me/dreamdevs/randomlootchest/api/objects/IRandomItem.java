package me.dreamdevs.randomlootchest.api.objects;

import org.bukkit.inventory.ItemStack;

public interface IRandomItem {

    ItemStack getItemStack();

    double getChance();

}