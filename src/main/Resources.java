package main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.image.Image;

public class Resources {

    private static final String DIR = System.getProperty("user.dir");
    private static final String FXML_PATH = "\\src\\resources\\fxml\\";
    private static final String GAME_AUDIO_PATH = "\\src\\resources\\audio\\game\\";
    private static final String MENU_AUDIO_PATH = "\\src\\resources\\audio\\menu\\";
    private static final String CONFIG_PATH = "\\src\\resources\\config\\";
    private static final String LEVEL_CONFIG_PATH = "\\src\\resources\\config\\levels\\level";
    private static final String SPAWN_CONFIG_PATH = "\\src\\resources\\config\\spawns\\level";
    private static final String SAVES_CONFIG_PATH = "\\src\\resources\\config\\saves\\save-";
    private static final String SAVES_FOLDER_PATH = "\\src\\resources\\config\\saves\\";
    private static final String SCOREBOARD_CONFIG_PATH = "\\src\\resources\\config\\scoreboard\\scoreboard-level";
    private static final String IMAGES_PATH = "\\src\\resources\\images\\";
    private static final String ENTITY_IMAGES_PATH = "\\src\\resources\\images\\game\\entities\\";
    private static final String TITLE_IMAGES_PATH = "\\src\\resources\\images\\game\\tiles\\";
    private static final String MENU_IMAGES_PATH = "\\src\\resources\\images\\menu\\";
    private static final String PLAYERS = "\\src\\resources\\players\\";

    public static File getGameAudio(String path) {
        return new File(DIR + GAME_AUDIO_PATH + path + ".wav");
    }

    public static File getMenuAudio(String path) {
        return new File(DIR + MENU_AUDIO_PATH + path + ".wav");
    }

    public static String getSettings() {
        return DIR + CONFIG_PATH + "settings.txt";
    }

    public static File getLevel(int level) {
        return new File(DIR + LEVEL_CONFIG_PATH + level + ".txt");
    }

    public static File getSpawns(int level) {
        return new File(DIR + SPAWN_CONFIG_PATH + level + "-spawns.txt");
    }

    public static File getSaves(String name) {
        return new File(DIR + SAVES_CONFIG_PATH + name + ".txt");
    }

    public static File getScoreboard(int level) {
        return new File(DIR + SCOREBOARD_CONFIG_PATH + level + ".txt");
    }

    public static URL getFXML(String fxml) throws MalformedURLException {
        return new URL("file:/" + DIR + FXML_PATH + fxml + ".fxml");
    }

    public static Image getImage(String image) {
        return new Image(DIR + IMAGES_PATH + image + ".png");
    }

    public static Image getEntityImage(String image) {
        return new Image(DIR + ENTITY_IMAGES_PATH + image + ".png");
    }

    public static Image getTileImage(String image) {
        return new Image(DIR + TITLE_IMAGES_PATH + image + ".png");
    }

    public static Image getMenuImage(String image) {
        return new Image(DIR + MENU_IMAGES_PATH + image + ".png");
    }

    public static File getPlayers() {
        return new File(DIR + PLAYERS + "players.txt");
    }

    public static File getSavesFolderPath() {
        return new File(DIR + SAVES_FOLDER_PATH);
    }

}
