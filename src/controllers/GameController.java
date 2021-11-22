package controllers;

import com.jfoenix.controls.JFXButton;
import entity.Entity;
import entity.rats.Rat;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*;
import main.level.Inventory;
import main.level.Level;
import main.stage.StageFunctions;
import main.level.LevelFileGenerator;
import main.level.LevelFileReader;
import tile.Tile;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */
public class GameController implements Initializable {

    private Canvas canvas;
    private final String dir = System.getProperty("user.dir") + "/src/resources/images/game/entities/";
    private final Image bombImage = new Image(dir + "bomb.png");
    private final Image maleRat = new Image(dir + "male-rat.png");

    @FXML
    private AnchorPane window;
    @FXML
    private AnchorPane bar;
    @FXML
    private AnchorPane abilities;
    @FXML
    private ScrollPane gameScroll;
    @FXML
    private BorderPane game;
    @FXML
    private JFXButton sfx;
    @FXML
    private JFXButton music;
    @FXML
    private JFXButton settings;
    @FXML
    private JFXButton minimize;
    @FXML
    private JFXButton maximise;
    @FXML
    private JFXButton exit;

    private final ArrayList<ImageView> items = new ArrayList<>();
    private final ImageView bomb = new ImageView();
    private final ImageView rat = new ImageView();

    private static double seconds;
    private static double currentTick;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvas = new Canvas(1000, 1000);
        game.setCenter(canvas);
        gameScroll.setPannable(true);

        try {
            LevelFileReader level = new LevelFileReader(1);
            new LevelFileGenerator(canvas, level.getLevel(), level.getSpawns());
        } catch (IOException e) {
            e.printStackTrace();
        }

        items.add(bomb);
        items.add(rat);
        draggableImage(bomb, bombImage, "bomb", 0);
        draggableImage(rat, maleRat, "deathRat", 1);

        Tile[][] tiles = Level.getTiles();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                ArrayList<Entity> entities = tiles[y][x].getEntitiesOnTile();
                for (Entity entity : entities) {
                    System.out.println(entity.getEntityName() + " at " + x + ',' + y);
                }
            }
        }

        ticker.start();
        setImages();
        onActions();
    }

    private void draggableImage(ImageView item, Image image, String itemString, int xOffset) {
        item.setImage(image);
        item.setFitHeight(50);
        item.setFitWidth(50);
        AnchorPane.setRightAnchor(item, 150.0);
        AnchorPane.setTopAnchor(item, 50.0 * xOffset);
        abilities.getChildren().add(item);

        item.setOnDragDetected(event -> {
            Dragboard db = item.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemString);
            db.setContent(content);
        });

        final ImageView[] thisImage = new ImageView[1];
        canvas.setOnDragOver(event -> {
            for (ImageView i : items) {
                if (event.getGestureSource() == i) {
                    thisImage[0] = i;
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }
        });

        canvas.setOnDragDropped(event -> dragAndDrop(event, canvas, thisImage[0].getImage()));

    }

    public void dragAndDrop(DragEvent event, Canvas canvas, Image image) {
        int x = ((int) event.getX() / 50);
        int y = ((int) event.getY() / 50);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, x * 50 + 12.5, y * 50 + 12.5);

        if (this.bombImage.equals(image)) {
            Inventory.createItem("bomb", x, y);
        } else if (this.maleRat.equals(image)) {
            Inventory.createItem("deathRat", x, y);
        }
    }

    private void setImages() {

    }

    private void onActions() {
        settings.setOnAction(e -> {
            try {
                StageFunctions.openSettings();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        music.setOnAction(e -> StageFunctions.muteMusic());
        sfx.setOnAction(e -> StageFunctions.muteEffects());
        minimize.setOnAction(e -> StageFunctions.minimize());
        maximise.setOnAction(e -> StageFunctions.maximise());
        exit.setOnAction(e -> {
            try {
                StageFunctions.exit();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void movement(final Rat rat) {
        /* AI rat movement
        if (direction == left) {
            move(-1);
        } else if (direction == right) {
            move(1);
        } else if (direction == up) {
            move(1);
        } else if (direction == down) {
            move(-1);
        }
        drawGame();
         */
    }

    private void move(final Rat rat, final int i) {
        /* check available movement
        if (rat.getCurrentPosX() + i != GrassTile) {
            rat.setCurrentPosX(rat.getCurrentPosX() + i);
        } else if (rat.getCurrentPosY() + i != GrassTile) {
            rat.setCurrentPosY(rat.getCurrentPosY() + i);
        } else if (rat.getCurrentPosX() - i != GrassTile) {
            rat.setCurrentPosX(rat.getCurrentPosX() + i);
        } else if (rat.getCurrentPosY() - i != GrassTile) {
            rat.setCurrentPosX(rat.getCurrentPosY() + i);
        }
         */
    }

    private static final Timer ticker = new Timer(500, e -> {
        //two ticks a second
        currentTick += 1;
        seconds = currentTick / 2;
        tick();
    });

    private static void tick() {
        Tile[][] tiles = Level.getTiles();
        System.out.println("tick-" + currentTick);
        System.out.println("seconds passed: " + seconds);
    }

}
