package player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Player
 *
 * @author Gareth Wade (1901805)
 * @author Dafydd Maund (2003900)
 */
public class Player {

    /** */
    private static final String players = System.getProperty("user.dir") + "\\src\\resources\\players\\players.txt";
    /** */
    private static String playerName;
    /** */
    private static int maxLevel;
    /** */
    private static THEME theme;
    /** */
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
     * @param playerName the player name
     * @param theme      the theme
     * @param generation the generation
     * @throws IOException the io exception
     */
    public Player(String playerName, THEME theme, ItemGeneration generation) throws IOException {
        Player.playerName = playerName;
        Player.theme = theme;
        Player.generation = generation;
        alreadyPlayed();
    }

    /**
     * Sets player name.
     *
     * @param playerName the player name
     */
    public static void setPlayerName(String playerName) {
        Player.playerName = playerName;
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
     * @param theme the theme
     */
    public static void setTheme(THEME theme) {
        Player.theme = theme;
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
     * @param generation the generation
     */
    public static void setGeneration(ItemGeneration generation) {
        Player.generation = generation;
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
     * @param maxLevel the max level
     */
    public static void setMaxLevel(int maxLevel) {
        Player.maxLevel = maxLevel;
    }

    /**
     * Gets max level.
     *
     * @return the max level
     */
    public static int getMaxLevel() {
        return maxLevel;
    }

    public static void unlockedNew(int level) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(players));
        ArrayList<String> existingPlayers = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            if(!line.equals(playerName + ":" + (level - 1))) {
                existingPlayers.add(line);
            } else {
                existingPlayers.add(playerName + ":" + level);
                Player.setMaxLevel(level);
            }
        }

        PrintWriter writer = new PrintWriter(players, StandardCharsets.UTF_8);
        for (String player : existingPlayers) {
            System.out.println(player);
            writer.println(player);
        }
        writer.close();
    }

    /**
     *
     *
     * @throws IOException
     */
    private void alreadyPlayed() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(players));

        boolean found = false;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(":");
            if (Objects.equals(split[0], Player.playerName)) {
                found = true;
                Player.maxLevel = Integer.parseInt(split[1]);
            }
        }

        if (!found) {
            FileWriter writer = new FileWriter(players, true);
            Player.maxLevel = 1;
            writer.write(Player.playerName + ":" + Player.maxLevel + "\n");
            writer.close();
        }
    }

    public static boolean hasSaveFile() {
        return checkforSaveFile(new File("src/resources/config/saves/"));
    }

    /**
     * Read through saves folder and check if player has a save file
     * @param folder saves folder
     * @return boolean
     */
    public static boolean checkforSaveFile(File folder) {
        boolean found = false;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                checkforSaveFile(fileEntry);
            } else {
                String[] playerSplit = fileEntry.getName().split("-");
                String playerName = playerSplit[1].split("\\.")[0];
                found = playerName.equals(Player.getPlayerName());
                if (found) {
                    return true;
                }
            }
        }

        return false;
    }

}