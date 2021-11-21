package main.level;

import entity.weapon.Item;
import tile.Tile;

/**
 * LevelFileGenerator
 *
 * @author Dawid Wisniewski
 */
public class Level {

    protected void placeItem(Item selectedItem, Tile tile) {
        if (tile.getClass().getName().equals("PathTile")) {
            tile.getEntitiesOnTile().add(selectedItem);
        } else {
            System.out.println("Invalid placement");
        }
    }

}
