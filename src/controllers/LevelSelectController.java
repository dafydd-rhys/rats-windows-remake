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
import main.stage.StageFunctions;
import player.Player;

/**
 * Main
 *
 * @author Gareth Wade (1901805)
 */
public class LevelSelectController implements Initializable {

    /**  */
    @FXML private AnchorPane window;
    /**  */
    @FXML private JFXButton level1;
    /**  */
    @FXML private JFXButton level2;
    /**  */
    @FXML private JFXButton level3;
    /**  */
    @FXML private JFXButton level4;
    /**  */
    @FXML private JFXButton level5;
    /**  */
    @FXML private JFXButton level6;
    /**  */
    @FXML private JFXButton mainMenuButton;
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

        int max = Player.getMaxLevel();
        if (max > 1) {
            level2.setDisable(false);
        }
        if (max > 2) {
            level3.setDisable(false);
        }
        if (max > 3) {
            level4.setDisable(false);
        }
        if (max > 4) {
            level5.setDisable(false);
        }
        if (max > 5) {
            level6.setDisable(false);
        }

        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));
    }

    /**
     *
     */
    private void onActions() {
        level1.setOnAction(e -> {
            try {
                Level.currentLevel = 1;
                Level.setIsSave(false);
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 1");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        level2.setOnAction(e -> {
            try {
                Level.currentLevel = 2;
                Level.setIsSave(false);
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 2");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        level3.setOnAction(e -> {
            try {
                Level.currentLevel = 3;
                Level.setIsSave(false);
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 3");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        level4.setOnAction(e -> {
            try {
                Level.currentLevel = 4;
                Level.setIsSave(false);
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 4");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        level5.setOnAction(e -> {
            try {
                Level.currentLevel = 5;
                Level.setIsSave(false);
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 5");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        level6.setOnAction(e -> {
            try {
                Level.currentLevel = 6;
                Level.setIsSave(false);
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level 6");
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

        mainMenuButton.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\main_menu.fxml", "Main Menu");
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
