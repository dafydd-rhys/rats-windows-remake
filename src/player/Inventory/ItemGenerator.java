package player.Inventory;

import entity.Item;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import main.level.Level;
import player.Player;

/**
 * PlayerItemGenerator, give items to player.
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class ItemGenerator {

    /**
     * inventory pane.
     */
    private final AnchorPane abilities;
    /**
     * current second.
     */
    private int second = 0;
    /**
     * ticker.
     */
    private static Timer ticker;
    /**
     * amount of item types.
     */
    private static final int AMOUNT_OF_ITEMS = 8;
    /**
     * how long to generate in random generation.
     */
    private static final int GENERATE_EVERY = 3;
    /**
     * delay until start of ticker.
     */
    private static final int DELAY = 0;
    /**
     * run ticker every second.
     */
    private static final int RUN_EVERY = 1000;

    /**
     * Instantiates a new Item generator.
     *
     * @param level     the level
     * @param canvas    the canvas
     * @param gc        the gc
     * @param pane the abilities
     */
    public ItemGenerator(final Level level, final Canvas canvas,
                         final GraphicsContext gc, final AnchorPane pane) {
        this.abilities = pane;
        Inventory.clear();
        Inventory.load(level, canvas, gc);
        cancelTicker();

        ticker = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (Level.getPaused()) {
                    second++;

                    if (Player.getGeneration() == Player.ItemGeneration.RANDOM) {
                        if (second % GENERATE_EVERY == 0) {
                            runRandom();
                        }
                    } else {
                        runPeriodic(level);
                    }
                }
            }
        };

        //run tick method every 500ms until stopped
        ticker.schedule(task, DELAY, RUN_EVERY);
    }

    /**
     * runs random item generation.
     */
    private void runRandom() {
        //creates random number
        int random = new Random().nextInt(AMOUNT_OF_ITEMS);

        //what ever generated add item
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
    private void runPeriodic(final Level level) {
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

    private void enableItem(final Item.TYPE type) {
        Inventory.enableItem(type, abilities);
    }

    /**
     * Cancel ticker.
     */
    public static void cancelTicker() {
        if (ticker != null) {
            ticker.cancel();
        }
    }

    /**
     * @return current second.
     */
    public int getSecond() {
        return second;
    }

    /**
     * @return ticker.
     */
    public static Timer getTicker() {
        return ticker;
    }

    /**
     * @return ability pane.
     */
    public AnchorPane getAbilities() {
        return abilities;
    }

}
