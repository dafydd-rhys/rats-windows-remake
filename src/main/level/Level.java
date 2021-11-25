package main.level;

import entity.rat.Rat;
import entity.Item;
import java.util.ArrayList;
import java.util.HashMap;
import tile.Tile;

/**
 * Level
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Dawid Wisniewski
 */
public class Level {

    public static int currentLevel;
    public static int cols;
    public static int rows;
    private static HashMap<Item.TYPE, Integer> timeToGenerate;
    private static Tile[][] tiles;
    private static ArrayList<Rat> rats;
    private static ArrayList<Item> items;
    private static int expectedTime;
    private static int maxRats;

    public Level(HashMap<Item.TYPE, Integer> timeToGenerate, int expectedTime, int maxRats,
                 Tile[][] tiles, ArrayList<Rat> rats) {
        Level.tiles = tiles;
        Level.rats = rats;
        Level.timeToGenerate = timeToGenerate;
        Level.expectedTime = expectedTime;
        Level.maxRats = maxRats;
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

    public static HashMap<Item.TYPE, Integer> getTimeToGenerate() {
        return timeToGenerate;
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

    public static int getMaxRats() {
        return maxRats;
    }

    public static int getExpectedTime() {
        return expectedTime;
    }

}
