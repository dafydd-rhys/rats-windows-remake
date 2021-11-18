package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.Functions;
import scoreboard.Scoreboard;


/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class ScoreboardController implements Initializable {

    @FXML
    private JFXButton scoreboard1;
    @FXML
    private JFXButton scoreboard2;
    @FXML
    private JFXButton scoreboard3;
    @FXML
    private JFXButton scoreboard4;
    @FXML
    private JFXButton scoreboard5;
    @FXML
    private JFXButton scoreboard6;
    @FXML
    private JFXButton scoreboard7;
    @FXML
    private JFXButton scoreboard8;
    @FXML
    private JFXButton scoreboard9;
    @FXML
    private JFXButton scoreboard10;
    @FXML
    private JFXButton back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OnActions();
        setImages();
    }

    private void OnActions() {
        scoreboard1.setOnAction(e -> {
            openScoreboard(1);
        });
        scoreboard2.setOnAction(e -> {
            openScoreboard(2);
        });
        scoreboard3.setOnAction(e -> {
            openScoreboard(3);
        });
        scoreboard4.setOnAction(e -> {
            openScoreboard(4);
        });
        scoreboard5.setOnAction(e -> {
            openScoreboard(5);
        });
        scoreboard6.setOnAction(e -> {
            openScoreboard(6);
        });
        scoreboard7.setOnAction(e -> {
            openScoreboard(7);
        });
        scoreboard8.setOnAction(e -> {
            openScoreboard(8);
        });
        scoreboard9.setOnAction(e -> {
            openScoreboard(9);
        });
        scoreboard10.setOnAction(e -> {
            openScoreboard(10);
        });
        back.setOnAction(e -> {
            Functions.close((Stage) back.getScene().getWindow());
            try {
                Functions.openWindow("\\src\\resources\\fxml\\main_menu.fxml", "Main Menu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void openScoreboard(int level) {
        Scoreboard.level = level;
        Functions.close((Stage) back.getScene().getWindow());
        try {
            Functions.openWindow("\\src\\resources\\fxml\\top10.fxml", "Top10");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setImages() {

    }

}
