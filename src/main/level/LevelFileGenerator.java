package main.level;

import entity.Entity;
import entity.rats.FemaleRat;
import entity.rats.MaleRat;
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

    private final Canvas canvas;
    private final char[][] tiles;
    private final char[][] spawns;

    private final String entitiesDir = System.getProperty("user.dir") + "/src/resources/images/game/entities/";
    private final Image male = new Image(entitiesDir + "male-rat.png");
    private final Image female = new Image(entitiesDir + "female-rat.png");

    public LevelFileGenerator(Canvas canvas, char[][] tiles, char[][] spawns) throws IOException {
        this.canvas = canvas;
        this.tiles = tiles;
        this.spawns = spawns;
        generateLevel();
    }

    private void generateLevel() {
        Tile[][] tilesArray = new Tile[20][20];
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] == 'G') {
                    tilesArray[y][x] = new GrassTile(x, y, new ArrayList<>());
                    gc.drawImage(GrassTile.getImage(), x * 50, y * 50);
                } else if (tiles[y][x] == 'P') {
                    tilesArray[y][x] = new PathTile(x, y, new ArrayList<>());
                    gc.drawImage(PathTile.getImage(), x * 50, y * 50);
                } else {
                    tilesArray[y][x] = new TunnelTile(x, y, new ArrayList<>());
                    gc.drawImage(TunnelTile.getImage(), x * 50, y * 50);
                }
                if (spawns[y][x] == 'M') {
                    tilesArray[y][x].addEntityToTile(new MaleRat(false));
                    gc.drawImage(rotate(MaleRat.getImage(), 90), x * 50, y * 50);
                } else if (spawns[y][x] == 'F') {
                    tilesArray[y][x].addEntityToTile(new FemaleRat(true));
                    gc.drawImage(rotate(FemaleRat.getImage(), -90), x * 50, y * 50);
                }
            }
        }
        new Level(tilesArray);
    }

    private Image rotate(final Image image, final int degree) {
        ImageView iv = new ImageView(image);
        iv.setRotate(degree);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        return iv.snapshot(params, null);
    }

}
