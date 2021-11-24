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
import java.nio.file.attribute.PosixFileAttributes;
import java.util.Arrays;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.*;

/**
 * PlayerItemGenerator
 * generator items for player
 * call startGiveItem() to start give player item
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Chunyuan Zhang (2131205)
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
        generateItem();
        timer.start();
    }

    public void startGiveItem() {
        generateItem();
    }

    private void generateItem() {
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

    private boolean addItem(Item item, int amount) {
        if (amount < getMaxAmount()) {
            Inventory.addItem(item);
            InventoryInteraction.draggableImage(canvas, gc, abilities, item, amount);

            return true;
        }
        return false;
    }

    private final Timer timer = new Timer(1000, e -> {
        second += 1;

        if (second % getWaitAmount() == 0) {
            generateItem();
        }
    });

    public int getMaxAmount() {
        return 4;
    }

    public int getWaitAmount() {
        return 3;
    }

}
