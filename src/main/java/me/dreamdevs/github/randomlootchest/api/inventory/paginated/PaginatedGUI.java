package me.dreamdevs.github.randomlootchest.api.inventory.paginated;

import me.dreamdevs.github.randomlootchest.api.inventory.GUI;
import me.dreamdevs.github.randomlootchest.api.inventory.GUISize;

public class PaginatedGUI extends GUI {

    private int page = 1;

    public PaginatedGUI(String title, GUISize guiSize) {
        super(title, guiSize);
        int maxPages = getMaxPages();
    }

    public int getMaxPages() {
        return Math.max(1, (int) Math.ceil((getItemStacks().length) / 21/));
    }

}