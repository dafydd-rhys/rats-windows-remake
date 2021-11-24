package controllers;

import com.jfoenix.controls.JFXButton;
import entity.Rat;
import entity.Item;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import player.Inventory.ItemGenerator;
import tile.Movement;
import main.external.Audio;
import player.Inventory.Inventory;
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

    private static double currentTick;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Canvas canvas = new Canvas(1000, 1000);
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        game.setCenter(canvas);
        gameScroll.setPannable(true);
        new ItemGenerator(canvas, gc, abilities);

        onActions();

        try {
            LevelFileReader level = new LevelFileReader(1);
            new LevelFileGenerator(level.getTimeToGenerate(), gc, level.getLevel(),
                    level.getSpawns(), level.getExpectedTime(), level.getMaxRats());
        } catch (IOException e) {
            e.printStackTrace();
        }

        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));
        ticker.start();
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
        tick();
    });

    private static void tick() {
        ArrayList<Rat> rats = Level.getRats();

        if (currentTick % 2 == 0) {
            for (Rat rat : rats) {
                if (rat.isAdult()) {
                    move(rat);
                }
            }
        } else {
            for (Rat rat : rats) {
                if (!rat.isAdult()) {
                    move(rat);
                }
            }
        }


    }

    private static void draw() {
        Tile[][] tiles = Level.getTiles();
        ArrayList<Rat> rats = Level.getRats();
        ArrayList<Item> items = Level.getItems();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                Image image = tiles[y][x].getImage();
                gc.drawImage(image, x * 50, y * 50);
            }
        }

        for (Rat rat : rats) {
            if (Level.getTiles()[rat.getCurrentPosY()][rat.getCurrentPosX()].isCovering()){
                gc.drawImage(rat.getRotatedImage(), rat.getCurrentPosX() * 50, rat.getCurrentPosY() * 50);
            }
        }

        System.out.println(items.size());
        for (Item item : items) {
            gc.drawImage(item.getImage(), item.getCurrentPosX() * 50 + 10, item.getCurrentPosY() * 50 + 10);
        }
    }

}