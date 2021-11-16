package scoreboard;

/**
 * ScoreboardPlayer
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class ScoreboardPlayer {

    private String name;
    private int score;

    public ScoreboardPlayer(final String name, final int score) {
        this.name = name;
        this.score = score;
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

