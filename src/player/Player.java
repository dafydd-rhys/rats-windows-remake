package player;

/**
 * Player
 *
 * @author Chunyuan Zhang (2131205)
 * @author Gareth Wade (1901805)
 */

public class Player
{

    private String playerName;
    private String themeChoice;
    private int maxLevel;

    public Player(String playerName, String themeChoice) {
        this.playerName = playerName;
        this.themeChoice = themeChoice;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setThemeChoice(String themeChoice) {
        this.themeChoice = themeChoice;
    }

    public String getThemeChoice() {
        return themeChoice;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

}