package main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.image.Image;

/**
 * Resource.java - used to get resources throughout the program.
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class Resources {

    /**
     * Projects directory.
     */
    private static final String DIR = System.getProperty("user.dir");
    /**
     * fxml path directory.
     */
    private static final String FXML_PATH = "\\src\\resources\\fxml\\";
    /**
     * game audio directory.
     */
    private static final String GAME_AUDIO_PATH = "\\src\\resources\\audio\\game\\";
    /**
     * menu audio directory.
     */
    private static final String MENU_AUDIO_PATH = "\\src\\resources\\audio\\menu\\";
    /**
     * config path directory.
     */
    private static final String CONFIG_PATH = "\\src\\resources\\config\\";
    /**
     * level path directory.
     */
    private static final String LEVEL_CONFIG_PATH = "\\src\\resources\\config\\levels\\level";
    /**
     * saves directory.
     */
    private static final String SAVES_CONFIG_PATH = "\\src\\resources\\config\\saves\\save-";
    /**
     * saves folder directory.
     */
    private static final String SAVES_FOLDER_PATH = "\\src\\resources\\config\\saves\\";
    /**
     * scoreboard directory.
     */
    private static final String SCOREBOARD_CONFIG_PATH = "\\src\\resources\\config\\scoreboard\\scoreboard-level";
    /**
     * images directory.
     */
    private static final String IMAGES_PATH = "\\src\\resources\\images\\";
    /**
     * entity image directory.
     */
    private static final String ENTITY_IMAGES_PATH = "\\src\\resources\\images\\game\\entities\\";
    /**
     * tiles image directory.
     */
    private static final String TILES_IMAGES_PATH = "\\src\\resources\\images\\game\\tiles\\";
    /**
     * menu images directory.
     */
    private static final String MENU_IMAGES_PATH = "\\src\\resources\\images\\menu\\";
    /**
     * players directory.
     */
    private static final String PLAYERS = "\\src\\resources\\players\\";

    /**
     * Gets game audio.
     *
     * @param path path of wanted file.
     * @return returns file.
     */
    public static File getGameAudio(final String path) {
        return new File(DIR + GAME_AUDIO_PATH + path + ".wav");
    }

    /**
     * Gets menu audio.
     *
     * @param path path of wanted file.
     * @return returns file.
     */
    public static File getMenuAudio(final String path) {
        return new File(DIR + MENU_AUDIO_PATH + path + ".wav");
    }


    /**
     * Retrieves settings file.
     *
     * @return settings file.
     */
    public static String getSettings() {
        return DIR + CONFIG_PATH + "settings.txt";
    }

    /**
     * Retrieves wanted level.
     *
     * @param level level number.
     * @return returns file.
     */
    public static File getLevel(final int level) {
        return new File(DIR + LEVEL_CONFIG_PATH + level + ".txt");
    }

    /**
     * Retrieves saves.
     *
     * @param name name of wanted file.
     * @return returns file.
     */
    public static File getSaves(final String name) {
        return new File(DIR + SAVES_CONFIG_PATH + name + ".txt");
    }

    /**
     * Retrieves scoreboard.
     *
     * @param level level number.
     * @return returns file.
     */
    public static File getScoreboard(final int level) {
        return new File(DIR + SCOREBOARD_CONFIG_PATH + level + ".txt");
    }

    /**
     * Retrieves fxml.
     *
     * @param fxml path of wanted file.
     * @return returns URL of fxml.
     * @throws MalformedURLException url does not exist.
     */
    public static URL getFXML(final String fxml) throws MalformedURLException {
        return new URL("file:/" + DIR + FXML_PATH + fxml + ".fxml");
    }

    /**
     * Retrieves image.
     *
     * @param image name of wanted file.
     * @return returns file.
     */
    public static Image getImage(final String image) {
        return new Image(DIR + IMAGES_PATH + image + ".png");
    }

    /**
     * Retrieves entity image.
     *
     * @param image name of wanted file.
     * @return returns file.
     */
    public static Image getEntityImage(final String image) {
        return new Image(DIR + ENTITY_IMAGES_PATH + image + ".png");
    }

    /**
     * Retrieves tile image.
     *
     * @param image name of wanted file.
     * @return returns file.
     */
    public static Image getTileImage(final String image) {
        return new Image(DIR + TILES_IMAGES_PATH + image + ".png");
    }

    /**
     * Retrieves menu image.
     *
     * @param image name of wanted file.
     * @return returns file.
     */
    public static Image getMenuImage(final String image) {
        return new Image(DIR + MENU_IMAGES_PATH + image + ".png");
    }

    /**
     * Retrieves players.
     *
     * @return returns file.
     */
    public static File getPlayers() {
        return new File(DIR + PLAYERS + "players.txt");
    }

    /**
     * Retrieves save folder.
     *
     * @return returns file.
     */
    public static File getSavesFolderPath() {
        return new File(DIR + SAVES_FOLDER_PATH);
    }

}
