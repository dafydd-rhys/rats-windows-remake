package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import main.Functions;

/**
 * Settings Controller
 *
 * @author Gareth Wade (1901805)
 */
public class SettingsController implements Initializable {
    @FXML
    private Slider musicSlider;
    @FXML
    private Slider sfxSlider;
    @FXML
    private JFXButton mainMenuButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onActions();
        setImages();
    }

    private void onActions() {
        mainMenuButton.setOnAction(e -> {
            try {
                Functions.openWindow("\\src\\resources\\fxml\\main_menu.fxml", "Main Menu");
                Functions.close((Stage) mainMenuButton.getScene().getWindow());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setImages() {

    }

}
