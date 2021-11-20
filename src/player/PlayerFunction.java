
package player;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Player
 *
 * @author ChunyuanZhang (2131205)
 */

public class PlayerFunction {
    Player player = new Player();

    //addPlayerItems: add player item ones a time
    //Example call: addPlayerItems(1,1)
    public void addPlayerItems(int ItemId, int a) {
        if(player.getMaxNumberOfItems()[ItemId] > player.getPlayerItems()[ItemId])
        {//check is player have max item
            if(player.getPlayerItems().length == 0)
            {//if the array is empty, input the default value
                double[] tempList = {0,0,0,0,0,0,0,0};
                player.setPlayerItems(tempList);
            }
            else
            {//add item to playerItems.s
                double[] tempList = player.getPlayerItems();
                tempList[ItemId] = tempList[ItemId] + a;
                player.setPlayerItems(tempList);
            }
        }
    }

    //playerUseItems: player use item, let the count of item - 1
    public void playerUseItems(int ItemId)
    {
        if(player.getPlayerItems()[ItemId] != 0)
        {
            double[] tempList = player.getPlayerItems();
            tempList[ItemId] = tempList[ItemId] - 1;
            player.setPlayerItems(tempList);
        }
    }
}