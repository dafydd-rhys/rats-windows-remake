package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.external.Audio;
import main.level.Level;
import main.level.LevelLoader;
import main.stage.StageFunctions;
import player.Player;

/**
 * Main
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 * @author Maurice Petersen (2013396)
 */
public class MainMenuController implements Initializable {

    /**  */
    @FXML private AnchorPane window;
    /**  */
    @FXML private JFXButton levels;
    /**  */
    @FXML private JFXButton loadGame;
    /**  */
    @FXML private JFXButton scoreboard;
    /**  */
    @FXML private JFXButton settings;
    /**  */
    @FXML private JFXButton sfx;
    /**  */
    @FXML private JFXButton music;
    /**  */
    @FXML private JFXButton minimize;
    /**  */
    @FXML private JFXButton maximise;
    /**  */
    @FXML private JFXButton exit;
    /**  */
    @FXML private JFXButton btnExit;
    /**  */
    @FXML private ImageView musicImage;
    /**  */
    @FXML private ImageView effectsImage;

    /**
     *
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onActions();
        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));
        if (!Player.hasSaveFile()) {
            loadGame.setDisable(true);
        }

    }

    /**
     *
     */
    private void onActions() {
        levels.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\level_select.fxml", "Level Select");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        loadGame.setOnAction(e -> {
            try {
                Level.currentLevel = LevelLoader.getCurrentLevel();
                Level.setIsSave(true);
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level " + Level.getCurrentLevel());
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }

        });

        scoreboard.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\scoreboard.fxml", "Scoreboard");
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
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

        btnExit.setOnAction(e -> {
            try {
                StageFunctions.exit();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}
