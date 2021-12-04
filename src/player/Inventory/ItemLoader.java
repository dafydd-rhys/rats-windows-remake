package player.Inventory;

import entity.Item;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;

/**
 * ItemLoader.java
 * <p>
 * Loads the inventory items from save file.
 *
 * @param items items in inventory
 * @param abilities ability pane
 * @author Maurice Petersen (2013396).
 */
public record ItemLoader(ArrayList<Item.TYPE> items, AnchorPane abilities) {

    /**
     * loads all items in save.
     *
     * @param items items list
     * @param abilities inventory pane
     */
    public ItemLoader(final ArrayList<Item.TYPE> items, final AnchorPane abilities) {
        this.items = items;
        this.abilities = abilities;

        load();
    }


    /**
     * loads all items in save.
     */
    public void load() {
        for (Item.TYPE item : items) {
            if (item == Item.TYPE.BOMB) {
                enableItem(Item.TYPE.BOMB);
            } else if (item == Item.TYPE.DEATH_RAT) {
                enableItem(Item.TYPE.DEATH_RAT);
            } else if (item == Item.TYPE.FEMALE_CHANGE) {
                enableItem(Item.TYPE.FEMALE_CHANGE);
            } else if (item == Item.TYPE.GAS) {
                enableItem(Item.TYPE.GAS);
            } else if (item == Item.TYPE.MALE_CHANGE) {
                enableItem(Item.TYPE.MALE_CHANGE);
            } else if (item == Item.TYPE.NO_ENTRY) {
                enableItem(Item.TYPE.NO_ENTRY);
            } else if (item == Item.TYPE.POISON) {
                enableItem(Item.TYPE.POISON);
            } else if (item == Item.TYPE.STERILISATION) {
                enableItem(Item.TYPE.STERILISATION);
            }
        }
    }

    /**
     * Enables item.
     *
     * @param type type of item
     */
    private void enableItem(final Item.TYPE type) {
        Inventory.enableItem(type, abilities);
    }

}
