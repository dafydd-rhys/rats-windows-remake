package scoreboard;

/**
 * ScoreboardPlayer.
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Bryan Kok
 */
public class ScoreboardPlayer {

    /**
     *
     */
    private String level;
    /**
     *
     */
    private int rank;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private int score;

    /**
     * Instantiates a new Scoreboard player.
     *
     * @param newLevel the level
     * @param newRank  the rank
     * @param newName  the name
     * @param newScore the score
     */
    public ScoreboardPlayer(final String newLevel, final int newRank, final String newName, final int newScore) {
        setLevel(newLevel);
        setRank(newRank);
        setName(newName);
        setScore(newScore);
    }

    /**
     * Sets level.
     *
     * @param newLevel the level
     */
    public void setLevel(final String newLevel) {
        this.level = newLevel;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets rank.
     *
     * @param newRank the rank
     */
    public void setRank(final int newRank) {
        this.rank = newRank;
    }

    /**
     * Gets rank.
     *
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets name.
     *
     * @param newName the name
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets score.
     *
     * @param newScore the score
     */
    public void setScore(final int newScore) {
        this.score = newScore;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

}

