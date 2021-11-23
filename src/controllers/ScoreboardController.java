package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.external.Audio;
import main.stage.StageFunctions;
import scoreboard.Scoreboard;
import scoreboard.ScoreboardPlayer;


/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Stefan-Cristian Daitoiu (2033160)
 */
public class ScoreboardController implements Initializable {

    /** window - represents the parent of all elements */
    @FXML private AnchorPane window;
    /** levelSelector - combo box holding all scoreboard options */
    @FXML private ComboBox<String> levelSelector;
    /** scoreboard - table that holds the top 10 players */
    @FXML private TableView<ScoreboardPlayer> scoreboard;
    /** level - level row representing the top 10 players for this particular level */
    @FXML private TableColumn<ScoreboardPlayer, String> level;
    /** rank - rank of the player in question */
    @FXML private TableColumn<ScoreboardPlayer, Integer> rank;
    /** name - name of the player in question */
    @FXML private TableColumn<ScoreboardPlayer, String> name;
    /** score - score of the player in question */
    @FXML private TableColumn<ScoreboardPlayer, Integer> score;
    /** sfxMute - button that mutes effect sound */
    @FXML private JFXButton sfx;
    /** musicMute - button that mutes music */
    @FXML private JFXButton music;
    /** settings - button that opens the setting window */
    @FXML private JFXButton settings;
    /** minimize - button that minimizes window */
    @FXML private JFXButton minimize;
    /** maximise - button that minimizes window */
    @FXML private JFXButton maximise;
    /** back - button that goes back to previous window */
    @FXML private JFXButton back;
    /** exit - button that exits program */
    @FXML private JFXButton exit;
    /** musicImage - Image within JFXButton music */
    @FXML private ImageView musicImage;
    /** effectsImage - Image within JFXButton sfx */
    @FXML private ImageView effectsImage;
    
    /**
     * Main
     *
     * @author Dafydd-Rhys Maund (2003900)
     * initialize - when the fxml is loaded do this straight away
     * loads all players from all scoreboards into scoreboard table.
     */
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
        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));
    }

    /**
     * OnActions() - adds listeners to buttons (i.e when button is clicked -> do this)
     *
     * minimize - minimize window
     * maximise - maximise window
     * back - go back to previous window
     * exit - exit program
     * settings - open settings window
     */
    private void onActions() {
        music.setOnAction(e -> {
            StageFunctions.muteMusic();
            StageFunctions.toggleOpacity(musicImage);
        });
        sfx.setOnAction(e -> {
            StageFunctions.muteEffects();
            StageFunctions.toggleOpacity(effectsImage);
        });
        minimize.setOnAction(e -> StageFunctions.minimize());
        maximise.setOnAction(e -> StageFunctions.maximise());
        exit.setOnAction(e -> {
            try {
                StageFunctions.exit();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });
        back.setOnAction(e-> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\main_menu.fxml", "Game Screen");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });
        settings.setOnAction(e -> {
            try {
                StageFunctions.openSettings();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * scoreboardListener() - adds listener to comboBox (i.e when combo box is edited -> do this)
     *
     * levelSelector - combo box
     * when combo box is changed check in row 1 (level row) for all rows that contain new value and show them
     */
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
