package player.Inventory;

import entity.Item;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ItemLoader {

    private final AnchorPane abilities;
    private ArrayList<Item.TYPE> items;

    public ItemLoader(ArrayList<Item.TYPE> items, AnchorPane abilities) {
        this.items = items;
        this.abilities = abilities;

        load();
    }

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

    private void enableItem(Item.TYPE type) {
        Inventory.enableItem(type, abilities);
    }

}
