package player.Inventory;

import entity.Item;
import entity.weapon.Bomb;
import entity.weapon.DeathRat;
import entity.weapon.FemaleSexChange;
import entity.weapon.Gas;
import entity.weapon.MaleSexChange;
import entity.weapon.NoEntrySign;
import entity.weapon.Poison;
import entity.weapon.Sterilisation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javax.swing.*;
import main.level.Level;

/**
 * PlayerItemGenerator
 * generator items for player
 * call startGiveItem() to start give player item
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class ItemGenerator {

    private static Canvas canvas;
    private final GraphicsContext gc;
    private final AnchorPane abilities;

    private int second = 0;

    public ItemGenerator(Canvas canvas, GraphicsContext gc, AnchorPane abilities) {
        ItemGenerator.canvas = canvas;
        this.gc = gc;
        this.abilities = abilities;

        Inventory.getItems().clear();
        Timer timer = new Timer(1000, e -> {
            second += 1;

            if (second % Level.getTimeToGenerate().get(Item.TYPE.BOMB) == 0) {
                generateItem(new Bomb(), Inventory.getBombAmount());
            }
            if (second % Level.getTimeToGenerate().get(Item.TYPE.GAS) == 0) {
                generateItem(new Gas(), Inventory.getGasAmount());
            }
            if (second % Level.getTimeToGenerate().get(Item.TYPE.POISON) == 0) {
                generateItem(new Poison(), Inventory.getPoisonAmount());
            }
            if (second % Level.getTimeToGenerate().get(Item.TYPE.STERILISATION) == 0) {
                generateItem(new Sterilisation(), Inventory.getSterilisationAmount());
            }
            if (second % Level.getTimeToGenerate().get(Item.TYPE.FEMALE_CHANGE) == 0) {
                generateItem(new FemaleSexChange(), Inventory.getFemaleChangeAmount());
            }
            if (second % Level.getTimeToGenerate().get(Item.TYPE.MALE_CHANGE) == 0) {
                generateItem(new MaleSexChange(), Inventory.getMaleChangeAmount());
            }
            if (second % Level.getTimeToGenerate().get(Item.TYPE.NO_ENTRY) == 0) {
                generateItem(new NoEntrySign(), Inventory.getNoEntryAmount());
            }
            if (second % Level.getTimeToGenerate().get(Item.TYPE.DEATH_RAT) == 0) {
                generateItem(new DeathRat(), Inventory.getDeathRatAmount());
            }
        });
        timer.start();
    }

    private void generateItem(Item item, int amount) {
        if (!Inventory.maxAbilities()) {
            if (amount < getMaxAmount()) {
                Inventory.addItem(item);
                InventoryInteraction.draggableImage(canvas, gc, abilities, item, amount);
            }
        }
    }

    public int getMaxAmount() {
        return 4;
    }

    /*
    private void generateRandomItem() {
        int random = new Random().nextInt(8);
        boolean added = false;

        if (!Inventory.maxAbilities())
            while (!added) {
                if (random == 0) {
                    added = addItem(new Bomb(), Inventory.getBombAmount());
                } else if (random == 1) {
                    added = addItem(new Gas(), Inventory.getGasAmount());
                } else if (random == 2) {
                    added = addItem(new FemaleSexChange(), Inventory.getFemaleChangeAmount());
                } else if (random == 3) {
                    added = addItem(new MaleSexChange(), Inventory.getMaleChangeAmount());
                } else if (random == 4) {
                    added = addItem(new NoEntrySign(), Inventory.getNoEntryAmount());
                } else if (random == 5) {
                    added = addItem(new Poison(), Inventory.getPoisonAmount());
                } else if (random == 6) {
                    added = addItem(new Sterilisation(), Inventory.getSterilisationAmount());
                } else {
                    added = addItem(new DeathRat(), Inventory.getDeathRatAmount());
                }
                random = new Random().nextInt(8);
            }
    }

    public void startGiveItem() {
    generateRandomItem();
    }

    private boolean addItem(Item item, int amount) {
        if (amount < getMaxAmount()) {
            Inventory.addItem(item);
            InventoryInteraction.draggableImage(canvas, gc, abilities, item, amount);

            return true;
        }
        return false;
    }

    public int getWaitAmount() {
        return 3;
    }
     */

}
