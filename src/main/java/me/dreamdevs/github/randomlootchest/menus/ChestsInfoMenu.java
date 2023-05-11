package me.dreamdevs.github.randomlootchest.menus;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.inventory.GItem;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;
import me.dreamdevs.github.randomlootchest.api.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.utils.TimeUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChestsInfoMenu {

    public ChestsInfoMenu(Player player) {
        GUI gui = new GUI("&6&lChests", GUISize.sizeOf(RandomLootChestMain.getInstance().getChestsManager().getChests().size()));
        int x = 0;
        for(Map.Entry<String, ChestGame> mapEntry : RandomLootChestMain.getInstance().getChestsManager().getChests().entrySet()) {
            List<String> strings = new ArrayList<>();
            strings.add(ChatColor.GOLD+"» Cooldown: "+ ChatColor.YELLOW+ TimeUtil.formattedTime(mapEntry.getValue().getTime()));
            strings.add(ChatColor.GOLD+"» Max Items: "+ ChatColor.YELLOW+mapEntry.getValue().getMaxItems());
            strings.add(ChatColor.GOLD+"» Max Items In The Same Type: "+ ChatColor.YELLOW+mapEntry.getValue().getMaxItemsInTheSameType());
            strings.add(ChatColor.GOLD+"» Use Particle: "+ ChatColor.YELLOW+((mapEntry.getValue().isParticleUse()) ? "on" : "off"));
            strings.add(ChatColor.GOLD+"» Particle Type: "+ ChatColor.YELLOW+((mapEntry.getValue().getParticleType() != null) ? mapEntry.getValue().getParticleType() : "none"));
            strings.add(ChatColor.GOLD+"» Particle Amount: "+ ChatColor.YELLOW+mapEntry.getValue().getParticleAmount());
            strings.add(ChatColor.GOLD+"» Items: ("+mapEntry.getValue().getItems().size()+")");
            for(RandomItem randomItem : mapEntry.getValue().getItems()) {
                strings.add(ChatColor.GOLD+"➢ "+ChatColor.YELLOW+randomItem.getItemStack().getType().toString() + " - "+(randomItem.getChance()*100)+"%");
            }
            GItem gItem = new GItem(Material.CHEST, mapEntry.getValue().getTitle(), strings);
            gui.setItem(x, gItem);
            x++;
        }
        gui.openGUI(player);
    }

}