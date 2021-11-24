package player.Inventory;

import entity.Item;
import java.util.ArrayList;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */
public class Inventory {

    private static ArrayList<Item> items = new ArrayList<>();

    public static void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

}
