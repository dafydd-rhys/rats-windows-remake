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
    public static boolean isSave = false;

    private Level level;
    private final HashMap<Item.TYPE, Integer> timeToGenerate;
    private final ArrayList<Rat> rats;
    private ArrayList<Item> items;
    private final Tile[][] tiles;

    private static int score;
    private int cols;
    private int rows;
    private int expectedTime;
    private int maxRats;
    private static boolean gameOver = false;
    private static boolean gameWon = false;
    private int currentTick;

    public Level(HashMap<Item.TYPE, Integer> timeToGenerate, int expectedTime, int maxRats, Tile[][] tiles,
                 ArrayList<Rat> rats, int sizeY, int sizeX) {
        this.tiles = tiles;
        this.rats = rats;
        this.timeToGenerate = timeToGenerate;
        this.expectedTime = expectedTime;
        this.maxRats = maxRats;
        this.items = new ArrayList<>();
        this.cols = sizeX;
        this.rows = sizeY;
        this.level = this;
        this.currentTick = 0;
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

    public static void setGameOver(boolean gameOver) {
        Level.gameOver = gameOver;
    }

    public static void setGameWon(boolean gameWon) {
        Level.gameWon = gameWon;
    }

    public static boolean getGameWon() {
        return gameWon;
    }

    public static boolean getGameOver() {
        return gameOver;
    }

    public void setScore(int score) {
        Level.score = score;
    }

    public static int getScore() {
        return score;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public static void setCurrentLevel(int currentLevel) {
        Level.currentLevel = currentLevel;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setIsSave(boolean isSave) {
        Level.isSave = isSave;
    }

    public static boolean isSave() {
        return Level.isSave;
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

    public void setItems(ArrayList<Item> items) {
        this.items = items;
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

    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

    public int getCurrentTick() {
        return currentTick;
    }
}
