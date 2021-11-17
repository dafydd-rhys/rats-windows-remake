package controllers;

import entity.Rat;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import main.Functions;
import main.level.LevelFileReaderTest;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class GameController implements Initializable {

    @FXML
    private AnchorPane bar;
    @FXML
    private AnchorPane abilities;
    @FXML
    private BorderPane game;
    private Canvas canvas;
    private final String dir = System.getProperty("user.dir") + "/src/resources/images/";
    private final Image tunnel = new Image(dir + "tunnel.png");
    private final Image path = new Image(dir + "path.png");
    private final Image grass = new Image(dir + "grass.png");
    private final Image male = new Image(dir + "male-rat.png");
    private final Image female = new Image(dir + "female-rat.png");
    private final Image bomb = new Image(dir + "bomb.png");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvas = new Canvas(600, 500);
        game.setCenter(canvas);
        try {
            drawGame();
        } catch (IOException e) {
            e.printStackTrace();
        }

        onActions();
        setImages();
    }

    public void drawGame() throws IOException {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        LevelFileReaderTest levelTest = new LevelFileReaderTest(1);
        char[][] lvl = levelTest.getLevel();
        char[][] spawns = levelTest.getSpawns();

        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                if (lvl[y][x] == 'G') {
                    gc.drawImage(grass, x * 50, y * 50);
                } else if (lvl[y][x] == 'P') {
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

    private void movement(final Rat rat) {
        /* AI rat movement
        if (direction == left) {
            move(-1);
        } else if (direction == right) {
            move(1);
        } else if (direction == up) {
            move(1);
        } else if (direction == down) {
            move(-1);
        }
        drawGame();
         */
    }

    private void move(final Rat rat, final int i) {
        /* check available movement
        if (rat.getCurrentPosX() + i != GrassTile) {
            rat.setCurrentPosX(rat.getCurrentPosX() + i);
        } else if (rat.getCurrentPosY() + i != GrassTile) {
            rat.setCurrentPosY(rat.getCurrentPosY() + i);
        } else if (rat.getCurrentPosX() - i != GrassTile) {
            rat.setCurrentPosX(rat.getCurrentPosX() + i);
        } else if (rat.getCurrentPosY() - i != GrassTile) {
            rat.setCurrentPosX(rat.getCurrentPosY() + i);
        }
         */
    }

    private void onActions() {
        bar.setOnMouseDragged(E -> Functions.dragWindow(bar));
    }

    private void setImages() {

    }

}
