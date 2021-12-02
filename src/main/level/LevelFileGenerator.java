package main.level;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.canvas.GraphicsContext;
import tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * LevelFileGenerator
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski (857847)
 */
public class LevelFileGenerator {

    /**
     *
     */
    private final HashMap<Item.TYPE, Integer> timeToGenerate;
    /**
     *
     */
    private final GraphicsContext gc;
    /**
     *
     */
    private final char[][] tiles;
    /**
     *
     */
    private final char[][] spawns;
    /**
     *
     */
    private final int expectedTime;
    /**
     *
     */
    private final int maxRats;
    /**
     *
     */
    private final int sizeY;
    /**
     *
     */
    private final int sizeX;
    /**
     *
     */
    private Level level;

    /**
     * Instantiates a new Level file generator.
     *
     * @param timeToGenerate the time to generate
     * @param gc             the gc
     * @param sizeX          the size x
     * @param sizeY          the size y
     * @param level          the level
     * @param spawns         the spawns
     * @param expectedTime   the expected time
     * @param maxRats        the max rats
     */
    public LevelFileGenerator(HashMap<Item.TYPE, Integer> timeToGenerate,
                              GraphicsContext gc, int sizeX, int sizeY,
                              char[][] level, char[][] spawns, int expectedTime,
                              int maxRats) {
        this.timeToGenerate = timeToGenerate;
        this.gc = gc;
        this.tiles = level;
        this.spawns = spawns;
        this.expectedTime = expectedTime;
        this.maxRats = maxRats;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        generateLevel();
    }

    /**
     *
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
                } else {
                    setTile(tilesArray, x, y, Tile.TYPE.TUNNEL);
                }
                if (spawns[y][x] == 'M') {
                    setRat(tilesArray, ratsArray, x, y, Rat.Gender.MALE, false);
                } else if (spawns[y][x] == 'F') {
                    setRat(tilesArray, ratsArray, x, y, Rat.Gender.FEMALE,
                            false);
                }
            }
        }

        this.level =
                new Level(timeToGenerate, expectedTime, maxRats, tilesArray,
                        ratsArray, sizeY, sizeX);
    }

    /**
     * @param tiles
     * @param x
     * @param y
     * @param type
     */
    private void setTile(Tile[][] tiles, int x, int y, Tile.TYPE type) {
        Tile tile = new Tile(x, y, type, new ArrayList<>());
        tiles[y][x] = tile;
        gc.drawImage(tile.getImage(), x * 50, y * 50);
    }

    /**
     * @param tiles
     * @param rats
     * @param x
     * @param y
     * @param gender
     * @param adult
     */
    private void setRat(Tile[][] tiles, ArrayList<Rat> rats, int x, int y,
                        Rat.Gender gender, boolean adult) {
        Rat rat = new Rat(gender, adult);
        rat.setCurrentPosX(x);
        rat.setCurrentPosY(y);

        tiles[y][x].addEntityToTile(rat);
        rats.add(rat);

        if (tiles[y][x].isCovering()) {
            gc.drawImage(rat.getImage(), x * 50, y * 50);
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
