package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Functions;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */
public class MainController implements Initializable {

    @FXML
    private JFXButton proceed;

    @FXML
    private ComboBox<String> selectBiome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onActions();
        setImages();
        selectBiome.getItems().addAll("Grass","Sand","Snow");
    }

    private void onActions() {
        proceed.setOnAction(e -> {
            try {
                Functions.openWindow("\\src\\resources\\fxml\\main_menu.fxml", "Game Screen");
                Functions.close((Stage) proceed.getScene().getWindow());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setImages() {

    }

}
