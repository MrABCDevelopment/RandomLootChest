package me.dreamdevs.github.randomlootchest.commands.subcommands;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.commands.ArgumentCommand;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import me.dreamdevs.github.randomlootchest.api.inventory.GItem;
import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;
import me.dreamdevs.github.randomlootchest.api.inventory.actions.CloseAction;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ExtensionsSubCommand implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("not-player"));
            return false;
        }
        Player player = (Player) commandSender;
        GUI extensionsGui = new GUI("&2&lExtensions Menu", GUISize.SIX_ROWS);
        int x = 0;
        for(Extension extension : RandomLootChestMain.getInstance().getExtensionManager().getEnabledExtensions()) {
            GItem gItem = new GItem(Material.PAPER, extension.getDescription().getExtensionName() + " - " + extension.getDescription().getExtensionVersion(), new ArrayList<>());
            gItem.addActions(new CloseAction(), event -> {
                extension.reloadConfig();
                event.getPlayer().sendMessage(ColourUtil.colorize("&aYou reloaded config of extension: "+extension.getDescription().getExtensionName()));
            });
            extensionsGui.setItem(x, gItem);
            x++;
        }
        extensionsGui.openGUI(player);
        return true;
    }

    @Override
    public String getHelpText() {
        return "&6/randomlootchest extensions - opens extensions menu";
    }

    @Override
    public String getPermission() {
        return "randomlootchest.admin.extensions";
    }
}