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
import javafx.scene.layout.AnchorPane;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.external.Audio;
import main.stage.StageFunctions;
import player.Player;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private ComboBox<String> selectTheme;
    @FXML
    private JFXButton sfx;
    @FXML
    private JFXButton music;
    @FXML
    private JFXButton settings;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton maximise;
    @FXML
    private JFXButton exit;
    @FXML
    private TextField playerName;
    @FXML
    private JFXButton proceed;
    @FXML
    private ImageView musicImage;
    @FXML
    private ImageView effectsImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listeners();
        onActions();

        settings.setDisable(true);
        selectTheme.getItems().addAll("Default", "Beach", "Christmas");
        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));
    }

    private void listeners() {
        proceed.disableProperty().bind(Bindings.isEmpty(playerName.textProperty())
                .or(selectTheme.valueProperty().isNull()));
    }

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

            try {
                new Player(playerName.getText(), theme);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\main_menu.fxml", "Game Screen");
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
