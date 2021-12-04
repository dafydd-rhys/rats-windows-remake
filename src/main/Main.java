package main;

import entity.rat.RatSprites;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.external.Audio;
import main.stage.StageFunctions;

/**
 * Main.java - used to initiate the program.
 *
 * @author Dafydd -Rhys Maund (2003900)
 */
public class Main extends Application {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * Method to start the fx implementation - loads first fxml.
     *
     * @param window the stage created.
     * @throws Exception throws if it can't load fxml.
     */
    @Override
    public void start(final Stage window) throws Exception {
        //loads all possible rat sprites (rotated)
        RatSprites.load();
        Platform.setImplicitExit(false);

        //sets stage and makes it draggable and resizable
        StageFunctions.setStage(window);
        StageFunctions.changeScene("main", "Player Entry Screen");

        //gets audio config and plays music
        Audio.getValues();
        Audio.playMusic();
    }

}
