package scoreboard;

import java.io.IOException;

public class Score {

    private final int level;
    private final String name;
    private final int score;

    public Score(int level, String name, int score) {
        this.level = level;
        this.name = name;
        this.score = score;
    }

    public void addToScoreBoard() throws IOException {
        Scoreboard scoreboard = new Scoreboard(level);
        scoreboard.getScoreboard();
        scoreboard.add(new ScoreboardPlayer("Level: " + level, 0, name, score));
    }

}
