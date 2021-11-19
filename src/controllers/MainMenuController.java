package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Functions;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */
public class MainMenuController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private JFXButton proceed;
    @FXML
    private JFXButton scoreboard;
    @FXML
    private JFXButton settings;
    @FXML
    private JFXButton volume;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton maximise;
    @FXML
    private JFXButton exit;
    @FXML
    private JFXButton btnExit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onActions();
        setImages();
    }

    private void onActions() {
        proceed.setOnAction(e -> {
            try {
                Functions.openWindow("\\src\\resources\\fxml\\game.fxml", "Game Screen");
                Functions.close(window);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        scoreboard.setOnAction(e -> {
            try {
                Functions.openWindow("\\src\\resources\\fxml\\scoreboard.fxml", "Scoreboard");
                Functions.close(window);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        settings.setOnAction(e -> {
            try {
                Functions.openWindow("\\src\\resources\\fxml\\settings.fxml", "Settings");
                Functions.close(window);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //volume.setOnAction(e -> music?);
        minimize.setOnAction(e -> Functions.minimize(window));
        maximise.setOnAction(e -> Functions.maximise(window));
        exit.setOnAction(e -> Functions.exit());
        btnExit.setOnAction(e -> Functions.exit());
    }

    private void setImages() {

    }

}
