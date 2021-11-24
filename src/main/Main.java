package main;

import entity.rat.RatSprites;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.external.Audio;
import main.stage.StageFunctions;

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
        RatSprites.load();
        Platform.setImplicitExit(false);
        StageFunctions.setStage(window);
        StageFunctions.changeScene("\\src\\resources\\fxml\\main.fxml", "Player Entry Screen");
        Audio.getValues();
        Audio.playMusic();
    }

}
