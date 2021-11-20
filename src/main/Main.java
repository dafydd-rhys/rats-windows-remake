package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

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
        StageFunctions.setStage(window);
        StageFunctions.changeScene("\\src\\resources\\fxml\\main.fxml", "Player Entry Screen");
    }

}
