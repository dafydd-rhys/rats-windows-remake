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

    private static final ArrayList<Item> items = new ArrayList<>();

    public static void addItem(Item item) {
        items.add(item);
    }

    public static void removeItem(Item item) {
        items.remove(item);
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    static int getAmount(Item.TYPE type) {
        int count = 0;
        for (Item item : items) {
            if (item != null) {
                if (item.getType() == type) {
                    count++;
                }
            }
        }

        return count;
    }

}
