package controllers;

import com.jfoenix.controls.JFXButton;
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
import main.Movement;
import main.external.Audio;
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
    @FXML
    private ImageView musicImage;
    @FXML
    private ImageView effectsImage;

    private static Canvas canvas;
    private static GraphicsContext gc;

    private final String dir = System.getProperty("user.dir") + "/src/resources/images/game/entities/";
    private final Image bombImage = new Image(dir + "bomb.png");
    private final Image deathRatImage = new Image(dir + "death-rat.png");
    private final Image femaleChangeImage = new Image(dir + "female-change.png");
    private final Image maleChangeImage = new Image(dir + "male-change.png");
    private final Image gasGrenadeImage = new Image(dir + "gas-grenade.png");
    private final Image noEntryImage = new Image(dir + "no-entry-sign.png");
    private final Image poisonImage = new Image(dir + "poison.png");
    private final Image sterilisationImage = new Image(dir + "sterilisation.png");

    private final ImageView bomb = new ImageView();
    private final ImageView deathRat = new ImageView();
    private final ImageView femaleChange = new ImageView();
    private final ImageView maleChange = new ImageView();
    private final ImageView gasGrenade = new ImageView();
    private final ImageView noEntrySign = new ImageView();
    private final ImageView poison = new ImageView();
    private final ImageView sterilisation = new ImageView();
    private final ImageView[] items = {bomb, deathRat, femaleChange, maleChange,
            gasGrenade, noEntrySign, poison, sterilisation};

    private static double seconds;
    private static double currentTick;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvas = new Canvas(1000, 1000);
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        game.setCenter(canvas);
        gameScroll.setPannable(true);
        onActions();

        try {
            LevelFileReader level = new LevelFileReader(1);
            new LevelFileGenerator(gc, level.getLevel(), level.getSpawns());
        } catch (IOException e) {
            e.printStackTrace();
        }

        draggableImage(items[0], bombImage, "bomb", 0, 0);
        draggableImage(items[1], deathRatImage, "deathRat", 1, 0);
        draggableImage(items[2], femaleChangeImage, "femaleSexChange", 2, 0);
        draggableImage(items[3], maleChangeImage, "maleSexChange", 3, 0);
        draggableImage(items[4], gasGrenadeImage, "gasGrenade", 0, 75);
        draggableImage(items[5], noEntryImage, "noEntrySign", 1, 75);
        draggableImage(items[6], poisonImage, "poison", 2, 75);
        draggableImage(items[7], sterilisationImage, "sterilisation", 3, 75);

        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));
        ticker.start();
    }

    private void draggableImage(ImageView item, Image image, String itemString, int yOffset, int xOffset) {
        item.setImage(image);
        item.setFitHeight(30);
        item.setFitWidth(30);

        AnchorPane.setTopAnchor(item, 75.0 * yOffset + 50);
        AnchorPane.setRightAnchor(item, 50.0 + xOffset);
        abilities.getChildren().add(item);

        item.setOnDragDetected(event -> {
            Dragboard db = item.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemString);
            db.setContent(content);
        });

        final ImageView[] storedImage = new ImageView[1];
        canvas.setOnDragOver(event -> {
            for (ImageView thisImage : items) {
                if (event.getGestureSource() == thisImage) {
                    storedImage[0] = thisImage;
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }
        });

        canvas.setOnDragDropped(event -> dragAndDrop(event, storedImage[0].getImage()));
    }

    public void dragAndDrop(DragEvent event, Image image) {
        int x = ((int) event.getX() / 50);
        int y = ((int) event.getY() / 50);

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

    private void onActions() {
        settings.setOnAction(e -> {
            try {
                StageFunctions.openSettings();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        music.setOnAction(e -> {
            StageFunctions.muteMusic();
            StageFunctions.toggleOpacity(musicImage);
        });

        sfx.setOnAction(e -> {
            StageFunctions.muteEffects();
            StageFunctions.toggleOpacity(effectsImage);
        });

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

    private static void move(final Rat rat) {
        Movement.tiles = Level.getTiles();
        Movement.rat = rat;
        Movement.current = Level.getTiles()[rat.getCurrentPosX()][rat.getCurrentPosY()];
        Movement.curX = rat.getCurrentPosX();
        Movement.curY = rat.getCurrentPosY();

        if (rat.getDirection() == Rat.Direction.LEFT) {
            Movement.tryHorizontal(-1, 1);
        } else if (rat.getDirection() == Rat.Direction.RIGHT) {
            Movement.tryHorizontal(1, -1);
        } else if (rat.getDirection() == Rat.Direction.UP) {
            Movement.tryVertical(-1, 1);
        } else if (rat.getDirection() == Rat.Direction.DOWN) {
            Movement.tryVertical(1, -1);
        }
        draw();
    }

    private static final Timer ticker = new Timer(500, e -> {
        currentTick += 1;
        seconds = currentTick / 2;
        tick();
    });

    private static void tick() {
        ArrayList<Rat> rats = Level.getRats();

        if (seconds % 1 == 0) {
            for (Rat rat : rats) {
                move(rat);
            }
        }
    }

    private static void draw() {
        Tile[][] tiles = Level.getTiles();
        ArrayList<Rat> rats = Level.getRats();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                Image image = tiles[y][x].getImage();
                gc.drawImage(image, x * 50, y * 50);
            }
        }

        for (Rat rat : rats) {
            gc.drawImage(rat.getRotatedImage(), rat.getCurrentPosX() * 50, rat.getCurrentPosY() * 50);
        }
    }

}