package me.dreamdevs.github.randomlootchest.menus;

import me.dreamdevs.github.randomlootchest.api.inventory.GItem;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;
import me.dreamdevs.github.randomlootchest.objects.ChestGame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChestCreateMenu {

    public ChestCreateMenu(Player player, ChestGame chestGame) {
        GUI gui = new GUI("Create GUI: "+chestGame.getId(), GUISize.TWO_ROWS);
        GItem setCooldown = new GItem(Material.STICK, ChatColor.GOLD+"Current Time: "+chestGame.getTime(), new ArrayList<>());
    }

}