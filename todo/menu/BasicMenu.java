package menu;

public class BasicMenu extends Menu {

    public BasicMenu(String title, MenuSize menuSize, boolean protect) {
        super(title, menuSize, protect);
    }

    @Override
    public void setItems() {
        for(int x = 0; x<getSize(); x++)
            if(getMenuItems()[x] != null)
                getInventory().setItem(x, getMenuItems()[x].getItemStack());
    }
}