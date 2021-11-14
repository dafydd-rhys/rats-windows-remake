package scoreboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {

    /*
    - this class shows the top 10 players
    - all public & static, will be accessed and used from scoreboard UI controller
     */
    private static final List<ScoreboardPlayer> scoreboardPlayers = new ArrayList<>();

    public List<ScoreboardPlayer> getScoreboard() {
        return scoreboardPlayers;
    }

    //loads scoreboard
    public static void load() throws IOException {
        scoreboardPlayers.clear();
        BufferedReader reader = new BufferedReader(new FileReader("src/resources/scoreboard.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] split = line.split(":");
            scoreboardPlayers.add(new ScoreboardPlayer(split[0], Integer.parseInt(split[1])));
        }
    }

    //add player to scoreboard
    public static void add(ScoreboardPlayer player) throws IOException {
        if (player.getScore() > scoreboardPlayers.get(scoreboardPlayers.size() - 1).getScore()) {
            if (scoreboardPlayers.size() > 9) {
                remove(scoreboardPlayers.get(scoreboardPlayers.size() - 1));
            }
            scoreboardPlayers.add(player);
            sort();
        }
    }

    //remove player from scoreboard
    public static void remove(ScoreboardPlayer player) {
        scoreboardPlayers.remove(player);
    }

    //sorts leaderboard (highest-to-lowest score)
    public static void sort() throws IOException {
        for (int i = 0; i < scoreboardPlayers.size(); i++) {
            int pos = i;
            for (int j = i + 1; j < scoreboardPlayers.size(); j++) {
                if (scoreboardPlayers.get(j).getScore() < scoreboardPlayers.get(pos).getScore()) {
                    pos = j;
                }
            }

            String tempName = scoreboardPlayers.get(pos).getName();
            int tempScore = scoreboardPlayers.get(pos).getScore();
            scoreboardPlayers.get(pos).setName(scoreboardPlayers.get(i).getName());
            scoreboardPlayers.get(pos).setScore(scoreboardPlayers.get(i).getScore());
            scoreboardPlayers.get(i).setName(tempName);
            scoreboardPlayers.get(i).setScore(tempScore);
        }
        Collections.reverse(scoreboardPlayers);
        writeToFile();
    }

    //updates scoreboard in file
    private static void writeToFile() throws IOException {
        PrintWriter writer = new PrintWriter("src/scoreboard.txt", StandardCharsets.UTF_8);

        for (ScoreboardPlayer player : scoreboardPlayers) {
            writer.println(player.getName() + ":" + player.getScore());
        }
        writer.close();
    }

}