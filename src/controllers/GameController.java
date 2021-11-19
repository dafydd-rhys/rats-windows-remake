package controllers;

import entity.Rat;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.Functions;
import main.TickTimer;
import main.level.LevelFileGenerator;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Canvas canvas = new Canvas(600, 500);
        game.setCenter(canvas);

        try {
            LevelFileReaderTest levelTest = new LevelFileReaderTest(1);
            char[][] lvl = levelTest.getLevel();
            char[][] spawns = levelTest.getSpawns();
            new LevelFileGenerator(canvas, lvl, spawns);
        } catch (IOException e) {
            e.printStackTrace();
        }

        onActions();
        setImages();
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

    private void tick() {
        //TickTimer.start();
    }

    private void onActions() {
        bar.setOnMouseDragged(E -> Functions.dragWindow(bar));
    }

    private void setImages() {

    }

}
