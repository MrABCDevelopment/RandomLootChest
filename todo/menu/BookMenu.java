package menu;

public abstract class BookMenu extends Menu {

    public int page = 0;
    public int index = 0;

    public BookMenu(String title) {
        super(title, MenuSize.SIX_ROWS, true);
    }

    public void setupMenuNavigation() {
        for(int x = 45; x<54; x++)
            setItem(x, new StaticMenuItem());

        setItem(50, new NextPageMenuItem());
        setItem(48, new ReturnPageMenuItem());

        for(int x = 0; x<getSize(); x++)
            if(getMenuItems()[x] != null)
                getInventory().setItem(x, getMenuItems()[x].getItemStack());
    }

}