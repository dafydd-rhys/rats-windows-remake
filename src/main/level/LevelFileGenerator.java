package main.level;

import entity.Item;
import entity.Rat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.GraphicsContext;
import tile.Tile;

/**
 * LevelFileGenerator
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class LevelFileGenerator {

    private final GraphicsContext gc;
    private HashMap<Item.TYPE, Integer> timeToGenerate;
    private final char[][] tiles;
    private final char[][] spawns;
    private final int expectedTime;
    private final int maxRats;

    public LevelFileGenerator(HashMap<Item.TYPE, Integer> timeToGenerate, GraphicsContext gc, char[][] tiles,
                              char[][] spawns, int expectedTime, int maxRats) throws IOException {
        this.timeToGenerate = timeToGenerate;
        this.gc = gc;
        this.tiles = tiles;
        this.spawns = spawns;
        this.expectedTime = expectedTime;
        this.maxRats = maxRats;

        generateLevel();
    }

    private void generateLevel() {
        Tile[][] tilesArray = new Tile[20][20];
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
                    setRat(tilesArray, ratsArray, x, y, Rat.Gender.MALE, true);
                } else if (spawns[y][x] == 'F') {
                    setRat(tilesArray, ratsArray, x, y, Rat.Gender.FEMALE, true);
                }
            }
        }

        new Level(timeToGenerate, expectedTime, maxRats, tilesArray, ratsArray);
    }

    private void setTile(Tile[][] tiles, int x, int y, Tile.TYPE type) {
        Tile tile = new Tile(x, y, type, new ArrayList<>());
        tiles[y][x] = tile;
        gc.drawImage(tile.getImage(), x * 50, y * 50);
    }

    private void setRat(Tile[][] tiles, ArrayList<Rat> rats, int x, int y, Rat.Gender gender, boolean adult) {
        Rat rat = new Rat(gender, adult);
        rat.setCurrentPosX(x);
        rat.setCurrentPosY(y);

        tiles[y][x].addEntityToTile(rat);
        rats.add(rat);

        if (tiles[y][x].isCovering()) {
            gc.drawImage(rat.getImage(), x * 50, y * 50);
        }
    }

}
