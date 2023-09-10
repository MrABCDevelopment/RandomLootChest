package me.dreamdevs.randomlootchest.commands.subcommands;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.randomlootchest.objects.RandomItem;
import me.dreamdevs.randomlootchest.utils.ColourUtil;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
            return false;
        }
        Player player = (Player)commandSender;
        if(args.length == 1) {
         //   new ItemsMenu().open(player);
            return true;
        } else if(args.length > 1) {
            if(args[1].equalsIgnoreCase("add")) {
                String id = args[2];
                if(id == null) {
                    player.sendMessage(ColourUtil.colorize("&cNo id for new item..."));
                    return true;
                }

                Double chance = Double.parseDouble(args[3]);
                if(chance == null) {
                    player.sendMessage(ColourUtil.colorize("&cYou have to set chance!"));
                    return true;
                }

                if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    player.sendMessage(ColourUtil.colorize("&cYou cannot add air to the items..."));
                    return true;
                }
                RandomItem randomItem = new RandomItem(player.getInventory().getItemInMainHand(), chance);
                RandomLootChestMain.getInstance().getItemsManager().getItems().put(id, randomItem);
                player.sendMessage(ColourUtil.colorize("&aYou added new item to items!"));
                return true;
            } else if(args[1].equalsIgnoreCase("remove")) {

                return true;
            } else {
                player.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-argument"));
                return true;
            }
        } else {
            player.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessage("no-argument"));
        }
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest items [add/remove] [id] [chance] - opens items menu or adds/removes an item with specific id";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.items";
    }

    @Override
    public List<String> getArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.add("add");
        return arguments;
    }
}