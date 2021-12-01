package controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.level.Level;
import main.stage.StageFunctions;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import static main.external.Audio.playGameEffect;


public class GameOverController implements Initializable {

    @FXML
    private JFXButton sfx;
    @FXML
    private JFXButton music;
    @FXML
    private JFXButton exit;
    @FXML
    private JFXButton exitbtn;
    @FXML
    private ImageView musicImage;
    @FXML
    private ImageView effectsImage;
    @FXML
    private JFXButton play;
    @FXML
    private JFXButton restart;
    @FXML
    private JFXButton menu;
    @FXML
    private Text status;
    @FXML
    private Text score;

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

    public void playSound(String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {
            playGameEffect(System.getProperty("user.dir") + path);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private void onActions() {

        play.setOnAction(e -> {
            try {
                Level.setCurrentLevel(Level.getCurrentLevel() + 1);
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level " + Level.getCurrentLevel());
                StageFunctions.exitGameOver();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        restart.setOnAction(e -> {
            try {
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

        music.setOnAction(e -> {
            StageFunctions.muteMusic();
            StageFunctions.toggleOpacity(musicImage);
        });

        sfx.setOnAction(e -> {
            StageFunctions.muteEffects();
            StageFunctions.toggleOpacity(effectsImage);
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