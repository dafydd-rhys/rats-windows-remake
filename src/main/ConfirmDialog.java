package main;

import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.external.Audio;
import main.stage.StageFunctions;

/**
 * Confirm dialog - used to get users response to alert.
 */
public class ConfirmDialog {

    /**
     * Constructor used to access alert.
     */
    public ConfirmDialog() {
    }

    /**
     * Gets whether used click yes or no.
     *
     * @param title title of alert
     * @param message message of alert
     * @return answer to alert
     */
    public boolean getDecision(final String title, final String message) {
        try {
            Audio.clickEffect();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }

        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.WARNING, message, yes, no);
        alert.initOwner(StageFunctions.getStage());
        alert.setTitle(title);

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(no) == yes;
    }

}
