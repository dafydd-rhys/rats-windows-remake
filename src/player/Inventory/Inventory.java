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

    public static int getBombAmount() {
        return getAmount(Item.TYPE.BOMB);
    }

    public static int getGasAmount() {
        return getAmount(Item.TYPE.GAS);
    }

    public static int getDeathRatAmount() {
        return getAmount(Item.TYPE.DEATH_RAT);
    }

    public static int getFemaleChangeAmount() {
        return getAmount(Item.TYPE.FEMALE_CHANGE);
    }

    public static int getMaleChangeAmount() {
        return getAmount(Item.TYPE.MALE_CHANGE);
    }

    public static int getNoEntryAmount() {
        return getAmount(Item.TYPE.NO_ENTRY);
    }

    public static int getPoisonAmount() {
        return getAmount(Item.TYPE.POISON);
    }

    public static int getSterilisationAmount() {
        return getAmount(Item.TYPE.STERILISATION);
    }

    public static boolean maxAbilities() {
        return getItems().size() == 32;
    }

    private static int getAmount(Item.TYPE type) {
        int count = 0;
        for (Item item : items) {
            if (item.getType() == type) {
                count++;
            }
        }

        return count;
    }

}
