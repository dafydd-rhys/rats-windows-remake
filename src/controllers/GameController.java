package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import entity.rat.Rat;
import entity.Item;
import entity.weapon.DeathRat;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import player.Inventory.Inventory;
import player.Inventory.ItemGenerator;
import player.Player;
import scoreboard.Score;
import tile.Movement;
import main.external.Audio;
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
 * @author Harry Boyce (2011556)
 * @author Maurice Petersen (2013396)
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
    @FXML
    private JFXTextArea lblLevel;
    @FXML
    private JFXTextArea lblTime;
    @FXML
    private JFXTextArea lblScore;
    @FXML
    private JFXTextArea lblExpected;
    @FXML
    private JFXButton restartBtn;
    @FXML
    private JFXButton mainMenu;

    private static Level level;
    private static GraphicsContext gc;
    private static double currentTick;
    private Timer ticker;
    private boolean gameOver = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LevelFileReader levelReader = null;
        try {
            levelReader = new LevelFileReader(Level.currentLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert levelReader != null;

        Canvas canvas = new Canvas(levelReader.getSizeX() * 50, levelReader.getSizeY() * 50);
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        game.setCenter(canvas);
        gameScroll.setPannable(true);

        //generates map and starts items generating
        LevelFileGenerator generator = new LevelFileGenerator(levelReader.getTimeToGenerate(), gc, levelReader.getSizeX(),
                levelReader.getSizeY(), levelReader.getLevel(), levelReader.getSpawns(),
                levelReader.getExpectedTime(), levelReader.getMaxRats());
        level = generator.getLevel();

        lblLevel.setText("Level: " + level.getCurrentLevel());
        lblExpected.setText("Expected: " + level.getExpectedTime() + " seconds");

        Inventory.clear();
        new ItemGenerator(level, canvas, gc, abilities);

        onActions();

        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));

        ticker = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                currentTick += 1;

                if (currentTick % 2 == 0) {
                    lblTime.setText("Current: " + (int) currentTick / 2  + " seconds");
                }
                lblScore.setText("Score: " + level.getScore());
                tick();

                if (level.getRats().size() > level.getMaxRats()) {
                    gameOver = true;
                }

                if (gameOver) {
                    if ((int) currentTick / 2 < level.getExpectedTime()) {
                        try {
                            Score score = new Score(level.getCurrentLevel(), Player.getPlayerName(), level.getScore());
                            score.addToScoreBoard();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        level.setScore(0);
                        lblScore.setText("Score: " + level.getScore());
                    }
                }
            }
        };
        //run tick method every 500ms until stopped
        ticker.schedule(task, 500, 500);
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

        restartBtn.setOnAction(e -> {
            try {
                ticker.cancel();
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level " + level.getCurrentLevel());
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });
    }

    private static void move(final Rat rat) {
        Movement.tiles = level.getTiles();
        Movement.rat = rat;
        Movement.current = level.getTiles()[rat.getCurrentPosX()][rat.getCurrentPosY()];
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
    }

    private static void tick() {
        ArrayList<Rat> rats = level.getRats();
        ArrayList<Item> items = level.getItems();

        //adult rats - don't change for-loop to enhanced-for-loop (ConcurrentModificationException)
        if (currentTick % 2 == 0) {
            for (int i = 0; i < rats.size(); i++) {
                if (rats.get(i).isAdult()) {
                    Rat rat = rats.get(i);
                    rat.setLevel(level);

                    move(rat);
                    rat.findPartner(level.getTiles()[rat.getCurrentPosY()][rat.getCurrentPosX()]);
                    rat.giveBirth();
                }
            }
        }

        //baby rats - don't change for-loop to enhanced-for-loop (ConcurrentModificationException)
        if (currentTick % 1 == 0) {
            for (int i = 0; i < rats.size(); i++) {
                if (!rats.get(i).isAdult()) {
                    Rat rat = rats.get(i);
                    rat.setLevel(level);

                    move(rat);
                    rat.growUp();
                }
            }
        }
        for (int i = 0; i < items.size(); i++) {
            items.get(i).activate(level);
        }
        draw();
    }

    private static void draw() {
        Tile[][] tiles = level.getTiles();
        ArrayList<Rat> rats = level.getRats();
        ArrayList<Item> items = level.getItems();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                Image image = tiles[y][x].getImage();
                gc.drawImage(image, x * 50, y * 50);
            }
        }

        for (Rat rat : rats) {
            if (level.getTiles()[rat.getCurrentPosY()][rat.getCurrentPosX()].isCovering()) {
                gc.drawImage(rat.getRotatedImage(), rat.getCurrentPosX() * 50, rat.getCurrentPosY() * 50);
            }
        }

        for (Item item : items) {
            if (level.getTiles()[item.getCurrentPosY()][item.getCurrentPosX()].isCovering()) {
                if (item.getType() == Item.TYPE.DEATH_RAT) {
                    DeathRat deathRat = (DeathRat) item;
                    gc.drawImage(deathRat.getRotatedImage(), item.getCurrentPosX() * 50 + 10, item.getCurrentPosY() * 50 + 10);
                } else {
                    gc.drawImage(item.getImage(), item.getCurrentPosX() * 50 + 10, item.getCurrentPosY() * 50 + 10);
                }
            }
        }
    }

}