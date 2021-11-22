package main.level;

import entity.weapon.Bomb;
import entity.weapon.DeathRat;

import java.util.HashMap;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */

public class Inventory {

    HashMap<String, Integer> inventoryList = new HashMap<>();

    //Initialize inventory with items at start of a level
    public void initializeInventory() {
        inventoryList.put("bomb", 1);
        inventoryList.put("deathRat", 1);
    }

    public static void createItem(String itemType, int x, int y) {
        switch (itemType) {
            case "bomb":
                Bomb bomb = new Bomb(x, y);
                break;
            case "deathRat":
                DeathRat deathRat = new DeathRat(x, y);
                break;
        }

    }

    public void removeItem(String itemType) {
        inventoryList.replace(itemType, inventoryList.get(itemType) - 1);
    }


}
