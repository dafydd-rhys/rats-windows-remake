package main.stage;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.ConfirmDialog;
import main.Resources;
import main.external.Audio;

/**
 * StageFunctions.java, host of all functions across all fxml.
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Dawid Wisniewski (857847)
 */
public class StageFunctions {

    /**
     * Main stage.
     */
    private static Stage stage;
    /**
     * settings stage.
     */
    private static Stage settingsStage;
    /**
     * game over stage.
     */
    private static Stage gameOverStage;
    /**
     * profile stage.
     */
    private static Stage profileStage;
    /**
     * main stage width.
     */
    private static final int STAGE_WIDTH = 800;
    /**
     * main stage height.
     */
    private static final int STAGE_HEIGHT = 530;
    /**
     * pop-out stage width.
     */
    private static final int POP_OUT_WIDTH = 400;
    /**
     * pop-out stage height.
     */
    private static final int POP_OUT_HEIGHT = 230;

    /**
     * Sets main stage.
     *
     * @param paramStage the stage
     */
    public static void setStage(final Stage paramStage) {
        StageFunctions.stage = paramStage;
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
    public static void changeScene(final String path, final String title) throws IOException,
            UnsupportedAudioFileException, LineUnavailableException {
        Audio.clickEffect();
        Parent scene = FXMLLoader.load(Resources.getFXML(path));

        stage.setMinHeight(STAGE_HEIGHT);
        stage.setMinWidth(STAGE_WIDTH);
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
    public static void openSettings() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Audio.clickEffect();
        StageFunctions.settingsStage = openPopOut(Resources.getFXML("settings"));
    }

    /**
     * Open game over.
     *
     * @throws IOException                   the io exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     */
    public static void openGameOver() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        StageFunctions.gameOverStage = openPopOut(Resources.getFXML("game_over"));
    }

    /**
     * Opens profile stage.
     *
     * @throws IOException can't find file
     */
    public static void openProfile() throws IOException {
        StageFunctions.profileStage = openPopOut(Resources.getFXML("profile"));
    }

    /**
     * Opens pop out stage for fxml.
     *
     * @param url url of stage
     * @return pop out stage
     * @throws IOException can't find file
     */
    private static Stage openPopOut(final URL url) throws IOException {
        Stage popStage = new Stage();
        Parent scene = FXMLLoader.load(url);

        popStage.setResizable(false);
        popStage.setMinHeight(POP_OUT_HEIGHT);
        popStage.setMinWidth(POP_OUT_WIDTH);
        popStage.setAlwaysOnTop(true);
        popStage.setTitle("Settings");
        popStage.initStyle(StageStyle.UNDECORATED);
        popStage.initModality(Modality.WINDOW_MODAL);
        popStage.initOwner(stage);
        popStage.setScene(new Scene(scene));
        popStage.show();
        StageResizer.addResizeListener(popStage);

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
    public static void exit() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Audio.clickEffect();
        ConfirmDialog dialog = new ConfirmDialog();

        boolean result = dialog.getDecision("Exit Warning", "Are you sure you want to exit?");
        if (result) {
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
    public static void exitSettings() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Audio.clickEffect();
        settingsStage.close();
    }

    /**
     * Exit settings.
     *
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public static void exitProfile() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Audio.clickEffect();
        profileStage.close();
    }

    /**
     * Exit game over.
     *
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public static void exitGameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Audio.clickEffect();
        gameOverStage.close();
    }

    /**
     * Toggle opacity.
     *
     * @param image the image
     */
    public static void toggleOpacity(final ImageView image) {
        final double disabled = 0.2;
        if (image.getOpacity() == disabled) {
            image.setOpacity(1);
        } else {
            image.setOpacity(disabled);
        }
    }

    /**
     * @return main stage
     */
    public static Stage getStage() {
        return stage;
    }

}
