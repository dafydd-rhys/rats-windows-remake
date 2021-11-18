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
public class MainController implements Initializable {

    @FXML
    private JFXButton proceed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onActions();
        setImages();
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
