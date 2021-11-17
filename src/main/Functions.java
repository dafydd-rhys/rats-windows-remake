package main;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class Functions {

    public static void dragWindow(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();

        window.setOnMousePressed(pressEvent -> window.setOnMouseDragged(dragEvent -> {
            thisWindow.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            thisWindow.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
    }

    public static void openWindow(String path, String title) throws IOException {
        Parent window = FXMLLoader.load(new URL("file:/" + System.getProperty("user.dir") + path));
        Stage newWindow = new Stage();

        //ResizeHelper.addResizeListener(window);
        newWindow.setAlwaysOnTop(true);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setTitle(title);
        newWindow.setScene(new Scene(window));
        newWindow.show();
    }

    public static void minimize(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.setIconified(true);
    }

    public static void close(Stage stage) {
        stage.close();
    }

}
