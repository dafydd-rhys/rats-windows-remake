package main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.image.Image;

public class Resources {

    private final static String dir = System.getProperty("user.dir");
    private final static String gameAudio = "\\src\\resources\\audio\\game\\";
    private final static String menuAudio = "\\src\\resources\\audio\\menu\\";
    private final static String config = "\\src\\resources\\config\\";
    private final static String levelConfig = "\\src\\resources\\config\\levels\\level";
    private final static String spawnConfig = "\\src\\resources\\config\\spawns\\level";
    private final static String savesConfig = "\\src\\resources\\config\\saves\\save-";
    private final static String savesFolder = "\\src\\resources\\config\\saves\\";
    private final static String scoreboardConfig = "\\src\\resources\\config\\scoreboard\\scoreboard-level";
    private final static String fxmlPath = "\\src\\resources\\fxml\\";
    private final static String imagesPath = "\\src\\resources\\images\\";
    private final static String entityImages = "\\src\\resources\\images\\game\\entities\\";
    private final static String tileImages = "\\src\\resources\\images\\game\\tiles\\";
    private final static String menuImages = "\\src\\resources\\images\\menu\\";
    private final static String players = "\\src\\resources\\players\\";

    public static File getGameAudio(String path) {
        return new File(dir + gameAudio + path + ".wav");
    }

    public static File getMenuAudio(String path) {
        return new File(dir + menuAudio + path + ".wav");
    }

    public static String getSettings() {
        return dir + config + "settings.txt";
    }

    public static File getLevel(int level) {
        return new File(dir + levelConfig + level + ".txt");
    }

    public static File getSpawns(int level) {
        return new File(dir + spawnConfig + level + "-spawns.txt");
    }

    public static File getSaves(String name) {
        return new File(dir + savesConfig + name + ".txt");
    }

    public static File getScoreboard(int level) {
        return new File(dir + scoreboardConfig + level + ".txt");
    }

    public static URL getFXML(String fxml) throws MalformedURLException {
        return new URL("file:/" + dir + fxmlPath + fxml + ".fxml");
    }

    public static Image getImage(String image) {
        return new Image(dir + imagesPath + image + ".png");
    }

    public static Image getEntityImage(String image) {
        return new Image(dir + entityImages + image + ".png");
    }

    public static Image getTileImage(String image) {
        return new Image(dir + tileImages + image + ".png");
    }

    public static Image getMenuImage(String image) {
        return new Image(dir + menuImages + image + ".png");
    }

    public static File getPlayers() {
        return new File(dir + players + "players.txt");
    }

    public static File getSavesFolder() {
        return new File(dir + savesFolder);
    }

}
