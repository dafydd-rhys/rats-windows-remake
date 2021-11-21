package player;

/**
 * Player
 *
 * @author Chunyuan Zhang (2131205)
 */

public class Player
{
    private String PlayerName;
    private double[] PlayerItems = {0,0,0,0,0,0,0,0};
    private double[] MaxNumberOfItems = {4,4,4,4,4,4,4,4};
    private boolean PlayerScore;
    /* PlayerItems:
    index of array is the number of the items
    0 - Boom
    1 -
    2 -
    3 -
    4 -
    5 -
    6 -
    7 -

    MaxNumberOfItems:
    index of the largest number of item player can have.
     */

    public void Player() {
    }

    public String getPlayerName() {
        return PlayerName;
    }
    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    public double[] getPlayerItems() {
        return PlayerItems;
    }
    public void setPlayerItems(double[] PlayerItems) {
        this.PlayerItems = PlayerItems;
    }

    public boolean getPlayerScore() {
        return PlayerScore;
    }
    public void setPlayerScore(boolean PlayerScore) {
        this.PlayerScore = PlayerScore;
    }

    public double[] getMaxNumberOfItems() {
        return MaxNumberOfItems;
    }
    public void setMaxNumberOfItems(double[] MaxNumberOfItems) {
        this.MaxNumberOfItems = MaxNumberOfItems;
    }
}

