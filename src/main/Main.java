package main;

import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        Platform.setImplicitExit(false);
        Parent startWindow;
        startWindow = FXMLLoader.load(new URL("file:/" + System.getProperty("user.dir") + "\\src\\resources\\fxml\\main.fxml"));

        //ResizeHelper.addResizeListener(window);
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(new Scene(startWindow, 800, 500));
        window.show();
    }

}
