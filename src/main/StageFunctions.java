package main;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.*;

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

    public static void changeScene(String path, String title) throws IOException {
        Parent scene = FXMLLoader.load(new URL("file:/" + System.getProperty("user.dir") + path));

        stage.setMinHeight(530);
        stage.setMinWidth(800);
        stage.setAlwaysOnTop(true);
        stage.setTitle(title);
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

    public static void exit() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit",
                "Close Program", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

    public static void exitGame() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit",
                "Close Program", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {

            System.exit(1);
        }
    }

}
