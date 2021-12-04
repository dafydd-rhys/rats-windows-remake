package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.external.Audio;
import main.level.Level;
import main.stage.StageFunctions;

/**
 * Settings Controller - handles all interaction in settings.fxml.
 *
 * @author Gareth Wade (1901805)
 * @author Dafydd Maund (2003900)
 * @author Dawid Wisniewski (857847)
 */
public class SettingsController implements Initializable {
    
    /**
     * Slider for music volume.
     */
    @FXML
    private Slider musicSlider;
    /**
     * Slider for effects volume.
     */
    @FXML
    private Slider sfxSlider;
    /**
     * JavaFX Button for minimizing application window.
     */
    @FXML
    private JFXButton minimize;
    /**
     * JavaFX Button for closing scene.
     */
    @FXML
    private JFXButton exit;

    /**
     * This method is run upon scene being loaded.
     *
     * @param url the url of resources.
     * @param resourceBundle bundle of resources.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        //adds listeners to controls
        onActions();
        musicSlider.setValue(Audio.getMusic());
        sfxSlider.setValue(Audio.getEffects());
    }

    /**
     * Defines actions that occur when a button is clicked as listeners.
     */
    private void onActions() {
        // slider sets the music volume to the value on the slider when it is moved by the user.
        musicSlider.valueProperty().addListener((ov, old, value) -> {
            try {
                Audio.setMusic(value.floatValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // slider sets the effect volume to the value on the slider when it is moved by the user.
        sfxSlider.valueProperty().addListener((ov, old, value) -> {
            try {
                Audio.setEffects(value.floatValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // minimizes scene to user taskbar.
        minimize.setOnAction(e -> StageFunctions.minimizeSettings());

        // closes settings window.
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
