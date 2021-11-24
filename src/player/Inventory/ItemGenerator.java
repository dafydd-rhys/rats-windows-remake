package player.Inventory;

/**
 * PlayerItemGenerator
 * generator items for player
 * call startGiveItem() to start give player item
 *
 * @author Chunyuan Zhang (2131205)
 */

public class ItemGenerator {

    private int maxItem = 4; //player most can have 4 items each
    //{bomb, deathRat, femaleChange, maleChange,gasGrenade, noEntrySign, poison, sterilisation}
    private int[] playerCurrentItem = {0, 0, 0, 0, 0, 0, 0, 0};
    private int[] timeOfGenerate = {3, 3, 3, 3, 3, 3, 3, 3};
    //time of generator each item (default is all 3 sec)
    //you can modify number in this array to control the time of item generator
    private boolean flag = true;//flag true-keep generator false - stop generator

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setPlayerCurrentItem(int i) {
        playerCurrentItem[i] += 1;
    }

    public int getPlayerCurrentItem(int i) {
        return playerCurrentItem[i];
    }

    public int getMaxItem() {
        return maxItem;
    }

    public void startGiveItem() {
        Threading t = new Threading();
        for (int i = 0; i < 8; i++) {
            Thread thread = new Thread(t);
            thread.start();
        }
    }
}

class Threading extends ItemGenerator implements Runnable {

    @Override
    public void run() {
        int i = Thread.currentThread().getName().toString().charAt(7);

        while (getFlag()) {
            try {
                Thread.sleep(1000 * i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (getPlayerCurrentItem(i) <= getMaxItem()) {
                setPlayerCurrentItem(i);
            }
        }
    }
}
