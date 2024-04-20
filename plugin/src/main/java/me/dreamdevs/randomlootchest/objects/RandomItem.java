package me.dreamdevs.randomlootchest.objects;

import lombok.AllArgsConstructor;
import me.dreamdevs.randomlootchest.api.object.IRandomItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RandomItem implements IRandomItem {

    private ItemStack itemStack;
    private double chance;
    private boolean randomDropAmount;

    public RandomItem(ItemStack itemStack, double chance) {
        this.itemStack = itemStack;
        this.chance = chance;
        this.randomDropAmount = false;
    }

    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public double getChance() {
        return this.chance;
    }

    @Override
    public boolean isRandomDropAmount() {
        return randomDropAmount;
    }

    public void setRandomDropAmount(boolean randomDropAmount) {
        this.randomDropAmount = randomDropAmount;
    }

    public String getDisplayName() {
        return (itemStack.hasItemMeta() && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName())
                ? itemStack.getItemMeta().getDisplayName() : itemStack.getType().name();
    }

    public List<String> getLore() {
        return (itemStack.hasItemMeta() && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasLore())
                ? itemStack.getItemMeta().getLore() : new ArrayList<>();
    }
}