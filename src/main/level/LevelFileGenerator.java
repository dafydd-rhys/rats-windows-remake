package main.level;

import entity.Item;
import entity.rat.Rat;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.GraphicsContext;
import tile.Tile;

/**
 * LevelFileGenerator.
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski (857847)
 */
public class LevelFileGenerator {

    /**
     * The current level.
     */
    private Level level;
    /**
     * Hashmap of the time to generate each item.
     */
    private final HashMap<Item.TYPE, Integer> timeToGenerate;
    /**
     *
     */
    private final GraphicsContext gc;
    /**
     * 2D Array of Tiles.
     */
    private final char[][] tiles;
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
     * The image's X position.
     */
    private static final int IMAGE_X = 50;
    /**
     * The image's Y position.
     */
    private static final int IMAGE_Y = 50;
    /**
     * Instantiates a new Level file generator.
     *
     * @param paramTimeToGenerate the time to generate
     * @param paramGC             the gc
     * @param paramSizeX          the size x
     * @param paramSizeY          the size y
     * @param paramLevel          the level
     * @param paramExpectedTime   the expected time
     * @param paramMaxRats        the max rats
     */
    public LevelFileGenerator(final HashMap<Item.TYPE, Integer> paramTimeToGenerate, final GraphicsContext paramGC,
                              final int paramSizeX, final int paramSizeY, final char[][] paramLevel,
                              final int paramExpectedTime, final int paramMaxRats) {
        this.timeToGenerate = paramTimeToGenerate;
        this.gc = paramGC;
        this.tiles = paramLevel;
        this.expectedTime = paramExpectedTime;
        this.maxRats = paramMaxRats;
        this.sizeX = paramSizeX;
        this.sizeY = paramSizeY;

        generateLevel();
    }

    /**
     * Reads in the level and sets the tile and rat arrays.
     */
    private void generateLevel() {
        Tile[][] tilesArray = new Tile[sizeY][sizeX];
        ArrayList<Rat> ratsArray = new ArrayList<>();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] == 'G') {
                    setTile(tilesArray, x, y, Tile.TYPE.THEMED);
                } else if (tiles[y][x] == 'P') {
                    setTile(tilesArray, x, y, Tile.TYPE.PATH);
                } else if (tiles[y][x] == 'T') {
                    setTile(tilesArray, x, y, Tile.TYPE.TUNNEL);
                } else if (tiles[y][x] == 'M') {
                    setTile(tilesArray, x, y, Tile.TYPE.PATH);
                    setRat(tilesArray, ratsArray, x, y, Rat.Gender.MALE, false);
                } else if (tiles[y][x] == 'N') {
                    setTile(tilesArray, x, y, Tile.TYPE.TUNNEL);
                    setRat(tilesArray, ratsArray, x, y, Rat.Gender.MALE, false);
                } else if (tiles[y][x] == 'F') {
                    setTile(tilesArray, x, y, Tile.TYPE.PATH);
                    setRat(tilesArray, ratsArray, x, y, Rat.Gender.FEMALE, false);
                } else if (tiles[y][x] == 'K') {
                    setTile(tilesArray, x, y, Tile.TYPE.TUNNEL);
                    setRat(tilesArray, ratsArray, x, y, Rat.Gender.FEMALE, false);
                }
            }
        }

        this.level = new Level(timeToGenerate, expectedTime, maxRats, tilesArray, ratsArray, sizeY, sizeX);
    }

    /**
     * @param paramTiles 2D array of tiles
     * @param x X coordinate of the tile
     * @param y Y coordinate of the tile
     * @param type type of tile (grass, path, tunnel)
     */
    private void setTile(final Tile[][] paramTiles, final int x, final int y, final Tile.TYPE type) {
        Tile tile = new Tile(x, y, type, new ArrayList<>());
        paramTiles[y][x] = tile;
        gc.drawImage(tile.getImage(), x * IMAGE_X, y * IMAGE_Y);
    }

    /**
     * @param paramTiles 2D array of tiles
     * @param rats List of rats
     * @param x X coordinate of rat
     * @param y Y coordinate of rat
     * @param gender gender of rat
     * @param adult if the rat is an adult or a baby
     */
    private void setRat(final Tile[][] paramTiles, final ArrayList<Rat> rats, final int x, final int y,
                        final Rat.Gender gender, final boolean adult) {
        Rat rat = new Rat(gender, adult);
        rat.setCurrentPosX(x);
        rat.setCurrentPosY(y);

        paramTiles[y][x].addEntityToTile(rat);
        rats.add(rat);

        if (paramTiles[y][x].isCovering()) {
            gc.drawImage(rat.getImage(), x * IMAGE_X, y * IMAGE_Y);
        }
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public Level getLevel() {
        return this.level;
    }

}
