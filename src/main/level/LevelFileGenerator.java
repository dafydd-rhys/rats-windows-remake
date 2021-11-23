package main.level;

import entity.Entity;
import entity.rats.Rat;
import entity.weapon.Bomb;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javax.crypto.AEADBadTagException;
import tile.GrassTile;
import tile.PathTile;
import tile.Tile;
import tile.TunnelTile;

/**
 * LevelFileGenerator
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class LevelFileGenerator {

    private final GraphicsContext gc;
    private final char[][] tiles;
    private final char[][] spawns;

    public LevelFileGenerator(GraphicsContext gc, char[][] tiles, char[][] spawns) throws IOException {
        this.gc = gc;
        this.tiles = tiles;
        this.spawns = spawns;
        generateLevel();
    }

    private void generateLevel() {
        Tile[][] tilesArray = new Tile[20][20];
        ArrayList<Rat> ratsArray = new ArrayList<>();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] == 'G') {
                    GrassTile grass = new GrassTile(x, y, new ArrayList<>());
                    tilesArray[y][x] = grass;
                    gc.drawImage(grass.getImage(), x * 50, y * 50);
                } else if (tiles[y][x] == 'P') {
                    PathTile path = new PathTile(x, y, new ArrayList<>());
                    tilesArray[y][x] = path;
                    gc.drawImage(path.getImage(), x * 50, y * 50);
                } else {
                    TunnelTile tunnel = new TunnelTile(x, y, new ArrayList<>());
                    tilesArray[y][x] = tunnel;
                    gc.drawImage(tunnel.getImage(), x * 50, y * 50);
                }
                if (spawns[y][x] == 'M') {
                    Rat maleRat = new Rat(Rat.Gender.MALE, true);
                    maleRat.setCurrentPosX(x);
                    maleRat.setCurrentPosY(y);
                    
                    tilesArray[y][x].addEntityToTile(maleRat);
                    ratsArray.add(maleRat);

                    if (!tilesArray[y][x].isCovering()) {
                        gc.drawImage(maleRat.getImage(), x * 50, y * 50);
                    }
                } else if (spawns[y][x] == 'F') {
                    Rat femaleRat = new Rat(Rat.Gender.FEMALE, true);
                    femaleRat.setCurrentPosX(x);
                    femaleRat.setCurrentPosY(y);

                    tilesArray[y][x].addEntityToTile(femaleRat);
                    ratsArray.add(femaleRat);

                    if (!tilesArray[y][x].isCovering()) {
                        gc.drawImage(femaleRat.getImage(), x * 50, y * 50);
                    }
                }
            }
        }

        new Level(tilesArray, ratsArray);
    }

}
