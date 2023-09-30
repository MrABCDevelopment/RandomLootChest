package me.dreamdevs.randomlootchest.hooks;

import me.dreamdevs.randomlootchest.api.utils.Util;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MMOItemsHook {

	public static MMOItemsHook INSTANCE;

	public MMOItemsHook() {
		INSTANCE = this;
		Util.sendPluginMessage("&aHooked into MMOItems!");
	}

	public ItemStack getItemStack(@NotNull String type, @NotNull String itemId) {
		MMOItem mmoItem = MMOItems.plugin.getMMOItem(Type.get(type), itemId);
		return Optional.ofNullable(mmoItem).map(mmoItem1 -> mmoItem1.newBuilder().build()).orElse(null);
	}

}