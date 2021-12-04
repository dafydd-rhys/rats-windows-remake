package scoreboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.Resources;

/**
 * Scoreboard.
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Bryan Kok
 */
public class Scoreboard {

    /**
     * Level of scoreboard.
     */
    private int boardLevel;
    /**
     * List of players in scoreboard.
     */
    private final List<ScoreboardPlayer> scoreboardPlayers = new ArrayList<>();
    /**
     * File directory.
     */
    private final File directory;

    /**
     * Instantiates a new Scoreboard.
     *
     * @param level the level
     * @throws IOException the io exception
     */
    public Scoreboard(final int level) throws IOException {
        setBoardLevel(level);
        this.directory = Resources.getScoreboard(level);
    }

    /**
     * Gets scoreboard.
     *
     * @throws IOException the io exception
     */
    public void getScoreboard() throws IOException {
        scoreboardPlayers.clear();
        BufferedReader reader = new BufferedReader(new FileReader(directory));

        int count = 1;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(":");
            scoreboardPlayers.add(new ScoreboardPlayer("Level " + boardLevel, count, split[0], Integer.parseInt(split[1])));
            count++;
        }
    }

    /**
     * Gets all.
     *
     * @return the all
     * @throws IOException the io exception
     */
    public ArrayList<ScoreboardPlayer> getAll() throws IOException {
        ArrayList<ScoreboardPlayer> allScoreboardPlayers = new ArrayList<>();
        final int limit = 7;
        for (int i = 1; i < limit; i++) {
            File file = Resources.getScoreboard(i);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            int count = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                allScoreboardPlayers.add(new ScoreboardPlayer("Level " + i, count, split[0], Integer.parseInt(split[1])));
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
    public void add(final ScoreboardPlayer player) throws IOException {
        final int limit = 9;
        if (player.getScore() > scoreboardPlayers.get(scoreboardPlayers.size() - 1).getScore()) {
            if (scoreboardPlayers.size() > limit) {
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
    public void remove(final ScoreboardPlayer player) {
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


    /**
     * Writes score in a file.
     *
     * @throws IOException
     */
    private void writeToFile() throws IOException {
        PrintWriter writer = new PrintWriter(directory, StandardCharsets.UTF_8);

        for (ScoreboardPlayer player : scoreboardPlayers) {
            writer.println(player.getName() + ":" + player.getScore());
        }
        writer.close();
    }

    /**
     * Sets scoreboard's level.
     *
     * @param level level of scoreboard.
     */
    public void setBoardLevel(final int level) {
        this.boardLevel = level;
    }

}
