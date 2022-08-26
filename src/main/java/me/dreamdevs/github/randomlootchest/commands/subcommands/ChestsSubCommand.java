package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.commands.ArgumentCommand;
import me.dreamdevs.github.randomlootchest.api.inventory.GItem;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;
import me.dreamdevs.github.randomlootchest.menus.ChestsInfoMenu;
import me.dreamdevs.github.randomlootchest.objects.ChestGame;
import me.dreamdevs.github.randomlootchest.objects.RandomItem;
import me.dreamdevs.github.randomlootchest.utils.TimeUtil;
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
        new ChestsInfoMenu(player);
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