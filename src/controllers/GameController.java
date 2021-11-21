package controllers;

import com.jfoenix.controls.JFXButton;
import entity.Rat;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.StageFunctions;
import main.level.LevelFileGenerator;
import main.level.LevelFileReader;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class GameController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private AnchorPane bar;
    @FXML
    private AnchorPane abilities;
    @FXML
    private ScrollPane gameScroll;
    @FXML
    private BorderPane game;
    @FXML
    private JFXButton settings;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton maximise;
    @FXML
    private JFXButton exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Canvas canvas = new Canvas(1000, 1000);
        game.setCenter(canvas);
        gameScroll.setPannable(true);

        try {
            LevelFileReader level = new LevelFileReader(1);
            new LevelFileGenerator(canvas, level.getLevel(), level.getSpawns());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new PlayerController(window,canvas);

        setImages();
        onActions();
    }

    private void setImages() {

    }

    private void onActions() {
        settings.setOnAction(e -> {
            try {
                StageFunctions.openSettings("\\src\\resources\\fxml\\settings.fxml", "Settings");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        minimize.setOnAction(e -> StageFunctions.minimize());
        maximise.setOnAction(e -> StageFunctions.maximise());
        exit.setOnAction(e -> StageFunctions.exit());
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

}
