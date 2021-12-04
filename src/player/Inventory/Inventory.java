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
import java.io.IOException;
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
import javafx.scene.layout.AnchorPane;
import main.Resources;
import main.external.Audio;
import main.level.Level;
import tile.Tile;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Inventory.java, used to manage inventory, add, remove & interact.
 *
 * @author Dafydd -Rhys Maund (2003900)
 */
public class Inventory {

    /**
     * This arraylist contains all items, 8 stacks, 4 hash maps per stack containing each item and its image.
     */
    private static final ArrayList<Stack<HashMap<Item, ImageView>>> ITEMS = new ArrayList<>();
    /**
     * Array of all types of weapons.
     */
    private static final Item[] TYPES = {new Bomb(), new DeathRat(), new FemaleSexChange(), new MaleSexChange(),
            new Gas(), new NoEntrySign(), new Poison(), new Sterilisation()};
    /**
     * reference to current level.
     */
    private static Level level;
    /**
     *  reference to current canvas.
     */
    private static Canvas canvas;
    /**
     *  reference to current canvas drawer.
     */
    private static GraphicsContext gc;
    /**
     * Stored stack used to remove used items hash map.
     */
    private static Stack<HashMap<Item, ImageView>> storedStack;
    /**
     * Stored hash map used to reference current item and its image.
     */
    private static HashMap<Item, ImageView> storedMap;
    /**
     * Stored entry used to remove used items image and item instance.
     */
    private static Map.Entry<Item, ImageView> storedEntry;
    /**
     * Max amount of items.
     */
    private final static int MAX_AMOUNT = 4;
    /**
     * y offset of images.
     */
    private final static double Y_OFFSET = 40.0;
    /**
     * x offset of images.
     */
    private final static double X_OFFSET = 35.0;
    /**
     * addition onto y offset for spacing.
     */
    private final static double Y_OFFSET_ADD = 10.0;
    /**
     * addition onto x offset for spacing.
     */
    private final static double X_OFFSET_ADD = 5.0;
    /**
     * size of tile on board.
     */
    private final static int TILE_SIZE = 50;

    /**
     * This method simply creates the stack with all items.
     *
     * @param lvl  the level
     * @param paramCanvas the canvas
     * @param paramGC    the gc
     */
    public static void load(final Level lvl, final Canvas paramCanvas, final GraphicsContext paramGC) {
        Inventory.level = lvl;
        Inventory.canvas = paramCanvas;
        Inventory.gc = paramGC;

        for (Item item : TYPES) {
            Stack<HashMap<Item, ImageView>> stack = new Stack<>();

            //create for instances of item
            for (int j = 0; j < MAX_AMOUNT; j++) {
                HashMap<Item, ImageView> map = new HashMap<>();
                ImageView view = new ImageView(item.getImage());

                //disable image until active
                view.setDisable(true);
                map.put(item.createNewInstance(), view);
                stack.add(map);
            }
            ITEMS.add(stack);
        }
    }

    /**
     * Enables item, so it can be used.
     *
     * @param type      the type
     * @param abilities the abilities
     */
    public static void enableItem(final Item.TYPE type, final AnchorPane abilities) {
        //loops through until finds disabled item of same type
        for (Stack<HashMap<Item, ImageView>> stack : ITEMS) {
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
                        AnchorPane.setTopAnchor(image, item.getYOffset() * Y_OFFSET + Y_OFFSET_ADD);
                        AnchorPane.setLeftAnchor(image, i * X_OFFSET + X_OFFSET_ADD);

                        //prevent not being able to drag (image too thin - hard to grab)
                        if (type == Item.TYPE.MALE_CHANGE) {
                            image.setImage(Resources.getEntityImage("male-change-show"));
                        } else if (type == Item.TYPE.FEMALE_CHANGE) {
                            image.setImage(Resources.getEntityImage("female-change-show"));
                        }

                        //adds item to inventory and draws it
                        Platform.runLater(() -> abilities.getChildren().add(image));
                        //listens for drag and drop
                        listener(item, image, abilities);
                    }
                }
                //break loop if found
                if (found) {
                    return;
                }
            }
        }
    }

    /**
     * Listens for each item in inventory.
     *
     * @param item item
     * @param image items image
     * @param abilities ability pane
     */
    private static void listener(final Item item, final ImageView image, final AnchorPane abilities) {
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
            for (Stack<HashMap<Item, ImageView>> stack : ITEMS) {
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
        canvas.setOnDragDropped(event -> {
            try {
                dragAndDrop(event, gc, abilities);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * Drag and drops item onto canvas.
     *
     * @param event drag event
     * @param gcx canvas drawer
     * @param abilities inventory pane
     * @throws UnsupportedAudioFileException incorrect audio extension
     * @throws LineUnavailableException LineUnavailableException
     * @throws IOException can't find file
     */
    private static void dragAndDrop(final DragEvent event, final GraphicsContext gcx, final AnchorPane abilities)
            throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int x = ((int) event.getX() / TILE_SIZE);
        int y = ((int) event.getY() / TILE_SIZE);

        Item item = storedEntry.getKey();
        ImageView image = storedEntry.getValue();

        //if the tile attempting to drag on is walkable (not themed tile)
        Tile tile = level.getTiles()[y][x];
        if (tile.isWalkable() && tile.isCovering()) {
            //removes item from inventory and draws in-game
            level.placeItem(item, tile);

            Audio.clickEffect();
            if (tile.isCovering()) {
                gcx.drawImage(image.getImage(), x * TILE_SIZE + Y_OFFSET_ADD, y * TILE_SIZE + Y_OFFSET_ADD);
            }
            image.setOpacity(0);
            image.setDisable(true);

            //creates new map for new item of same type
            HashMap<Item, ImageView> newMap = new HashMap<>();
            Item newItem = item.createNewInstance();
            ImageView oi = new ImageView(newItem.getImage());
            oi.setDisable(true);
            newMap.put(newItem, oi);

            //removed used and adds new
            storedStack.remove(storedMap);
            storedStack.add(newMap);

            //reshuffle so not used items are on left
            reshuffle(abilities);
        }
    }

    /**
     * reshuffles inventory.
     *
     * @param abilities inventory pane
     */
    private static void reshuffle(final AnchorPane abilities) {
        //clear visual inventory
        abilities.getChildren().clear();

        for (Stack<HashMap<Item, ImageView>> stack : ITEMS) {

            //count used to identify its XOffset in pane
            int count = 0;
            for (HashMap<Item, ImageView> map : stack) {
                for (Map.Entry<Item, ImageView> entry : map.entrySet()) {
                    Item item = entry.getKey();
                    ImageView image = entry.getValue();

                    //enables next disabled image
                    if (!image.isDisable()) {
                        AnchorPane.setTopAnchor(image, item.getYOffset() * Y_OFFSET + Y_OFFSET_ADD);
                        AnchorPane.setLeftAnchor(image, count * X_OFFSET + X_OFFSET_ADD);

                        //redraws sorted inventory
                        Platform.runLater(() -> abilities.getChildren().add(image));
                        listener(item, image, abilities);
                        count++;
                    }
                }
            }
        }
    }

    /**
     * Retrieves items in inventory.
     *
     * @return items
     */
    public static ArrayList<Stack<HashMap<Item, ImageView>>> getItems() {
        return ITEMS;
    }

    /**
     * Clear inventory and stored items.
     */
    public static void clear() {
        Inventory.storedStack = null;
        Inventory.storedMap = null;
        Inventory.storedEntry = null;
        ITEMS.clear();
    }

}
