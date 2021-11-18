package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
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
    private TableColumn<?, ?> Position;

    @FXML
    private JFXButton back;

    @FXML
    private ImageView backIcon;

    @FXML
    private ImageView closeIcon;

    @FXML
    private TableColumn<?, ?> levelOne;

    @FXML
    private ComboBox<String> levelSelector;

    @FXML
    private TableColumn<?, ?> levelThree;

    @FXML
    private TableColumn<?, ?> levelTwo;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView maximiseIcon;

    @FXML
    private ImageView minimizeIcon;

    @FXML
    private ImageView minimizedLogo;

    @FXML
    private TableView<?> scoreTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        levelSelector.getItems().addAll(
                "Level 1",
                "Level 2",
                "Level 3",
                "Level 4",
                "Level 5",
                "Level 6");
    }



}
