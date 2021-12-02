package main.level;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.canvas.GraphicsContext;
import tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * LevelLoadGenerator.java
 * <p>
 * Generates a level from a loaded save file.
 *
 * @author Maurice Petersen (2013396)
 */
public class LevelLoadGenerator {

    private final HashMap<Item.TYPE, Integer> timeToGenerate;
    private final GraphicsContext gc;
    private final char[][] tiles;
    private final ArrayList<Rat> ratSpawns;
    private final ArrayList<Item> itemSpawns;
    private final int expectedTime;
    private final int maxRats;
    private final int sizeY;
    private final int sizeX;
    private Level level;

    public LevelLoadGenerator(HashMap<Item.TYPE, Integer> timeToGenerate,
                              GraphicsContext gc, int sizeX, int sizeY,
                              char[][] level, ArrayList<Rat> ratSpawns,
                              ArrayList<Item> itemSpawns, int expectedTime,
                              int maxRats) {
        this.timeToGenerate = timeToGenerate;
        this.gc = gc;
        this.tiles = level;
        this.ratSpawns = ratSpawns;
        this.itemSpawns = itemSpawns;
        this.expectedTime = expectedTime;
        this.maxRats = maxRats;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        generateLevel();
    }

    /**
     * Generates the level
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

        this.level =
                new Level(timeToGenerate, expectedTime, maxRats, tilesArray,
                        ratSpawns, sizeY, sizeX);
        this.level.setItems(itemSpawns);
    }

    /**
     * Add tile from level file to tile array.
     *
     * @param tiles tile array
     * @param x     x coordinate
     * @param y     y coordinate
     * @param type  tile type
     */
    private void setTile(Tile[][] tiles, int x, int y, Tile.TYPE type) {
        Tile tile = new Tile(x, y, type, new ArrayList<>());
        tiles[y][x] = tile;
        gc.drawImage(tile.getImage(), x * 50, y * 50);
    }

    /**
     * Add rats to tile.
     *
     * @param tiles tile array.
     * @param rat   rat to be added.
     */
    private void setRat(Tile[][] tiles, Rat rat) {
        int x = rat.getCurrentPosX();
        int y = rat.getCurrentPosY();
        tiles[y][x].addEntityToTile(rat);

        if (tiles[y][x].isCovering()) {
            gc.drawImage(rat.getImage(), x * 50, y * 50);
        }
    }

    /**
     * Add item to tile.
     *
     * @param tiles tile array
     * @param item  item to be added
     */
    private void setItem(Tile[][] tiles, Item item) {
        int x = item.getCurrentPosX();
        int y = item.getCurrentPosY();

        tiles[y][x].addEntityToTile(item);
    }

    public Level getLevel() {
        return this.level;
    }

}
