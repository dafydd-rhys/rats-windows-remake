package scoreboard;

import java.io.IOException;

/**
 * Score.
 *
 * @author Bryan Kok
 * @author creator of scoreboard
 */

public class Score {

    /**
     * The score's level.
     */
    private int completedLevel;
    /**
     * The scorer's name.
     */
    private String newName;
    /**
     * The score number.
     */
    private int newScore;

    /**
     * Instantiates a new Score.
     *
     * @param level the level
     * @param name  the name
     * @param score the score
     */
    public Score(final int level, final String name, final int score) {
        setCompletedLevel(level);
        setNewName(name);
        setNewScore(score);
    }

    /**
     * Add to score board.
     *
     * @throws IOException the io exception
     */
    public void addToScoreBoard() throws IOException {
        Scoreboard scoreboard = new Scoreboard(completedLevel);
        scoreboard.getScoreboard();
        scoreboard.add(new ScoreboardPlayer("Level: " + completedLevel, 0, newName, newScore));
    }

    /**
     * Sets the level of the score.
     *
     * @param level the level of the score.
     */
    public void setCompletedLevel(final int level) {
        this.completedLevel = level;
    }

    /**
     * Sets the scorer's name.
     *
     * @param name  the new scorer's name.
     */
    public void setNewName(final String name) {
        this.newName = name;
    }

    /**
     * Sets the score number.
     *
     * @param score the new score number.
     * */
    public void setNewScore(final int score) {
        this.newScore = score;
    }
}
