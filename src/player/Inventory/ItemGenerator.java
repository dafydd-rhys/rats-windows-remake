package player.Inventory;

import entity.Item;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import main.level.Level;
import player.Player;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * PlayerItemGenerator
 * generator items for player
 * call startGiveItem() to start give player item
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class ItemGenerator {

    private static Timer ticker;
    private final AnchorPane abilities;
    private int second = 0;

    /**
     * Instantiates a new Item generator.
     *
     * @param level     the level
     * @param canvas    the canvas
     * @param gc        the gc
     * @param abilities the abilities
     */
    public ItemGenerator(Level level, Canvas canvas, GraphicsContext gc,
                         AnchorPane abilities) {
        this.abilities = abilities;
        Inventory.clear();
        Inventory.load(level, canvas, gc);
        cancelTicker();

        ticker = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (Level.getPaused()) {
                    second++;

                    if (Player.getGeneration() ==
                            Player.ItemGeneration.RANDOM) {
                        if (second % 3 == 0) {
                            runRandom();
                        }
                    } else {
                        runPeriodic(level);
                    }
                }
            }
        };

        //run tick method every 500ms until stopped
        ticker.schedule(task, 0, 1000);
    }

    /**
     * Cancel ticker.
     */
    public static void cancelTicker() {
        if (ticker != null) {
            ticker.cancel();
        }
    }

    public static Timer getTicker() {
        return ticker;
    }

    /**
     *
     */
    private void runRandom() {
        int random = new Random().nextInt(8);

        if (random == 0) {
            enableItem(Item.TYPE.BOMB);
        } else if (random == 1) {
            enableItem(Item.TYPE.GAS);
        } else if (random == 2) {
            enableItem(Item.TYPE.POISON);
        } else if (random == 3) {
            enableItem(Item.TYPE.STERILISATION);
        } else if (random == 4) {
            enableItem(Item.TYPE.FEMALE_CHANGE);
        } else if (random == 5) {
            enableItem(Item.TYPE.MALE_CHANGE);
        } else if (random == 6) {
            enableItem(Item.TYPE.NO_ENTRY);
        } else {
            enableItem(Item.TYPE.DEATH_RAT);
        }
    }

    /**
     * @param level
     */
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
        if (second % level.getTimeToGenerate().get(Item.TYPE.STERILISATION) ==
                0) {
            enableItem(Item.TYPE.STERILISATION);
        }
        if (second % level.getTimeToGenerate().get(Item.TYPE.FEMALE_CHANGE) ==
                0) {
            enableItem(Item.TYPE.FEMALE_CHANGE);
        }
        if (second % level.getTimeToGenerate().get(Item.TYPE.MALE_CHANGE) ==
                0) {
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

    public int getSecond() {
        return second;
    }

    public AnchorPane getAbilities() {
        return abilities;
    }

}
