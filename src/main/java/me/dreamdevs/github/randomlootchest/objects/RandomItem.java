package me.dreamdevs.github.randomlootchest.objects;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class RandomItem {

    private ItemStack itemStack;
    private double chance;

    public RandomItem(ItemStack itemStack, double chance) {
        this.itemStack = itemStack;
        this.chance = chance;
    }

}