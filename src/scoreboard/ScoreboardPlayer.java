package scoreboard;

/**
 * ScoreboardPlayer
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class ScoreboardPlayer {

    private String level;
    private int rank;
    private String name;
    private int score;

    public ScoreboardPlayer(final String level, final int rank, final String name, final int score) {
        this.level = level;
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}

