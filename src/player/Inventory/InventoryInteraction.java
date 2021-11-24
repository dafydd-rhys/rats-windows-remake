package player.Inventory;

import entity.Item;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import main.level.Level;

public class InventoryInteraction {

    private static final ImageView[] images = new ImageView[32];
    private static final Item[] items = new Item[32];
    private static int index = 0;

    public static void draggableImage(Canvas canvas, GraphicsContext gc, AnchorPane abilities, Item item, int amount) {
        ImageView imageView = new ImageView();
        imageView.setImage(item.getImage());
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        AnchorPane.setTopAnchor(imageView, item.getYOffset() * 40.0 + 10);
        AnchorPane.setLeftAnchor(imageView, amount * 35.0 + 5);

        Platform.runLater(() -> abilities.getChildren().add(imageView));
        images[index] = imageView;
        items[index] = item;
        index++;

        System.out.println("added " + item.getEntityName() + " to inventory");

        imageView.setOnDragDetected(event -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(item.getEntityName());
            db.setContent(content);
        });

        final ImageView[] storedImage = new ImageView[1];
        final Item[] storedItem = new Item[1];
        final Integer[] storedPosition = new Integer[1];
        canvas.setOnDragOver(event -> {
            for (int i = 0; i < images.length - 1; i++) {
                if (event.getGestureSource() == images[i]) {
                    storedImage[0] = images[i];
                    storedItem[0] = items[i];
                    storedPosition[0] = i;
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }
        });

        canvas.setOnDragDropped(event -> dragAndDrop(event, gc, storedItem[0], storedImage[0], storedPosition[0]));

    }

    private static void dragAndDrop(DragEvent event, GraphicsContext gc, Item item, ImageView image, int pos) {
        int x = ((int) event.getX() / 50);
        int y = ((int) event.getY() / 50);

        item.setCurrentPosX(x);
        item.setCurrentPosY(y);
        System.out.println("Dropped " + item.getEntityName() + " at " + x + ", " + y);

        Level.getItems().add(item);
        Level.getTiles()[y][x].addEntityToTile(item);
        Inventory.removeItem(item);

        index -= 1;
        System.arraycopy(items, pos + 1, items, pos, items.length - pos - 1);
        System.arraycopy(images, pos + 1, images, pos, items.length - pos - 1);
        gc.drawImage(image.getImage(), x * 50 + 10, y * 50 + 10);
    }

}
