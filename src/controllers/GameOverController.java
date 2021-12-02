package controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.level.Level;
import main.stage.StageFunctions;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import static main.external.Audio.playGameEffect;
import player.Player;


/**
 * Controller used to handle the game over screen.
 */
public class GameOverController implements Initializable {

    /** */
    @FXML private JFXButton exit;
    /** */
    @FXML private JFXButton exitbtn;
    /** */
    @FXML private JFXButton play;
    /** */
    @FXML private JFXButton restart;
    /** */
    @FXML private JFXButton menu;
    /** */
    @FXML private Text status;
    /** */
    @FXML private Text score;

    /**
     *
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Level.getGameOver() && Level.getGameWon()) {
            status.setText("You Won!");

            try {
                playSound("/src/resources/audio/game/level-win.wav");
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            if (Level.getCurrentLevel() + 1 <= 6) {
                play.setDisable(false);
                if (Player.getMaxLevel() < Level.getCurrentLevel() + 1) {
                    try {
                        Player.unlockedNew(Level.getCurrentLevel() + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (Level.getGameOver()) {
            status.setText("You Lost!");
            try {
                playSound("/src/resources/audio/game/level-fail.wav");
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
        score.setText("Score: " + Level.getScore());

        onActions();
    }

    /**
     * Play sound.
     *
     * @param path the path
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public void playSound(String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {
            playGameEffect(System.getProperty("user.dir") + path);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void onActions() {
        play.setOnAction(e -> {
            try {
                Level.setCurrentLevel(Level.getCurrentLevel() + 1);
                Level.setIsSave(false);
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level " + Level.getCurrentLevel());
                StageFunctions.exitGameOver();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        restart.setOnAction(e -> {
            try {
                Level.setIsSave(false);;
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level " + Level.getCurrentLevel());
                StageFunctions.exitGameOver();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        menu.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\main_menu.fxml", "Main Menu");
                StageFunctions.exitGameOver();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        exit.setOnAction(e -> {
            try {
                StageFunctions.exitGameOver();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });

        exitbtn.setOnAction(e -> {
            try {
                StageFunctions.exitGameOver();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}