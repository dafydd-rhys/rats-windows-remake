package player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Player
 *
 * @author Chunyuan Zhang (2131205)
 * @author Gareth Wade (1901805)
 */

public class Player {

    private static final String players = System.getProperty("user.dir") + "\\src\\resources\\config\\players.txt";
    private static String playerName;
    private static String themeChoice;
    private static int maxLevel;

    public Player(String playerName, String themeChoice) throws IOException {
        Player.playerName = playerName;
        Player.themeChoice = themeChoice;
        alreadyPlayed();
    }

    public void setPlayerName(String playerName) {
        Player.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setThemeChoice(String themeChoice) {
        Player.themeChoice = themeChoice;
    }

    public String getThemeChoice() {
        return themeChoice;
    }

    public void setMaxLevel(int maxLevel) {
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
            PrintWriter writer = new PrintWriter(players, StandardCharsets.UTF_8);
            writer.println(Player.playerName + ":" + 1);
            writer.close();
        }
    }

}