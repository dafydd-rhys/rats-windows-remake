package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import main.StageFunctions;

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
    private JFXButton sfxMute;
    @FXML
    private JFXButton musicMute;
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
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Game Screen");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        scoreboard.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\scoreboard.fxml", "Scoreboard");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        settings.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\settings.fxml", "Settings");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //volume.setOnAction(e -> music?);
        minimize.setOnAction(e -> StageFunctions.minimize());
        maximise.setOnAction(e -> StageFunctions.maximise());
        exit.setOnAction(e -> StageFunctions.exit());
        btnExit.setOnAction(e -> StageFunctions.exit());
    }

    private void setImages() {

    }

}
