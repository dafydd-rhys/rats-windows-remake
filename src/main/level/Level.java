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
 * @author Maurice Petersen (2013396)
 */
public final class Level {

    public static int currentLevel;
    public static int cols;
    public static int rows;
    private static HashMap<Item.TYPE, Integer> timeToGenerate;
    private static Tile[][] tiles;
    private static ArrayList<Rat> rats = new ArrayList<>();
    private static ArrayList<Item> items = new ArrayList<>();
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

    public static void placeItem(Item item, Tile tile) {
        item.setCurrentPosX(tile.getX());
        item.setCurrentPosY(tile.getY());
        tile.addEntityToTile(item);
        items.add(item);
    }

    public static void placeRat(Rat rat, Tile tile) {
        rat.setCurrentPosX(tile.getX());
        rat.setCurrentPosY(tile.getY());
        tile.addEntityToTile(rat);
        rats.add(rat);
    }

    public static void getEntitiesOnTile() {
        for (Tile[] tileY : tiles) {
            for (Tile tileX : tileY) {
                System.out.println(tileX.getEntitiesOnTile());
            }
        }
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static HashMap<Item.TYPE, Integer> getTimeToGenerate() {
        return timeToGenerate;
    }

    public static Tile[][] getTiles() {
        return tiles;
    }

    public static int getCols() {
        return cols;
    }

    public static int getRows() {
        return rows;
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
