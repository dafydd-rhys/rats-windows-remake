package controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.external.Audio;
import main.stage.StageFunctions;

import javafx.scene.media.Media;

/**
 * Settings Controller
 *
 * @author Gareth Wade (1901805)
 * @author Dafydd Maund (2003900)
 */
public class SettingsController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private Slider musicSlider;
    @FXML
    private Slider sfxSlider;
    @FXML
    private JFXButton music;
    @FXML
    private JFXButton sfx;
    @FXML
    private JFXButton mainMenuButton;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton maximise;
    @FXML
    private JFXButton exit;
    @FXML
    private ImageView musicImage;
    @FXML
    private ImageView effectsImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicSlider.setValue(Audio.getMusic());
        sfxSlider.setValue(Audio.getEffects());
        onActions();
        setImages();
    }

    private void onActions() {
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
        minimize.setOnAction(e -> StageFunctions.minimize());
        exit.setOnAction(e -> {
            try {
                StageFunctions.exitSettings();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setImages() {

    }

}
