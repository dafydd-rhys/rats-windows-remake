package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.ConfirmDialog;
import main.stage.StageFunctions;
import player.Player;

/**
 * Controller used to handle interaction with profile.fxml
 */
public class ProfileController implements Initializable {

    /**
     * Name of current player.
     */
    @FXML
    private Text name;
    /**
     * Max level of current player.
     */
    @FXML
    private Text max;
    /**
     * Button used to delete current player.
     */
    @FXML
    private JFXButton delete;
    /**
     * Minimize button used to minimize window.
     */
    @FXML
    private JFXButton minimize;
    /**
     * Exit button used to close this scene.
     */
    @FXML
    private JFXButton exit;

    /**
     * This method is run upon this scene being loaded.
     *
     * Menu icons created by group
     *
     * @param url url of resources.
     * @param resourceBundle bundle of resources.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        //sets player attributes
        name.setText("Current Player: " + Player.getPlayerName());
        max.setText("Maximum Level: " + Player.getMaxLevel());

        delete.setOnAction(e -> {
            ConfirmDialog dialog = new ConfirmDialog();
            boolean result = dialog.getDecision("Deletion Warning!", "Are you sure you want to "
                    + "delete your profile?");

            //if yes, delete current player
            if (result) {
                try {
                    Player.deletePlayer();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    //close scene and logout
                    StageFunctions.exitProfile();
                    StageFunctions.changeScene("main", "Player Entry Screen");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        minimize.setOnAction(e -> StageFunctions.minimize());

        exit.setOnAction(e -> {
            try {
                StageFunctions.exitProfile();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}
