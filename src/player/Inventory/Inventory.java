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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import main.level.Level;
import tile.Tile;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class Inventory {

    private static final ArrayList<Stack<HashMap<Item, ImageView>>> items = new ArrayList<>();
    private static final Item[] types = {new Bomb(), new DeathRat(), new FemaleSexChange(), new MaleSexChange(),
            new Gas(), new NoEntrySign(), new Poison(), new Sterilisation()};

    private static Level level;
    private static Canvas canvas;
    private static GraphicsContext gc;

    private static Stack<HashMap<Item, ImageView>> storedStack;
    private static HashMap<Item, ImageView> storedMap;
    private static Map.Entry<Item, ImageView> storedEntry;

    public static void load(Level level, Canvas canvas, GraphicsContext gc) {
        Inventory.level = level;
        Inventory.canvas = canvas;
        Inventory.gc = gc;

        for (Item item : types) {
            Stack<HashMap<Item, ImageView>> stack = new Stack<>();

            for (int j = 0; j < 4; j++) {
                HashMap<Item, ImageView> map = new HashMap<>();
                ImageView view = new ImageView(item.getImage());

                view.setDisable(true);
                map.put(item.createNewInstance(), view);
                stack.add(map);
            }
            items.add(stack);
        }
    }

    public static void enableItem(Item.TYPE type, AnchorPane abilities) {
        for (Stack<HashMap<Item, ImageView>> stack : items) {
            for (int i = 0; i < stack.size(); i++) {
                HashMap<Item, ImageView> map = stack.get(i);

                boolean found = false;
                for (Map.Entry<Item, ImageView> entry : map.entrySet()) {
                    Item item = entry.getKey();
                    ImageView image = entry.getValue();

                    //enables next disabled image
                    if (item.getType() == type && image.isDisable()) {
                        found = true;
                        image.setDisable(false);
                        AnchorPane.setTopAnchor(image, item.getYOffset() * 40.0 + 10);
                        AnchorPane.setLeftAnchor(image, i * 35.0 + 5);
                        Platform.runLater(() -> abilities.getChildren().add(image));
                        listener(item, image, abilities);
                    }
                }
                if (found) {
                    break;
                }
            }
        }
    }

    private static void listener(Item item, ImageView image, AnchorPane abilities) {
        //drags image onto board
        image.setOnDragDetected(event -> {
            Dragboard db = image.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(item.getEntityName());
            db.setContent(content);
            event.consume();
        });

        //checks if source is a draggable object
        canvas.setOnDragOver(event -> {
            for (Stack<HashMap<Item, ImageView>> stack : items) {
                for (HashMap<Item, ImageView> map : stack) {
                    for (Map.Entry<Item, ImageView> entry : map.entrySet()) {
                        if (event.getGestureSource() == entry.getValue()) {
                            Inventory.storedStack = stack;
                            Inventory.storedMap = map;
                            Inventory.storedEntry = entry;
                            event.acceptTransferModes(TransferMode.ANY);
                        }
                    }
                }
            }
        });

        //releases object
        canvas.setOnDragDropped(event -> dragAndDrop(event, gc, abilities));

    }

    private static void dragAndDrop(DragEvent event, GraphicsContext gc, AnchorPane abilities) {
        int x = ((int) event.getX() / 50);
        int y = ((int) event.getY() / 50);

        Item item = storedEntry.getKey();
        ImageView image = storedEntry.getValue();

        //if the tile attempting to drag on is walkable (not themed tile)
        Tile tile = level.getTiles()[y][x];
        if (tile.isWalkable()) {
            //removes item from inventory and draws in-game
            level.placeItem(item, tile);

            if (tile.isCovering()) {
                gc.drawImage(image.getImage(), x * 50 + 10, y * 50 + 10);
            }
            image.setOpacity(0);
            image.setDisable(true);

            HashMap<Item, ImageView> newMap = new HashMap<>();
            Item newItem = item.createNewInstance();
            ImageView oi = new ImageView(newItem.getImage());
            oi.setDisable(true);
            newMap.put(newItem, oi);

            storedStack.remove(storedMap);
            storedStack.add(newMap);

            reshuffle(abilities);
        }
    }

    private static void reshuffle(AnchorPane abilities) {
        abilities.getChildren().clear();

        for (Stack<HashMap<Item, ImageView>> stack : items) {

            int count = 0;
            for (HashMap<Item, ImageView> map : stack) {
                for (Map.Entry<Item, ImageView> entry : map.entrySet()) {
                    Item item = entry.getKey();
                    ImageView image = entry.getValue();

                    //enables next disabled image
                    if (!image.isDisable()) {
                        AnchorPane.setTopAnchor(image, item.getYOffset() * 40.0 + 10);
                        AnchorPane.setLeftAnchor(image, count * 35.0 + 5);
                        Platform.runLater(() -> abilities.getChildren().add(image));
                        listener(item, image, abilities);
                        count++;
                    }
                }
            }
        }
    }

    public static void clear() {
        Inventory.storedStack = null;
        Inventory.storedMap = null;
        Inventory.storedEntry = null;
        items.clear();
    }

}
