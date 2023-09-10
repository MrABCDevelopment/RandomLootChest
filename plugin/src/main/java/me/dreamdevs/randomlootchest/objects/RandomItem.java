package me.dreamdevs.randomlootchest.objects;

import lombok.AllArgsConstructor;
import me.dreamdevs.randomlootchest.api.objects.IRandomItem;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class RandomItem implements IRandomItem {

    private ItemStack itemStack;
    private double chance;

    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public double getChance() {
        return this.chance;
    }
}