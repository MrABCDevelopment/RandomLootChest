package me.mrsandking.github.randomlootchest.commands.subcommands;

import me.mrsandking.github.randomlootchest.RandomLootChestMain;
import me.mrsandking.github.randomlootchest.commands.ArgumentCommand;
import me.mrsandking.github.randomlootchest.inventory.GItem;
import me.mrsandking.github.randomlootchest.inventory.GUI;
import me.mrsandking.github.randomlootchest.inventory.GUISize;
import me.mrsandking.github.randomlootchest.objects.ChestGame;
import me.mrsandking.github.randomlootchest.objects.RandomItem;
import me.mrsandking.github.randomlootchest.util.TimeUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChestsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
            return false;
        }
        Player player = (Player) commandSender;
        if(RandomLootChestMain.getInstance().getChestsManager().getChests().isEmpty()) {
            player.sendMessage(ChatColor.RED+"Couldn't find any chest.");
            return false;
        }
        GUI gui = new GUI("&6&lChests", GUISize.sizeOf(RandomLootChestMain.getInstance().getChestsManager().getChests().size()));
        for(Map.Entry<String, ChestGame> mapEntry : RandomLootChestMain.getInstance().getChestsManager().getChests().entrySet()) {
            List<String> strings = new ArrayList<>();
            strings.add(ChatColor.GOLD+"» Cooldown: "+ ChatColor.YELLOW+TimeUtil.formattedTime(mapEntry.getValue().getTime()));
            strings.add(ChatColor.GOLD+"» Max Items: "+ ChatColor.YELLOW+mapEntry.getValue().getMaxItems());
            strings.add(ChatColor.GOLD+"» Max Items In The Same Type: "+ ChatColor.YELLOW+mapEntry.getValue().getMaxItemsInTheSameType());
            strings.add(ChatColor.GOLD+"» Items: ("+mapEntry.getValue().getItems().size()+")");
            for(RandomItem randomItem : mapEntry.getValue().getItems()) {
                strings.add(ChatColor.GOLD+"➢ "+ChatColor.YELLOW+randomItem.getItemStack().getType().toString() + " - "+(randomItem.getChance()*100)+"%");
            }
            GItem gItem = new GItem(Material.CHEST, mapEntry.getValue().getTitle(), strings);
            gui.addItem(gItem);
        }
        gui.openGUI(player);
        return true;
    }

    @Override
    public String getHelpText() {
        return ChatColor.GOLD+"/randomlootchest chests - opens inventory with chests and their informations";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.chests";
    }
}