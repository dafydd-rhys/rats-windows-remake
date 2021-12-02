package main.level;

import entity.rat.Rat;
import entity.Item;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.ProgressBar;
import tile.Tile;

/**
 * Level
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Maurice Petersen (2013396)
 */
public final class Level {

    /** The constant currentLevel. */
    public static int currentLevel;
    /** */
    private Level level;
    /** */
    private final HashMap<Item.TYPE, Integer> timeToGenerate;
    /** */
    private final ArrayList<Rat> rats;
    /** */
    private final ArrayList<Item> items;
    /** */
    private final Tile[][] tiles;
    /** */
    private static int score;
    /** */
    private int cols;
    /** */
    private int rows;
    /** */
    private int expectedTime;
    /** */
    private int maxRats;
    /** */
    private static boolean gameOver = false;
    /** */
    private static boolean gameWon = false;
    private static boolean paused = false;

    /**
     * Instantiates a new Level.
     *
     * @param timeToGenerate the time to generate
     * @param expectedTime   the expected time
     * @param maxRats        the max rats
     * @param tiles          the tiles
     * @param rats           the rats
     * @param sizeY          the size y
     * @param sizeX          the size x
     */
    public Level(HashMap<Item.TYPE, Integer> timeToGenerate, int expectedTime, int maxRats, Tile[][] tiles,
                 ArrayList<Rat> rats, int sizeY, int sizeX) {
        this.items = new ArrayList<>();
        this.tiles = tiles;
        this.rats = rats;
        this.timeToGenerate = timeToGenerate;
        this.expectedTime = expectedTime;
        this.maxRats = maxRats;
        this.cols = sizeX;
        this.rows = sizeY;
        this.level = this;
    }

    /**
     * Place item.
     *
     * @param item the item
     * @param tile the tile
     */
    public void placeItem(Item item, Tile tile) {
        item.setCurrentPosX(tile.getX());
        item.setCurrentPosY(tile.getY());
        tile.addEntityToTile(item);
        items.add(item);
    }

    /**
     * Place rat.
     *
     * @param rat  the rat
     * @param tile the tile
     */
    public void placeRat(Rat rat, Tile tile) {
        rat.setCurrentPosX(tile.getX());
        rat.setCurrentPosY(tile.getY());
        tile.addEntityToTile(rat);
        rats.add(rat);
    }

    /**
     * Gets entities on tile.
     */
    public void getEntitiesOnTile() {
        for (Tile[] tileY : this.tiles) {
            for (Tile tileX : tileY) {
                System.out.println(tileX.getEntitiesOnTile());
            }
        }
    }

    /**
     * Sets game over.
     *
     * @param gameOver the game over
     */
    public static void setGameOver(boolean gameOver) {
        Level.gameOver = gameOver;
    }

    /**
     * Sets game won.
     *
     * @param gameWon the game won
     */
    public static void setGameWon(boolean gameWon) {
        Level.gameWon = gameWon;
    }

    /**
     * Gets game won.
     *
     * @return the game won
     */
    public static boolean getGameWon() {
        return gameWon;
    }

    /**
     * Gets game over.
     *
     * @return the game over
     */
    public static boolean getGameOver() {
        return gameOver;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        Level.score = score;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public static int getScore() {
        return score;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Sets current level.
     *
     * @param currentLevel the current level
     */
    public static void setCurrentLevel(int currentLevel) {
        Level.currentLevel = currentLevel;
    }

    /**
     * Gets current level.
     *
     * @return the current level
     */
    public static int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Gets time to generate.
     *
     * @return the time to generate
     */
    public HashMap<Item.TYPE, Integer> getTimeToGenerate() {
        return timeToGenerate;
    }

    /**
     * Get tiles tile [ ] [ ].
     *
     * @return the tile [ ] [ ]
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Sets cols.
     *
     * @param cols the cols
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Gets cols.
     *
     * @return the cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets rows.
     *
     * @param rows the rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Gets rows.
     *
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets rats.
     *
     * @return the rats
     */
    public ArrayList<Rat> getRats() {
        return rats;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Sets max rats.
     *
     * @param maxRats the max rats
     */
    public void setMaxRats(int maxRats) {
        this.maxRats = maxRats;
    }

    /**
     * Gets max rats.
     *
     * @return the max rats
     */
    public int getMaxRats() {
        return maxRats;
    }

    /**
     * Sets expected time.
     *
     * @param expectedTime the expected time
     */
    public void setExpectedTime(int expectedTime) {
        this.expectedTime = expectedTime;
    }

    /**
     * Gets expected time.
     *
     * @return the expected time
     */
    public int getExpectedTime() {
        return expectedTime;
    }

    public static void setPaused(boolean paused) {
        Level.paused = paused;
    }

    public static boolean getPaused() {
        return !paused;
    }

}
