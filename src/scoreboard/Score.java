package scoreboard;

import java.io.IOException;

/**
 * The type Score.
 */
public class Score {

    /**
     *
     */
    private final int level;
    /**
     *
     */
    private final String name;
    /**
     *
     */
    private final int score;

    /**
     * Instantiates a new Score.
     *
     * @param level the level
     * @param name  the name
     * @param score the score
     */
    public Score(int level, String name, int score) {
        this.level = level;
        this.name = name;
        this.score = score;
    }

    /**
     * Add to score board.
     *
     * @throws IOException the io exception
     */
    public void addToScoreBoard() throws IOException {
        Scoreboard scoreboard = new Scoreboard(level);
        scoreboard.getScoreboard();
        scoreboard.add(new ScoreboardPlayer("Level: " + level, 0, name, score));
    }

}
