package main.level;

import entity.rat.Rat;
import entity.Item;
import java.util.ArrayList;
import java.util.HashMap;
import tile.Tile;

/**
 * This class represents the Level.
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Maurice Petersen (2013396)
 */
public final class Level {

    /**
     * The constant currentLevel.
     */
    public static int currentLevel;
    /**
     * Save status.
     */
    public static boolean isSave = false;
    /**
     * Current level.
     */
    private Level level;
    /**
     * HashMap for weapons and their respawn rates.
     */
    private final HashMap<Item.TYPE, Integer> timeToGenerate;
    /**
     * ArrayList of Rats in the level.
     */
    private final ArrayList<Rat> rats;
    /**
     * ArrayList of Items.
     */
    private ArrayList<Item> items;
    /**
     * 2D Array of Tiles.
     */
    private final Tile[][] tiles;
    /**
     * Score.
     */
    private static int score;
    /**
     * Number of Columns.
     */
    private int cols;
    /**
     * Number of Rows.
     */
    private int rows;
    /**
     * Expected time to complete the level.
     */
    private int expectedTime;
    /**
     * The maximum number of rats allowed on the game board before the player loses.
     */
    private int maxRats;
    /**
     * The current game state, if true game over.
     */
    private static boolean gameOver = false;
    /**
     * If true the player successfully completed the level.
     */
    private static boolean gameWon = false;
    /**
     * The current tick.
     */
    private int currentTick;
    /**
     * The current game state, if true the game has been paused.
     */
    private static boolean paused = false;

    /**
     * Instantiates a new Level.
     *
     * @param paramTimeToGenerate the time to generate
     * @param paramExpectedTime   the expected time
     * @param paramMaxRats        the max rats
     * @param paramTiles          the tiles
     * @param paramRats           the rats
     * @param paramSizeY          the size y
     * @param paramSizeX          the size x
     */
    public Level(final HashMap<Item.TYPE, Integer> paramTimeToGenerate, final int paramExpectedTime,
                 final int paramMaxRats, final Tile[][] paramTiles, final ArrayList<Rat> paramRats,
                 final int paramSizeY, final int paramSizeX) {
        this.items = new ArrayList<>();
        this.tiles = paramTiles;
        this.rats = paramRats;
        this.timeToGenerate = paramTimeToGenerate;
        this.expectedTime = paramExpectedTime;
        this.maxRats = paramMaxRats;
        this.cols = paramSizeX;
        this.rows = paramSizeY;
        this.level = this;
        this.currentTick = 0;
    }

    /**
     * Place items an item at the given Tile.
     *
     * @param item the item
     * @param tile the tile
     */
    public void placeItem(final Item item, final Tile tile) {
        item.setCurrentPosX(tile.getX());
        item.setCurrentPosY(tile.getY());
        tile.addEntityToTile(item);
        items.add(item);
    }

    /**
     * Place rat at the given Tile.
     *
     * @param rat  the rat
     * @param tile the tile
     */
    public void placeRat(final Rat rat, final Tile tile) {
        rat.setCurrentPosX(tile.getX());
        rat.setCurrentPosY(tile.getY());
        tile.addEntityToTile(rat);
        rats.add(rat);
    }

    /**
     * Sets game over.
     *
     * @param paramGameOver the game over
     */
    public static void setGameOver(final boolean paramGameOver) {
        Level.gameOver = paramGameOver;
    }

    /**
     * Sets game won.
     *
     * @param paramGameWon the game won
     */
    public static void setGameWon(final boolean paramGameWon) {
        Level.gameWon = paramGameWon;
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
     * @param paramScore the score
     */
    public void setScore(final int paramScore) {
        Level.score = paramScore;
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
     * @param paramLevel the level
     */
    public void setLevel(final Level paramLevel) {
        this.level = paramLevel;
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
     * @param paramCurrentLevel the current level
     */
    public static void setCurrentLevel(final int paramCurrentLevel) {
        Level.currentLevel = paramCurrentLevel;
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
     * Set IsSave.
     *
     * @param paramIsSave if game is saved
     */
    public static void setIsSave(final boolean paramIsSave) {
        Level.isSave = paramIsSave;
    }

    /**
     * Get IsSave.
     *
     * @return if game is saved
     */
    public static boolean isSave() {
        return Level.isSave;
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
     * Gets a tile at [x][y].
     *
     * @return the tile [ ] [ ]
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Sets columns.
     *
     * @param paramCols the cols
     */
    public void setCols(final int paramCols) {
        this.cols = paramCols;
    }

    /**
     * Gets columns.
     *
     * @return the cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets rows.
     *
     * @param paramRows the rows
     */
    public void setRows(final int paramRows) {
        this.rows = paramRows;
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
     * Sets items.
     *
     * @param paramItems the items
     */
    public void setItems(final ArrayList<Item> paramItems) {
        this.items = paramItems;
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
     * @param paramMaxRats the max rats
     */
    public void setMaxRats(final int paramMaxRats) {
        this.maxRats = paramMaxRats;
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
     * @param paramExpectedTime the expected time
     */
    public void setExpectedTime(final int paramExpectedTime) {
        this.expectedTime = paramExpectedTime;
    }

    /**
     * Gets expected time.
     *
     * @return the expected time
     */
    public int getExpectedTime() {
        return expectedTime;
    }

    /**
     * Sets current tick.
     *
     * @param paramCurrentTick sets the current tick to this value
     */
    public void setCurrentTick(final int paramCurrentTick) {
        this.currentTick = paramCurrentTick;
    }

    /**
     * Returns current tick.
     *
     * @return current tick
     */
    public int getCurrentTick() {
        return currentTick;
    }

    /**
     * Sets pause.
     *
     * @param paramPaused
     */
    public static void setPaused(final boolean paramPaused) {
        Level.paused = paramPaused;
    }

    /**
     * Returns the pause status.
     *
     * @return current state of paused boolean
     */
    public static boolean getPaused() {
        return !paused;
    }

}
