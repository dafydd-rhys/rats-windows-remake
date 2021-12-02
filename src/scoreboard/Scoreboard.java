package scoreboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Scoreboard
 *
 * @author Dafydd -Rhys Maund (2003900)
 */
public class Scoreboard {

    /**
     *
     */
    private final int level;
    /**
     *
     */
    private final List<ScoreboardPlayer> scoreboardPlayers = new ArrayList<>();
    /**
     *
     */
    private final String directory;

    /**
     * Instantiates a new Scoreboard.
     *
     * @param level the level
     * @throws IOException the io exception
     */
    public Scoreboard(int level) throws IOException {
        this.level = level;
        this.directory =
                "src/resources/config/scoreboard/scoreboard-level" + level +
                        ".txt";
    }

    /**
     * Gets scoreboard.
     *
     * @return the scoreboard
     * @throws IOException the io exception
     */
    public List<ScoreboardPlayer> getScoreboard() throws IOException {
        scoreboardPlayers.clear();
        BufferedReader reader = new BufferedReader(new FileReader(directory));

        int count = 1;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(":");
            scoreboardPlayers.add(
                    new ScoreboardPlayer("Level " + level, count, split[0],
                            Integer.parseInt(split[1])));
            count++;
        }

        return scoreboardPlayers;
    }

    /**
     * Gets all.
     *
     * @return the all
     * @throws IOException the io exception
     */
    public ArrayList<ScoreboardPlayer> getAll() throws IOException {
        ArrayList<ScoreboardPlayer> allScoreboardPlayers = new ArrayList<>();

        for (int i = 1; i < 7; i++) {
            String dir =
                    "src/resources/config/scoreboard/scoreboard-level" + i +
                            ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(dir));

            int count = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                allScoreboardPlayers.add(
                        new ScoreboardPlayer("Level " + i, count, split[0],
                                Integer.parseInt(split[1])));
                count++;
            }
        }
        return allScoreboardPlayers;
    }

    /**
     * Add.
     *
     * @param player the player
     * @throws IOException the io exception
     */
    public void add(ScoreboardPlayer player) throws IOException {
        if (player.getScore() >
                scoreboardPlayers.get(scoreboardPlayers.size() - 1)
                        .getScore()) {
            if (scoreboardPlayers.size() > 9) {
                remove(scoreboardPlayers.get(scoreboardPlayers.size() - 1));
            }
            scoreboardPlayers.add(player);
            sort();
        }
    }

    /**
     * Remove.
     *
     * @param player the player
     */
    public void remove(ScoreboardPlayer player) {
        scoreboardPlayers.remove(player);
    }

    /**
     * Sort.
     *
     * @throws IOException the io exception
     */
    public void sort() throws IOException {
        for (int i = 0; i < scoreboardPlayers.size(); i++) {
            int pos = i;
            for (int j = i + 1; j < scoreboardPlayers.size(); j++) {
                if (scoreboardPlayers.get(j).getScore() <
                        scoreboardPlayers.get(pos).getScore()) {
                    pos = j;
                }
            }

            String tempName = scoreboardPlayers.get(pos).getName();
            int tempScore = scoreboardPlayers.get(pos).getScore();
            scoreboardPlayers.get(pos)
                    .setName(scoreboardPlayers.get(i).getName());
            scoreboardPlayers.get(pos)
                    .setScore(scoreboardPlayers.get(i).getScore());
            scoreboardPlayers.get(i).setName(tempName);
            scoreboardPlayers.get(i).setScore(tempScore);
        }
        Collections.reverse(scoreboardPlayers);
        writeToFile();
    }


    /**
     * @throws IOException
     */
    private void writeToFile() throws IOException {
        PrintWriter writer = new PrintWriter(directory, StandardCharsets.UTF_8);

        for (ScoreboardPlayer player : scoreboardPlayers) {
            writer.println(player.getName() + ":" + player.getScore());
        }
        writer.close();
    }

}