package me.dreamdevs.treasurechestextension;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import me.dreamdevs.github.randomlootchest.utils.Util;
import me.dreamdevs.treasurechestextension.listeners.PlayerBreakListener;

public class ExtensionMain extends Extension {

    private @Getter static ExtensionMain extensionMain;
    public static double CHANCE;

    @Override
    public void onExtensionEnable() {
        extensionMain = this;
        saveDefaultConfig();

        CHANCE = getConfig().getDouble("Chance");
        registerListener(new PlayerBreakListener());
        RandomLootChestMain.getInstance().getChestsManager().registerChest(TreasureChest.class, false);
        Util.sendPluginMessage("&aLoaded "+getDescription().getExtensionName()+" "+getDescription().getExtensionVersion());
    }

    @Override
    public void onExtensionDisable() {

    }

}