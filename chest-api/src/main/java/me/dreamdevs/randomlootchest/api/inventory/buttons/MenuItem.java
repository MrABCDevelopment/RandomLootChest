package me.dreamdevs.randomlootchest.api.inventory.buttons;

import lombok.Getter;
import lombok.Setter;
import me.dreamdevs.randomlootchest.api.events.ItemClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MenuItem {

	private String displayName;
	private ItemStack icon;
	private List<String> lore;

	public MenuItem(String displayName, ItemStack icon, String... lore) {
		this.displayName = displayName;
		this.icon = icon;
		this.lore = new ArrayList<>();
		this.lore.addAll(List.of(lore));
	}

	public ItemStack getFinalIcon() {
		return setNameAndLore(getIcon().clone(), getDisplayName(), getLore());
	}

	public void onItemClick(ItemClickEvent event) {
		// Do nothing by default
	}

	public static ItemStack setNameAndLore(ItemStack itemStack, String displayName, List<String> lore) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
		return itemStack;
	}
}