package main.level;

import entity.Item;
import entity.rat.Rat;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.GraphicsContext;
import tile.Tile;

/**
 * LevelLoadGenerator.java
 * <p>
 * Generates a level from a loaded save file.
 *
 * @author Maurice Petersen (2013396)
 */
public class LevelLoadGenerator {

    /**
     * The current level.
     */
    private Level level;
    /**
     * Hashmap of the times to generate each item.
     */
    private final HashMap<Item.TYPE, Integer> timeToGenerate;
    /**
     *
     */
    private final GraphicsContext gc;
    /**
     * 2D array of tiles.
     */
    private final char[][] tiles;
    /**
     * List of rat spawns.
     */
    private final ArrayList<Rat> ratSpawns;
    /**
     * List of item spawns.
     */
    private final ArrayList<Item> itemSpawns;
    /**
     * Expected time to complete the level.
     */
    private final int expectedTime;
    /**
     * The maximum number of rats allowed on the game board before the player loses.
     */
    private final int maxRats;
    /**
     * The size of the level's Y axis.
     */
    private final int sizeY;
    /**
     * The size of the level's X axis.
     */
    private final int sizeX;
    /**
     * The Image's X position.
     */
    private static final int IMAGE_X = 50;
    /**
     * The Image's Y position.
     */
    private static final int IMAGE_Y = 50;

    /**
     * Constructor.
     *
     * @param paramTimeToGenerate hashmap of times to generate each item
     * @param paramGC
     * @param paramSizeX size of X axis
     * @param paramSizeY size of Y axis
     * @param paramLevel current level
     * @param paramRatSpawns list of rat spawns
     * @param paramItemSpawns list of item spawns
     * @param paramExpectedTime expected time to complete the level
     * @param paramMaxRats maximum number of rats allowed on the game board before the player loses
     */
    public LevelLoadGenerator(final HashMap<Item.TYPE, Integer> paramTimeToGenerate, final GraphicsContext paramGC,
                              final int paramSizeX, final int paramSizeY, final char[][] paramLevel,
                              final ArrayList<Rat> paramRatSpawns, final ArrayList<Item> paramItemSpawns,
                              final int paramExpectedTime, final int paramMaxRats) {
        this.timeToGenerate = paramTimeToGenerate;
        this.gc = paramGC;
        this.tiles = paramLevel;
        this.ratSpawns = paramRatSpawns;
        this.itemSpawns = paramItemSpawns;
        this.expectedTime = paramExpectedTime;
        this.maxRats = paramMaxRats;
        this.sizeX = paramSizeX;
        this.sizeY = paramSizeY;

        generateLevel();
    }

    /**
     * Generates the level.
     */
    private void generateLevel() {
        Tile[][] tilesArray = new Tile[sizeY][sizeX];

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] == 'G') {
                    setTile(tilesArray, x, y, Tile.TYPE.THEMED);
                } else if (tiles[y][x] == 'P') {
                    setTile(tilesArray, x, y, Tile.TYPE.PATH);
                } else {
                    setTile(tilesArray, x, y, Tile.TYPE.TUNNEL);
                }
            }
        }

        for (Rat rat : ratSpawns) {
            setRat(tilesArray, rat);
        }

        for (Item item : itemSpawns) {
            setItem(tilesArray, item);
        }

        this.level = new Level(timeToGenerate, expectedTime, maxRats, tilesArray, ratSpawns, sizeY, sizeX);
        this.level.setItems(itemSpawns);
    }

    /**
     * Add tile from level file to tile array.
     *
     * @param paramTiles tile array
     * @param x     x coordinate
     * @param y     y coordinate
     * @param type  tile type
     */
    private void setTile(final Tile[][] paramTiles, final int x, final int y, final Tile.TYPE type) {
        Tile tile = new Tile(x, y, type, new ArrayList<>());
        paramTiles[y][x] = tile;
        gc.drawImage(tile.getImage(), x * IMAGE_X, y * IMAGE_Y);
    }

    /**
     * Add rats to tile.
     *
     * @param paramTiles tile array.
     * @param rat   rat to be added.
     */
    private void setRat(final Tile[][] paramTiles, final Rat rat) {
        int x = rat.getCurrentPosX();
        int y = rat.getCurrentPosY();
        paramTiles[y][x].addEntityToTile(rat);

        if (paramTiles[y][x].isCovering()) {
            gc.drawImage(rat.getImage(), x * IMAGE_X, y * IMAGE_Y);
        }
    }

    /**
     * Add item to tile.
     *
     * @param paramTiles tile array
     * @param item  item to be added
     */
    private void setItem(final Tile[][] paramTiles, final Item item) {
        int x = item.getCurrentPosX();
        int y = item.getCurrentPosY();

        paramTiles[y][x].addEntityToTile(item);
    }

    /**
     * Get level.
     *
     * @return level
     */
    public Level getLevel() {
        return this.level;
    }

}
