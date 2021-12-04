package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.ConfirmDialog;
import main.external.Audio;
import main.level.Level;
import main.level.LevelLoader;
import main.stage.StageFunctions;
import player.Player;

/**
 * MainMenu Controller - used to handle all interaction while in main menu
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 * @author Maurice Petersen (2013396)
 */
public class MainMenuController implements Initializable {

    /**
     * Goes to level select screen.
     */
    @FXML
    private JFXButton levels;
    /**
     * Loads game save file.
     */
    @FXML
    private JFXButton loadGame;
    /**
     * Goes to the scoreboard scene.
     */
    @FXML
    private JFXButton scoreboard;
    /**
     * Goes to the settings scene.
     */
    @FXML
    private JFXButton settings;
    /**
     * Button used to mute/un-mute sfx.
     */
    @FXML
    private JFXButton sfx;
    /**
     * Button used to mute/un-mute music.
     */
    @FXML
    private JFXButton music;
    /**
     * Button used to minimize window.
     */
    @FXML
    private JFXButton minimize;
    /**
     * Button used to maximise window.
     */
    @FXML
    private JFXButton maximise;
    /**
     * Button used to terminate program.
     */
    @FXML
    private JFXButton exit;
    /**
     * Button used to terminate program in the scene.
     */
    @FXML
    private JFXButton btnExit;
    /**
     * Goes to the profile editor scene.
     */
    @FXML
    private JFXButton profile;
    /**
     * Button used to sign out current user.
     */
    @FXML
    private JFXButton signOut;
    /**
     * Image used to represent music button.
     */
    @FXML
    private ImageView musicImage;
    /**
     * Image used to represent sfx button.
     */
    @FXML
    private ImageView effectsImage;

    /**
     * This method is run when the scene iis loaded.
     *
     * @param url            url of resources.
     * @param resourceBundle bundle of resources.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        onActions();

        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));

        //checks if player has load available.
        if (!Player.hasSaveFile()) {
            loadGame.setDisable(true);
        }
    }

    /**
     * This method adds listener to each fxml element.
     */
    private void onActions() {
        levels.setOnAction(e -> {
            try {
                StageFunctions.changeScene("level_select", "Level Select");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        loadGame.setOnAction(e -> {
            try {
                Level.setPaused(false);
                Level.currentLevel = LevelLoader.getCurrentLevel();
                Level.setIsSave(true);
                StageFunctions.changeScene("game", "Level " + Level.getCurrentLevel());
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }

        });

        scoreboard.setOnAction(e -> {
            try {
                StageFunctions.changeScene("scoreboard", "Scoreboard");
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

        profile.setOnAction(e -> {
            try {
                StageFunctions.openProfile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        signOut.setOnAction(e -> {
            ConfirmDialog dialog = new ConfirmDialog();
            boolean result = dialog.getDecision("Sign Out Warning!", "Are you sure you want to sign out?");

            //if yes
            if (result) {
                try {
                    StageFunctions.changeScene("main", "Player Entry Screen");
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
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
