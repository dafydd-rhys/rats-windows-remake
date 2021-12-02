package scoreboard;

/**
 * ScoreboardPlayer
 *
 * @author Dafydd -Rhys Maund (2003900)
 */
public class ScoreboardPlayer {

    /** */
    private String level;
    /** */
    private int rank;
    /** */
    private String name;
    /** */
    private int score;

    /**
     * Instantiates a new Scoreboard player.
     *
     * @param level the level
     * @param rank  the rank
     * @param name  the name
     * @param score the score
     */
    public ScoreboardPlayer(final String level, final int rank, final String name, final int score) {
        this.level = level;
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(String level) {
        this.level = level;
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
     * @param rank the rank
     */
    public void setRank(int rank) {
        this.rank = rank;
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
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
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

