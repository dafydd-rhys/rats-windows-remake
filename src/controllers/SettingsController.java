package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.external.Audio;
import main.level.Level;
import main.stage.StageFunctions;

/**
 * Settings Controller
 *
 * @author Gareth Wade (1901805)
 * @author Dafydd Maund (2003900)
 * @author Dawid Wisniewski (857847)
 */
public class SettingsController implements Initializable {

    /**
     *
     */
    @FXML
    private AnchorPane window;
    /**
     *
     */
    @FXML
    private Slider musicSlider;
    /**
     *
     */
    @FXML
    private Slider sfxSlider;
    /**
     *
     */
    @FXML
    private JFXButton minimize;
    /**
     *
     */
    @FXML
    private JFXButton exit;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onActions();
        musicSlider.setValue(Audio.getMusic());
        sfxSlider.setValue(Audio.getEffects());
    }

    /**
     *
     */
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

        minimize.setOnAction(e -> StageFunctions.minimizeSettings());

        exit.setOnAction(e -> {
            try {
                Level.setPaused(false);
                StageFunctions.exitSettings();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}
