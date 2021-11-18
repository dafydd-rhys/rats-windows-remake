package main.level;

import java.io.IOException;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * LevelFileGenerator
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class LevelFileGenerator {

    private final Canvas canvas;
    private final char[][] level;
    private final char[][] spawns;
    private final String dir = System.getProperty("user.dir") + "/src/resources/images/";
    private final Image tunnel = new Image(dir + "tunnel.png");
    private final Image path = new Image(dir + "path.png");
    private final Image grass = new Image(dir + "grass.png");
    private final Image male = new Image(dir + "male-rat.png");
    private final Image female = new Image(dir + "female-rat.png");
    private final Image bomb = new Image(dir + "bomb.png");

    public LevelFileGenerator(Canvas canvas, char[][] level, char[][] spawns) throws IOException {
        this.canvas = canvas;
        this.level = level;
        this.spawns = spawns;
        generateLevel();
    }

    private void generateLevel() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < level.length; y++) {
            for (int x = 0; x < level[y].length; x++) {
                if (level[y][x] == 'G') {
                    gc.drawImage(grass, x * 50, y * 50);
                } else if (level[y][x] == 'P') {
                    gc.drawImage(path, x * 50, y * 50);
                } else {
                    gc.drawImage(tunnel, x * 50, y * 50);
                }
                if (spawns[y][x] == 'M') {
                    gc.drawImage(rotate(male, 90), x * 50, y * 50);
                } else if (spawns[y][x] == 'F') {
                    gc.drawImage(rotate(female, -90), x * 50, y * 50);
                }
            }
        }
    }

    private Image rotate(final Image image, final int degree) {
        ImageView iv = new ImageView(image);
        iv.setRotate(degree);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        return iv.snapshot(params, null);
    }

}
