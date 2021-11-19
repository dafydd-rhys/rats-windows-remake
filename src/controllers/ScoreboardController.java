package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Functions;
import scoreboard.Scoreboard;
import scoreboard.ScoreboardPlayer;


/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class ScoreboardController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private ComboBox<String> levelSelector;
    @FXML
    private TableView<ScoreboardPlayer> scoreboard;
    @FXML
    private TableColumn<ScoreboardPlayer, String> level;
    @FXML
    private TableColumn<ScoreboardPlayer, Integer> rank;
    @FXML
    private TableColumn<ScoreboardPlayer, String> name;
    @FXML
    private TableColumn<ScoreboardPlayer, Integer> score;

    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton maximise;
    @FXML
    private JFXButton exit;
    @FXML
    private JFXButton back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        levelSelector.getItems().addAll("Level 1", "Level 2",
                "Level 3", "Level 4", "Level 5", "Level 6");
        level.setCellValueFactory(new PropertyValueFactory<>("Level"));
        rank.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        score.setCellValueFactory(new PropertyValueFactory<>("Score"));

        ArrayList<ScoreboardPlayer> players = new ArrayList<>();
        try {
            players = Scoreboard.getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ScoreboardPlayer player : players) {
            scoreboard.getItems().add(player);
        }
        scoreboardListener();
        levelSelector.setValue("Level 1");
        onActions();
    }

    private void onActions() {
        minimize.setOnAction(e -> Functions.minimize(window));
        maximise.setOnAction(e -> Functions.maximise(window));
        exit.setOnAction(e -> Functions.exit());
    }

    private void scoreboardListener() {
        FilteredList<ScoreboardPlayer> filteredData = new FilteredList<>(scoreboard.getItems(), p -> true);
        levelSelector.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> filteredData.setPredicate(myObject -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return String.valueOf(myObject.getLevel()).toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<ScoreboardPlayer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(scoreboard.comparatorProperty());
        scoreboard.setItems(sortedData);
    }

}
