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
 * @author Gareth Wade (1901805)
 */
public class LevelSelectController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private JFXButton level_1;
    @FXML
    private JFXButton level_2;
    @FXML
    private JFXButton level_3;
    @FXML
    private JFXButton level_4;
    @FXML
    private JFXButton level_5;
    @FXML
    private JFXButton level_6;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onActions();
        setImages();
    }

    private void onActions() {
        level_1.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 1");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        level_2.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 1");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        level_3.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 1");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        level_4.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 1");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        level_5.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 1");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        level_6.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 1");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
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

    private void setImages() {

    }
}
