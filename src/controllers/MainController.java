package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.external.Audio;
import main.external.MOTD;
import main.stage.StageFunctions;
import player.Player;

/**
 * MainController - the player entry scene loaded at start of program.
 *
 * Menu icons created by group
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */
public class MainController implements Initializable {

    /**
     * Theme which the player wishes to play in.
     */
    @FXML
    private ComboBox<String> selectTheme;
    /**
     * Item generation in which the player wishes to play with.
     */
    @FXML
    private ComboBox<String> selectGeneration;
    /**
     * SFX Button - mutes/un-mutes sfx.
     */
    @FXML
    private JFXButton sfx;
    /**
     * Music Button - mutes/un-mutes music.
     */
    @FXML
    private JFXButton music;
    /**
     * Settings Button - loads settings stage.
     */
    @FXML
    private JFXButton settings;
    /**
     * Minimize Button - minimizes window.
     */
    @FXML
    private JFXButton minimize;
    /**
     * Maximise Button - maximises window.
     */
    @FXML
    private JFXButton maximise;
    /**
     * Exit button - terminates program.
     */
    @FXML
    private JFXButton exit;
    /**
     * Name of current player.
     */
    @FXML
    private TextField playerName;
    /**
     * Button allowing player to continue.
     */
    @FXML
    private JFXButton proceed;
    /**
     * The image used to represent music button.
     */
    @FXML
    private ImageView musicImage;
    /**
     * The image used to represent effects button.
     */
    @FXML
    private ImageView effectsImage;
    /**
     * The text used to represent message of the day.
     */
    @FXML
    private Text motd;

    /**
     * This method is run when the scene is loaded.
     *
     * @param url url of resources.
     * @param resourceBundle bundle of resources.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        listeners();
        onActions();

        //makes sure the user can't create any errors.
        selectTheme.setEditable(false);
        selectGeneration.setEditable(false);
        selectGeneration.getItems().addAll("Periodic Generation", "Random Generation");
        selectTheme.getItems().addAll("Default", "Beach", "Christmas");
        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));

        // Allows only alphanumeric characters to be inserted.
        playerName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z\\d*")) {
                playerName.setText(newValue.replaceAll("[^\\sa-zA-Z\\d]", ""));
            }
        });

        //displays MOTD.
        try {
            motd.setText(new MOTD().getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method ensures the user can't proceed without entering valid data.
     */
    private void listeners() {
        proceed.disableProperty().bind(Bindings.isEmpty(playerName.textProperty())
                .or(selectTheme.valueProperty().isNull()).or(selectGeneration.valueProperty().isNull()));
    }

    /**
     * Listeners for each fxml element, when clicked perform set action.
     */
    private void onActions() {
        proceed.setOnAction(e -> {
            Player.THEME theme;
            if (selectTheme.getSelectionModel().getSelectedItem().equals("Default")) {
                theme = Player.THEME.SPRING;
            } else if (selectTheme.getSelectionModel().getSelectedItem().equals("Beach")) {
                theme = Player.THEME.BEACH;
            } else {
                theme = Player.THEME.CHRISTMAS;
            }

            Player.ItemGeneration generation;
            if (selectGeneration.getSelectionModel().getSelectedItem().equals("Periodic Generation")) {
                generation = Player.ItemGeneration.PERIODIC;
            } else {
                generation = Player.ItemGeneration.RANDOM;
            }

            try {
                new Player(playerName.getText(), theme, generation);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                StageFunctions.changeScene("main_menu", "Game Screen");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        music.setOnAction(e -> {
            StageFunctions.muteMusic();
            StageFunctions.toggleOpacity(musicImage);
        });

        sfx.setOnAction(e -> {
            StageFunctions.muteEffects();
            StageFunctions.toggleOpacity(effectsImage);
        });

        settings.setOnAction(e -> {
            try {
                StageFunctions.openSettings();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        minimize.setOnAction(e -> StageFunctions.minimize());

        maximise.setOnAction(e -> StageFunctions.maximise());

        exit.setOnAction(e -> {
            try {
                StageFunctions.exit();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}
