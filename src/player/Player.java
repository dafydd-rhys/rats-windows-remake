package player;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import main.Resources;

/**
 * Player class, holds player information.
 *
 * @author Gareth Wade (1901805)
 * @author Dafydd Maund (2003900)
 */
public class Player {

    /**
     * Current player name.
     */
    private static String playerName;
    /**
     * Current player max level.
     */
    private static int maxLevel;
    /**
     * Current player theme.
     */
    private static THEME theme;
    /**
     * Current player generation.
     */
    private static ItemGeneration generation;

    /**
     * The enum Theme.
     */
    public enum THEME {
        /**
         * Spring theme.
         */
        SPRING(),
        /**
         * Beach theme.
         */
        BEACH(),
        /**
         * Christmas theme.
         */
        CHRISTMAS()
    }

    /**
     * The enum Item generation.
     */
    public enum ItemGeneration {
        /**
         * Periodic item generation.
         */
        PERIODIC(),
        /**
         * Random item generation.
         */
        RANDOM()
    }

    /**
     * Instantiates a new Player.
     *
     * @param paramName the player name
     * @param paramTheme    the theme
     * @param paramGeneration the generation
     * @throws IOException the io exception
     */
    public Player(final String paramName, final THEME paramTheme, final ItemGeneration paramGeneration)
            throws IOException {
        Player.playerName = paramName;
        Player.theme = paramTheme;
        Player.generation = paramGeneration;
        alreadyPlayed();
    }

    /**
     * Sets player name.
     *
     * @param paramName the player name
     */
    public static void setPlayerName(final String paramName) {
        Player.playerName = paramName;
    }

    /**
     * Gets player name.
     *
     * @return the player name
     */
    public static String getPlayerName() {
        return playerName;
    }

    /**
     * Sets theme.
     *
     * @param paramTheme the theme
     */
    public static void setTheme(final THEME paramTheme) {
        Player.theme = paramTheme;
    }

    /**
     * Gets theme.
     *
     * @return the theme
     */
    public static THEME getTheme() {
        return theme;
    }

    /**
     * Sets generation.
     *
     * @param paramGeneration the generation
     */
    public static void setGeneration(final ItemGeneration paramGeneration) {
        Player.generation = paramGeneration;
    }

    /**
     * Gets generation.
     *
     * @return the generation
     */
    public static ItemGeneration getGeneration() {
        return generation;
    }

    /**
     * Sets max level.
     *
     * @param max the max level
     */
    public static void setMaxLevel(final int max) {
        Player.maxLevel = max;
    }

    /**
     * Gets max level.
     *
     * @return the max level
     */
    public static int getMaxLevel() {
        return maxLevel;
    }


    /**
     * Unlocks new level for player.
     *
     * @param level the level to be unlocked
     * @throws IOException if there's error writing to file
     */
    public static void unlockedNew(final int level) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Resources.getPlayers()));
        ArrayList<String> existingPlayers = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.equals(playerName + ":" + (level - 1))) {
                existingPlayers.add(line);
            } else {
                existingPlayers.add(playerName + ":" + level);
                Player.setMaxLevel(level);
            }
        }

        //writes file with new unlocked level
        PrintWriter writer = new PrintWriter(Resources.getPlayers(), StandardCharsets.UTF_8);
        for (String player : existingPlayers) {
            writer.println(player);
        }
        writer.close();
    }

    /**
     * Gets already unlocked levels.
     *
     * @throws IOException if there's error writing to file
     */
    private void alreadyPlayed() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Resources.getPlayers()));

        //if player exist get player
        boolean found = false;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(":");
            if (Objects.equals(split[0], Player.playerName)) {
                found = true;
                Player.maxLevel = Integer.parseInt(split[1]);
            }
        }

        //if player doesn't exist create player
        if (!found) {
            FileWriter writer = new FileWriter(Resources.getPlayers(), true);
            Player.maxLevel = 1;
            writer.write(Player.playerName + ":" + Player.maxLevel + "\n");
            writer.close();
        }
    }


    /**
     * checks if there is a save file.
     *
     * @return save file
     */
    public static boolean hasSaveFile() {
        return checkForSaveFile(Resources.getSavesFolderPath());
    }

    /**
     * Read through saves folder and check if player has a save file.
     *
     * @param folder saves folder
     * @return boolean
     */
    public static boolean checkForSaveFile(final File folder) {
        boolean found;

        //checks directory to see if this player has a save file
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                checkForSaveFile(fileEntry);
            } else {
                String[] playerSplit = fileEntry.getName().split("-");
                String name = playerSplit[1].split("\\.")[0];
                found = name.equals(Player.getPlayerName());
                if (found) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * deletes current player.
     *
     * @throws IOException if there's error writing/reading to file
     */
    public static void deletePlayer() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Resources.getPlayers()));
        ArrayList<String> existingPlayers = new ArrayList<>();

        //reads file and adds all players apart from this one
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.equals(playerName + ":" + maxLevel)) {
                existingPlayers.add(line);
            }
        }

        //writes players without this one
        PrintWriter writer = new PrintWriter(Resources.getPlayers(), StandardCharsets.UTF_8);
        for (String player : existingPlayers) {
            writer.println(player);
        }
        writer.close();
    }

}
