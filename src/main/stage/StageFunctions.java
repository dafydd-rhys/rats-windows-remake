package main.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.external.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.URL;

/**
 * Main
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski (857847)
 */
public class StageFunctions {

    /**
     *
     */
    private static Stage stage;
    /**
     *
     */
    private static Stage settingsStage;
    /**
     *
     */
    private static Stage gameOverStage;

    /**
     * Sets stage.
     *
     * @param stage the stage
     */
    public static void setStage(Stage stage) {
        StageFunctions.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
    }

    /**
     * Change scene.
     *
     * @param path  the path
     * @param title the title
     * @throws IOException                   the io exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     */
    public static void changeScene(String path, String title)
            throws IOException, UnsupportedAudioFileException,
            LineUnavailableException {
        Audio.clickEffect();
        Parent scene = FXMLLoader.load(
                new URL("file:/" + System.getProperty("user.dir") + path));

        stage.setMinHeight(530);
        stage.setMinWidth(800);
        stage.setAlwaysOnTop(true);
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        StageResizer.addResizeListener(stage);
        stage.show();
    }

    /**
     * Open settings.
     *
     * @throws IOException                   the io exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     */
    public static void openSettings()
            throws IOException, UnsupportedAudioFileException,
            LineUnavailableException {
        Audio.clickEffect();
        StageFunctions.settingsStage =
                openPopOut(new URL("file:/" + System.getProperty("user.dir") +
                        "\\src\\resources\\fxml\\settings.fxml"));
    }

    /**
     * Open game over.
     *
     * @throws IOException                   the io exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     */
    public static void openGameOver()
            throws IOException, UnsupportedAudioFileException,
            LineUnavailableException {
        StageFunctions.gameOverStage =
                openPopOut(new URL("file:/" + System.getProperty("user.dir")
                        + "\\src\\resources\\fxml\\game_over.fxml"));
    }

    private static Stage openPopOut(URL url) throws IOException {
        Stage popStage = new Stage();
        Parent scene = FXMLLoader.load(url);

        popStage.setResizable(false);
        popStage.setMinHeight(230);
        popStage.setMinWidth(400);
        popStage.setAlwaysOnTop(true);
        popStage.setTitle("Settings");
        popStage.initStyle(StageStyle.UNDECORATED);
        popStage.initModality(Modality.WINDOW_MODAL);
        popStage.initOwner(stage);
        popStage.setScene(new Scene(scene));
        popStage.show();

        return popStage;
    }


    /**
     * Mute music.
     */
    public static void muteMusic() {
        if (Audio.isMusicMuted()) {
            Audio.resumeMusic();
        } else {
            Audio.muteMusic();
        }
    }

    /**
     * Mute effects.
     */
    public static void muteEffects() {
        if (Audio.isEffectsMuted()) {
            Audio.resumeEffects();
        } else {
            Audio.muteEffects();
        }
    }

    /**
     * Maximise.
     */
    public static void maximise() {
        stage.setMaximized(!stage.isMaximized());
    }

    /**
     * Minimize.
     */
    public static void minimize() {
        stage.setIconified(true);
    }

    /**
     * Minimize settings.
     */
    public static void minimizeSettings() {
        settingsStage.setIconified(true);
    }

    /**
     * Exit.
     *
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public static void exit()
            throws UnsupportedAudioFileException, LineUnavailableException,
            IOException {
        Audio.clickEffect();
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        int reply = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to exit",
                "Close Program", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

    /**
     * Exit settings.
     *
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public static void exitSettings()
            throws UnsupportedAudioFileException, LineUnavailableException,
            IOException {
        Audio.clickEffect();
        settingsStage.close();
    }

    /**
     * Exit game over.
     *
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public static void exitGameOver()
            throws UnsupportedAudioFileException, LineUnavailableException,
            IOException {
        Audio.clickEffect();
        gameOverStage.close();
    }

    /**
     * Toggle opacity.
     *
     * @param image the image
     */
    public static void toggleOpacity(ImageView image) {
        if (image.getOpacity() == 0.2) {
            image.setOpacity(1);
        } else {
            image.setOpacity(0.2);
        }
    }

    /**
     * Exit game.
     *
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public static void exitGame()
            throws UnsupportedAudioFileException, LineUnavailableException,
            IOException {
        Audio.clickEffect();
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        int reply = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to exit",
                "Close Program", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

}
