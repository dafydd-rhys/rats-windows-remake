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
public class Functions {

    public static void openWindow(String path, String title) throws IOException {
        Parent window = FXMLLoader.load(new URL("file:/" + System.getProperty("user.dir") + path));
        Stage newWindow = new Stage();

        newWindow.setAlwaysOnTop(true);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setTitle(title);
        newWindow.setScene(new Scene(window));
        ResizeHelper.addResizeListener(newWindow);
        newWindow.show();
    }

    public static void maximise(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.setMaximized(!thisWindow.isMaximized());
    }

    public static void minimize(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.setIconified(true);
    }

    public static void close(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.close();
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
