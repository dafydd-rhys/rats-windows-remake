package controllers;

import com.jfoenix.controls.JFXButton;
import entity.Entity;
import entity.rats.Rat;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
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
    private final Image deathRatImage = new Image(dir + "death-rat.png");
    private final Image femaleChangeImage = new Image(dir + "female-change.png");
    private final Image maleChangeImage = new Image(dir + "male-change.png");
    private final Image gasGrenadeImage = new Image(dir + "gas-grenade.png");
    private final Image noEntryImage = new Image(dir + "no-entry-sign.png");
    private final Image poisonImage = new Image(dir + "poison.png");
    private final Image sterilisationImage = new Image(dir + "sterilisation.png");


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
    private final ImageView deathRat = new ImageView();
    private final ImageView femaleChange = new ImageView();
    private final ImageView maleChange = new ImageView();
    private final ImageView gasGrenade = new ImageView();
    private final ImageView noEntrySign = new ImageView();
    private final ImageView poison = new ImageView();
    private final ImageView sterilisation = new ImageView();

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
        items.add(deathRat);
        items.add(femaleChange);
        items.add(maleChange);
        items.add(gasGrenade);
        items.add(noEntrySign);
        items.add(poison);
        items.add(sterilisation);
        draggableImage(bomb, bombImage, "bomb", 0, 0);
        draggableImage(deathRat, deathRatImage, "deathRat", 1, 0);
        draggableImage(femaleChange, femaleChangeImage, "femaleSexChange", 2, 0);
        draggableImage(maleChange, maleChangeImage, "maleSexChange", 3, 0);
        draggableImage(gasGrenade, gasGrenadeImage, "gasGrenade", 0, 75);
        draggableImage(noEntrySign, noEntryImage, "noEntrySign", 1, 75);
        draggableImage(poison, poisonImage, "poison", 2, 75);
        draggableImage(sterilisation, sterilisationImage, "sterilisation", 3, 75);

        ticker.start();
        setImages();
        onActions();
    }

    private void draggableImage(ImageView item, Image image, String itemString, int yOffset, int xOffset) {
        item.setImage(image);
        item.setFitHeight(50);
        item.setFitWidth(50);
        AnchorPane.setTopAnchor(item, 75.0 * yOffset + 50);
        AnchorPane.setRightAnchor(item, 50.0 + xOffset);
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

        printEntities();

        canvas.setOnDragDropped(event -> dragAndDrop(event, canvas, thisImage[0].getImage()));

    }

    public void dragAndDrop(DragEvent event, Canvas canvas, Image image) {
        int x = ((int) event.getX() / 50);
        int y = ((int) event.getY() / 50);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, x * 50 + 12.5, y * 50 + 12.5);

        if (this.bombImage.equals(image)) {
            Inventory.createItem("bomb", x, y);
        } else if (this.deathRatImage.equals(image)) {
            Inventory.createItem("deathRat", x, y);
        } else if (this.femaleChangeImage.equals(image)) {
            Inventory.createItem("femaleSexChange", x, y);
        } else if (this.maleChangeImage.equals(image)) {
            Inventory.createItem("maleSexChange", x, y);
        } else if (this.gasGrenadeImage.equals(image)) {
            Inventory.createItem("gasGrenade", x, y);
        } else if (this.noEntryImage.equals(image)) {
            Inventory.createItem("noEntrySign", x, y);
        } else if (this.poisonImage.equals(image)) {
            Inventory.createItem("poison", x, y);
        } else if (this.sterilisationImage.equals(image)) {
            Inventory.createItem("sterilisation", x, y);
        }
    }

    private static void printEntities() {
        Tile[][] tiles = Level.getTiles();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                ArrayList<Entity> entities = tiles[y][x].getEntitiesOnTile();
                for (Entity entity : entities) {
                    System.out.println(entity.getEntityName() + " at " + x + ',' + y);
                }
            }
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

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                ArrayList<Entity> entities = tiles[y][x].getEntitiesOnTile();
                for (int i = 0; i < entities.size(); i++) {
                    if (entities.get(i).getEntityName().equals("Rat")) {
                        Rat rat = (Rat) entities.get(i);
                        if ((x + 1) < 20) {
                            tiles[y][x + 1].addEntityToTile(rat);
                            entities.remove(rat);
                        }
                    }
                }
            }
        }

        System.out.println("---------------------");
        printEntities();
        System.out.println("---------------------");
    }

}
