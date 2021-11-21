package controllers;

import com.jfoenix.controls.JFXButton;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.Audio;
import main.StageFunctions;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Settings Controller
 *
 * @author Gareth Wade (1901805)
 * @author Dafydd Maund (2003900)
 */
public class SettingsController implements Initializable {

    private Media music;
    private Media click;
    private MediaPlayer musicPlayer;
    private MediaPlayer clickPlayer;

    @FXML
    private AnchorPane window;
    @FXML
    private Slider musicSlider;
    @FXML
    private Slider sfxSlider;
    @FXML
    private JFXButton musicTest;
    @FXML
    private JFXButton sfxTest;
    @FXML
    private JFXButton mainMenuButton;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton maximise;
    @FXML
    private JFXButton exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicSlider.setValue(Audio.getMusic());
        sfxSlider.setValue(Audio.getEffects());
        onActions();
        setImages();
        //Current file path needs rewriting (only works locally for Gareth Wade)
        //music = new Media(new File("D:\\Documents\\GitHub\\rats-windows-remake\\src\\resources\\audio\\menu\\music.wav").toURI().toString());
        //musicPlayer = new MediaPlayer(music);
        //musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        //click = new Media(new File("D:\\Documents\\GitHub\\rats-windows-remake\\src\\resources\\audio\\menu\\click.mp3").toURI().toString());
    }

    private void onActions() {
        mainMenuButton.setOnAction(e -> {
            try {
                StageFunctions.changeScene("\\src\\resources\\fxml\\main_menu.fxml", "Main Menu");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });
        musicSlider.valueProperty().addListener((ov, old, value) -> {
            try {
                Audio.setMusic(value.floatValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sfxSlider.valueProperty().addListener((ov, old, value) -> {
            try {
                Audio.setEffects(value.floatValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        /*
        musicTest.setOnAction(e -> {
            if(musicPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                musicPlayer.pause();
            }
            else {
                musicPlayer.play();
            }
        });
        sfxTest.setOnAction(e -> {
            clickPlayer = new MediaPlayer(click);
            clickPlayer.play();
        });
         */
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

    private void setImages() {

    }

}
