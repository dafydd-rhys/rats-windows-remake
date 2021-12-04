package controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import main.Resources;
import main.level.Level;
import main.stage.StageFunctions;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import static main.external.Audio.playGameEffect;
import player.Player;

/**
 * Controller used to handle the game over screen.
 *
 * @author Gareth Wade (1901805)
 * @author Chunyuan Zhang
 */
public class GameOverController implements Initializable {

    /**
     * JavaFX Button for closing the stage window.
     */
    @FXML
    private JFXButton exit;
    /**
     * JavaFX Button for terminating application window.
     */
    @FXML
    private JFXButton exitbtn;
    /**
     * JavaFX Button for proceeding to next scene.
     */
    @FXML
    private JFXButton play;
    /**
     * JavaFX Button for resetting current scene.
     */
    @FXML
    private JFXButton restart;
    /**
     * JavaFX Button for proceeding to menu scene.
     */
    @FXML
    private JFXButton menu;
    /**
     * JavaFX Text to show application status.
     */
    @FXML
    private Text status;
    /**
     * JavaFX Text to show application score.
     */
    @FXML
    private Text score;

    /**
     * represents max level for player to reach.
     */
    private static final int MAX_LEVEL = 6;

    /**
     * This method is run whenever this scene is loaded
     *
     * @param url            url of resources.
     * @param resourceBundle bundle of resources.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        //if level was won
        if (Level.getGameOver() && Level.getGameWon()) {
            status.setText("You Won!");

            try {
                playSound("level-win");
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }

            //if there is next level to be unlocked
            if (Level.getCurrentLevel() + 1 <= MAX_LEVEL) {
                play.setDisable(false);
                if (Player.getMaxLevel() < Level.getCurrentLevel() + 1) {
                    try {
                        //unlock new level for player
                        Player.unlockedNew(Level.getCurrentLevel() + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (Level.getGameOver()) {
            status.setText("You Lost!");
            try {
                playSound("level-fail");
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
        score.setText("Score: " + Level.getScore());

        //listeners on fxml objects
        onActions();
    }

    /**
     * Play sound of passed path.
     *
     * @param path the path of sound
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public void playSound(final String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {
            //plays sound effect
            playGameEffect(Resources.getGameAudio(path));
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Defines actions that occur when a button is clicked as listeners.
     */
    private void onActions() {
        //changes current players max level +=1 and begins the next level.
        play.setOnAction(e -> {
            try {
                Level.setCurrentLevel(Level.getCurrentLevel() + 1);
                Level.setIsSave(false);
                StageFunctions.changeScene("game", "Level " + Level.getCurrentLevel());
                StageFunctions.exitGameOver();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //reloads the current level from file.
        restart.setOnAction(e -> {
            try {
                Level.setIsSave(false);
                StageFunctions.changeScene("game", "Level " + Level.getCurrentLevel());
                StageFunctions.exitGameOver();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //change scene to menu scene.
        menu.setOnAction(e -> {
            try {
                StageFunctions.changeScene("main_menu", "Main Menu");
                StageFunctions.exitGameOver();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        //closes game over window.
        exit.setOnAction(e -> {
            try {
                StageFunctions.exitGameOver();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });

        //terminates application.
        exitbtn.setOnAction(e -> {
            try {
                StageFunctions.exit();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}
