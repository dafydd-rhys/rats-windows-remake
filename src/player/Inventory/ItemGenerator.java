package player.Inventory;

import entity.Item;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import main.level.Level;

/**
 * PlayerItemGenerator
 * generator items for player
 * call startGiveItem() to start give player item
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class ItemGenerator {

    private final AnchorPane abilities;
    private int second = 0;
    private static Timer ticker;

    public ItemGenerator(Level level, Canvas canvas, GraphicsContext gc, AnchorPane abilities) {
        this.abilities = abilities;
        Inventory.clear();
        Inventory.load(level, canvas, gc);
        cancelTicker();

        ticker = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                second++;

                runPeriodic(level);
                //runRandom
            }
        };

        //run tick method every 500ms until stopped
        ticker.schedule(task, 0, 1000);
    }

    private void runPeriodic(Level level) {
        if (second % level.getTimeToGenerate().get(Item.TYPE.BOMB) == 0) {
            enableItem(Item.TYPE.BOMB);
        }
        if (second % level.getTimeToGenerate().get(Item.TYPE.GAS) == 0) {
            enableItem(Item.TYPE.GAS);
        }
        if (second % level.getTimeToGenerate().get(Item.TYPE.POISON) == 0) {
            enableItem(Item.TYPE.POISON);
        }
        if (second % level.getTimeToGenerate().get(Item.TYPE.STERILISATION) == 0) {
            enableItem(Item.TYPE.STERILISATION);
        }
        if (second % level.getTimeToGenerate().get(Item.TYPE.FEMALE_CHANGE) == 0) {
            enableItem(Item.TYPE.FEMALE_CHANGE);
        }
        if (second % level.getTimeToGenerate().get(Item.TYPE.MALE_CHANGE) == 0) {
            enableItem(Item.TYPE.MALE_CHANGE);
        }
        if (second % level.getTimeToGenerate().get(Item.TYPE.NO_ENTRY) == 0) {
            enableItem(Item.TYPE.NO_ENTRY);
        }
        if (second % level.getTimeToGenerate().get(Item.TYPE.DEATH_RAT) == 0) {
            enableItem(Item.TYPE.DEATH_RAT);
        }
    }

    private void enableItem(Item.TYPE type) {
        Inventory.enableItem(type, abilities);
    }

    public static void cancelTicker() {
        if (ticker != null) {
            ticker.cancel();
        }
    }

}
