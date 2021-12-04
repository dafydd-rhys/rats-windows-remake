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
import main.external.Audio;
import main.level.Level;
import main.stage.StageFunctions;
import player.Player;

/**
 * Level select controller - players can select what level they want to play.
 *
 * @author Gareth Wade (1901805)
 */
public class LevelSelectController implements Initializable {

    /**
     * JavaFX Button to change to corresponding scene.
     */
    @FXML
    private JFXButton level1;
    /**
     * JavaFX Button to change to corresponding scene.
     */
    @FXML
    private JFXButton level2;
    /**
     * JavaFX Button to change to corresponding scene.
     */
    @FXML
    private JFXButton level3;
    /**
     * JavaFX Button to change to corresponding scene.
     */
    @FXML
    private JFXButton level4;
    /**
     * JavaFX Button to change to corresponding scene.
     */
    @FXML
    private JFXButton level5;
    /**
     * JavaFX Button to change to corresponding scene.
     */
    @FXML
    private JFXButton level6;
    /**
     * JavaFX Button to change to corresponding scene.
     */
    @FXML
    private JFXButton mainMenuButton;
    /**
     * JavaFX Button to open corresponding scene.
     */
    @FXML
    private JFXButton settings;
    /**
     * JavaFX Button change volume values.
     */
    @FXML
    private JFXButton sfx;
    /**
     * JavaFX Button change volume values.
     */
    @FXML
    private JFXButton music;
    /**
     * JavaFX Button for minimizing application window.
     */
    @FXML
    private JFXButton minimize;
    /**
     * JavaFX Button for maximizing application window.
     */
    @FXML
    private JFXButton maximise;
    /**
     * JavaFX Button for terminating application window.
     */
    @FXML
    private JFXButton exit;
    /**
     * ImageView of music icon.
     */
    @FXML
    private ImageView musicImage;
    /**
     * ImageView of effect icon.
     */
    @FXML
    private ImageView effectsImage;

    /**
     * Level 1.
     */
    private static final int LEVEL_1 = 1;
    /**
     * Level 2.
     */
    private static final int LEVEL_2 = 2;
    /**
     * Level 3.
     */
    private static final int LEVEL_3 = 3;
    /**
     * Level 4.
     */
    private static final int LEVEL_4 = 4;
    /**
     * Level 5.
     */
    private static final int LEVEL_5 = 5;
    /**
     * Level 6.
     */
    private static final int LEVEL_6 = 6;

    /**
     * This method is run when this scene is loaded.
     *
     * @param url            url of resources.
     * @param resourceBundle bundle of resources.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        onActions();

        //disables locked levels
        int max = Player.getMaxLevel();
        if (max > LEVEL_1) {
            level2.setDisable(false);
        }
        if (max > LEVEL_2) {
            level3.setDisable(false);
        }
        if (max > LEVEL_3) {
            level4.setDisable(false);
        }
        if (max > LEVEL_4) {
            level5.setDisable(false);
        }
        if (max > LEVEL_5) {
            level6.setDisable(false);
        }

        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));
    }

    /**
     * Defines actions that occur when a button is clicked as listeners.
     */
    private void onActions() {
        //loads level 1.
        level1.setOnAction(e -> {
            try {
                Level.currentLevel = LEVEL_1;
                Level.setIsSave(false);
                StageFunctions.changeScene("game", "Level 1");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //loads level 2.
        level2.setOnAction(e -> {
            try {
                Level.currentLevel = LEVEL_2;
                Level.setIsSave(false);
                StageFunctions.changeScene("game", "Level 2");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //loads level 3.
        level3.setOnAction(e -> {
            try {
                Level.currentLevel = LEVEL_3;
                Level.setIsSave(false);
                StageFunctions.changeScene("game", "Level 3");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //loads level 4.
        level4.setOnAction(e -> {
            try {
                Level.currentLevel = LEVEL_4;
                Level.setIsSave(false);
                StageFunctions.changeScene("game", "Level 4");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //loads level 5.
        level5.setOnAction(e -> {
            try {
                Level.currentLevel = LEVEL_5;
                Level.setIsSave(false);
                StageFunctions.changeScene("game", "Level 5");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //loads level 6.
        level6.setOnAction(e -> {
            try {
                Level.currentLevel = LEVEL_6;
                Level.setIsSave(false);
                StageFunctions.changeScene("game", "Level 6");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //opens settings window.
        settings.setOnAction(e -> {
            try {
                StageFunctions.openSettings();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //change scene to menu scene.
        mainMenuButton.setOnAction(e -> {
            try {
                StageFunctions.changeScene("main_menu", "Main Menu");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //mutes music volume.
        music.setOnAction(e -> {
            StageFunctions.muteMusic();
            StageFunctions.toggleOpacity(musicImage);
        });

        //mutes effect volume.
        sfx.setOnAction(e -> {
            StageFunctions.muteEffects();
            StageFunctions.toggleOpacity(effectsImage);
        });

        //minimizes application to user taskbar.
        minimize.setOnAction(e -> StageFunctions.minimize());

        //maximizes application to user taskbar.
        maximise.setOnAction(e -> StageFunctions.maximise());

        //terminates application.
        exit.setOnAction(e -> {
            try {
                StageFunctions.exit();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}
