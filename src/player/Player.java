package player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * Player
 *
 * @author Gareth Wade (1901805)
 * @author Dafydd Maund (2003900)
 */

public class Player {

    private static final String players = System.getProperty("user.dir") + "\\src\\resources\\config\\players.txt";
    private static String playerName;
    private static int maxLevel;
    private static THEME theme;

    public enum THEME {
        SPRING(),
        BEACH(),
        CHRISTMAS()
    }

    public Player(String playerName, THEME theme) throws IOException {
        Player.playerName = playerName;
        Player.theme = theme;
        alreadyPlayed();
    }

    public static void setPlayerName(String playerName) {
        Player.playerName = playerName;
    }

    public static String getPlayerName() {
        return playerName;
    }

    public static void setTheme(THEME theme) {
        Player.theme = theme;
    }

    public static THEME getTheme() {
        return theme;
    }

    public static void setMaxLevel(int maxLevel) {
        Player.maxLevel = maxLevel;
    }

    public static int getMaxLevel() {
        return maxLevel;
    }

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
            FileWriter writer = new FileWriter(players,true);
            Player.maxLevel = 1;
            writer.write(Player.playerName + ":" + Player.maxLevel + "\n");
            writer.close();
        }
    }

}