package main.stage;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import main.external.Audio;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class StageFunctions {

    private static Stage stage;

    public static void setStage(Stage stage) {
        StageFunctions.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public static void changeScene(String path, String title) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Audio.clickEffect();
        Parent scene = FXMLLoader.load(new URL("file:/" + System.getProperty("user.dir") + path));

        stage.setMinHeight(530);
        stage.setMinWidth(800);
        stage.setAlwaysOnTop(true);
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        StageResizer.addResizeListener(stage);
        stage.show();
    }

    public static void openSettings() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Audio.clickEffect();
        Parent scene = FXMLLoader.load(new URL("file:/" + System.getProperty("user.dir") + "\\src\\resources\\fxml\\settings.fxml"));

        stage.setMinHeight(530);
        stage.setMinWidth(800);
        stage.setAlwaysOnTop(true);
        stage.setTitle("Settings");
        stage.setScene(new Scene(scene));
        StageResizer.addResizeListener(stage);
        stage.show();
    }

    public static void maximise() {
        stage.setMaximized(!stage.isMaximized());
    }

    public static void minimize() {
        stage.setIconified(true);
    }

    public static void exit() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Audio.clickEffect();
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit",
                "Close Program", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

    public static void exitGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Audio.clickEffect();
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit",
                "Close Program", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {

            System.exit(1);
        }
    }

}
