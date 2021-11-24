package main.level;

import entity.Rat;
import entity.Item;
import java.util.ArrayList;
import tile.Tile;

/**
 * LevelFileGenerator
 *
 * @author Dawid Wisniewski
 */
public class Level {

    private static Tile[][] tiles;
    private static ArrayList<Rat> rats;
    private static ArrayList<Item> items;

    public Level(Tile[][] tiles, ArrayList<Rat> rats) {
        Level.tiles = tiles;
        Level.rats = rats;
        items = new ArrayList<>();
    }

    protected void placeItem(Item selectedItem, Tile tile) {
        if (tile.getClass().getName().equals("PathTile")) {
            tile.getEntitiesOnTile().add(selectedItem);
        } else {
            System.out.println("Invalid placement");
        }
    }

    public static void getEntitiesOnTile() {
        for (Tile[] tileY : tiles) {
            for (Tile tileX : tileY) {
                System.out.println(tileX.getEntitiesOnTile());
            }
        }
    }
    
    public static Tile[][] getTiles() {
        return tiles;
    }

    public static ArrayList<Rat> getRats() {
        return rats;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

}
