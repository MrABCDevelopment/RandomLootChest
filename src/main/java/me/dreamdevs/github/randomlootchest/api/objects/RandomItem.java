package me.dreamdevs.github.randomlootchest.api.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter @AllArgsConstructor
public class RandomItem {

    private ItemStack itemStack;
    private double chance;

}