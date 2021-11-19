package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Functions;

/**
 * Settings Controller
 *
 * @author Gareth Wade (1901805)
 */
public class SettingsController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private Slider musicSlider;
    @FXML
    private Slider sfxSlider;
    @FXML
    private JFXButton mainMenuButton;
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
        mainMenuButton.setOnAction(e -> {
            try {
                Functions.openWindow("\\src\\resources\\fxml\\main_menu.fxml", "Main Menu");
                Functions.close(window);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        minimize.setOnAction(e -> Functions.minimize(window));
        maximise.setOnAction(e -> Functions.maximise(window));
        exit.setOnAction(e -> Functions.exit());
    }

    private void setImages() {

    }

}
