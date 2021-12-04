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

public class ProfileController implements Initializable {

    /**
     *
     */
    @FXML
    private Text name;
    /**
     *
     */
    @FXML
    private Text max;
    /**
     *
     */
    @FXML
    private JFXButton delete;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText("Current Player: " + Player.getPlayerName());
        max.setText("Maximum Level: " + Player.getMaxLevel());

        delete.setOnAction(e -> {
            ConfirmDialog dialog = new ConfirmDialog();
            boolean result = dialog.getDecision("Deletion Warning!", "Are you sure you want to " +
                    "delete your profile?");

            if (result) {
                try {
                    Player.deletePlayer();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
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
