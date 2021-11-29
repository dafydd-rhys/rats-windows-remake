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

    private Level level;
    private final HashMap<Item.TYPE, Integer> timeToGenerate;
    private final ArrayList<Rat> rats;
    private final ArrayList<Item> items;
    private final Tile[][] tiles;

    private int score;
    private int cols;
    private int rows;
    private int expectedTime;
    private int maxRats;

    public Level(HashMap<Item.TYPE, Integer> timeToGenerate, int expectedTime, int maxRats,
                 Tile[][] tiles, ArrayList<Rat> rats) {
        this.tiles = tiles;
        this.rats = rats;
        this.timeToGenerate = timeToGenerate;
        this.expectedTime = expectedTime;
        this.maxRats = maxRats;
        this.items = new ArrayList<>();
        this.level = this;
    }

    public void placeItem(Item item, Tile tile) {
        item.setCurrentPosX(tile.getX());
        item.setCurrentPosY(tile.getY());
        tile.addEntityToTile(item);
        items.add(item);
    }

    public void placeRat(Rat rat, Tile tile) {
        rat.setCurrentPosX(tile.getX());
        rat.setCurrentPosY(tile.getY());
        tile.addEntityToTile(rat);
        rats.add(rat);
    }

    public void getEntitiesOnTile() {
        for (Tile[] tileY : this.tiles) {
            for (Tile tileX : tileY) {
                System.out.println(tileX.getEntitiesOnTile());
            }
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public void setCurrentLevel(int currentLevel) {
        Level.currentLevel = currentLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public HashMap<Item.TYPE, Integer> getTimeToGenerate() {
        return timeToGenerate;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getCols() {
        return cols;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    public ArrayList<Rat> getRats() {
        return rats;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setMaxRats(int maxRats) {
        this.maxRats = maxRats;
    }

    public int getMaxRats() {
        return maxRats;
    }

    public void setExpectedTime(int expectedTime) {
        this.expectedTime = expectedTime;
    }

    public int getExpectedTime() {
        return expectedTime;
    }

}
