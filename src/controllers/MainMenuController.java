package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.Functions;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class MainMenuController implements Initializable {

    @FXML
    private JFXButton proceed;
    @FXML
    private JFXButton scoreboard;
    @FXML
    private JFXButton settings;
    @FXML
    private JFXButton volume;
    @FXML
    private JFXButton exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onActions();
        setImages();
    }

    private void onActions() {
        proceed.setOnAction(e -> {
            try {
                Functions.openWindow("\\src\\resources\\fxml\\game.fxml", "Game Screen");
                Functions.close((Stage) proceed.getScene().getWindow());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        scoreboard.setOnAction(e -> {
            try {
                Functions.openWindow("\\src\\resources\\fxml\\scoreboard.fxml", "Scoreboard");
                Functions.close((Stage) scoreboard.getScene().getWindow());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        settings.setOnAction(e -> {
            try {
                Functions.openWindow("\\src\\resources\\fxml\\settings.fxml", "Settings");
                Functions.close((Stage) settings.getScene().getWindow());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //volume.setOnAction(e -> music?);
        exit.setOnAction(e -> System.exit(1));
    }

    private void setImages() {

    }

}
