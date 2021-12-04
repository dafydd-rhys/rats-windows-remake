package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import entity.rat.Rat;
import entity.Item;
import entity.weapon.DeathRat;
import entity.weapon.Gas;
import entity.weapon.Sterilisation;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;
import main.ConfirmDialog;
import main.Resources;
import main.level.Level;
import main.level.LevelFileGenerator;
import main.level.LevelFileReader;
import main.level.LevelLoader;
import main.level.LevelSave;
import main.level.LevelLoadGenerator;
import player.Inventory.Inventory;
import player.Inventory.ItemGenerator;
import player.Inventory.ItemLoader;
import player.Player;
import scoreboard.Score;
import tile.Movement;
import main.external.Audio;
import main.stage.StageFunctions;
import tile.Tile;

/**
 * This is GameController.java, this is where all in-game procedures take place e.g.
 * drawing game, updating tick and overall where the game is played and displayed to the player.
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 * @author Harry Boyce (2011556)
 * @author Maurice Petersen (2013396)
 */
public class GameController implements Initializable {

    /**
     * Abilities pane, where all items are drawn when given to the player.
     */
    @FXML
    private AnchorPane abilities;
    /**
     * The scroll pane where the game is hosted.
     */
    @FXML
    private ScrollPane gameScroll;
    /**
     * The pane which the game is held in and can be interacted with.
     */
    @FXML
    private BorderPane game;
    /**
     * Sound-FX button, mutes/un-mutes on click.
     */
    @FXML
    private JFXButton sfx;
    /**
     * Music button, mutes/un-mutes on click.
     */
    @FXML
    private JFXButton music;
    /**
     * Settings button, loads settings menu on click.
     */
    @FXML
    private JFXButton settings;
    /**
     * Minimize button, minimizes window on click.
     */
    @FXML
    private JFXButton minimize;
    /**
     * Maximise button, maximises window on click.
     */
    @FXML
    private JFXButton maximise;
    /**
     * Exit button, exits program on click.
     */
    @FXML
    private JFXButton exit;
    /**
     * image used to represent music button.
     */
    @FXML
    private ImageView musicImage;
    /**
     * image used to represent effects button.
     */
    @FXML
    private ImageView effectsImage;
    /**
     * label representing current level.
     */
    @FXML
    private JFXTextArea lblLevel;
    /**
     * label representing current time.
     */
    @FXML
    private JFXTextArea lblTime;
    /**
     * label representing current score.
     */
    @FXML
    private JFXTextArea lblScore;
    /**
     * label representing expected time.
     */
    @FXML
    private JFXTextArea lblExpected;
    /**
     * restart button, restarts level on click.
     */
    @FXML
    private JFXButton restartBtn;
    /**
     * main menu button, goes back to main menu on click.
     */
    @FXML
    private JFXButton mainMenu;
    /**
     * progress bar, shows how close the player to losing.
     */
    @FXML
    private ProgressBar pr;
    /**
     * percentage of the bar currently occupied.
     */
    @FXML
    private Rectangle percentage;

    /**
     * The size of a tile on the board.
     */
    private final static int TILE_SIZE = 50;
    /**
     * Offset of images on the tile.
     */
    private final static int OFFSET = 10;
    /**
     * Time at which item images begin to fade relative to health (gas cloud).
     */
    private final static int START_FADE = 3;
    /**
     * Delay in which each tick is ran.
     */
    private final static int DELAY = 500;
    /**
     * Value at which the progress bar is full.
     */
    private final static int COMPLETE_BAR = 100;
    /**
     * The current level instance.
     */
    private static Level level;
    /**
     * value at which the progress bar increases per rat.
     */
    private static double progressIncrease;
    /**
     * The graphics context used to draw to the canvas.
     */
    private static GraphicsContext gc;
    /**
     * Represents current tick in the program.
     */
    private static double currentTick;
    /**
     * The ticker which runs every 500ms.
     */
    private Timer ticker;

    /**
     * This runs as soon as the scene is loaded, it simply sets all
     * placeholder values and properties to the fxml controls and also
     * loads the current level into a canvas and starts ticker.
     *
     * @param url            url of resources.
     * @param resourceBundle bundle of resources.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        Level.setGameOver(false);
        Level.setGameWon(false);
        lblTime.setEditable(false);
        lblExpected.setEditable(false);

        //loads current level.
        LevelFileReader levelReader = null;
        LevelLoader levelLoader = null;
        try {
            levelReader = new LevelFileReader(Level.currentLevel, Level.isSave());
            if (Level.isSave()) {
                levelLoader = new LevelLoader();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert levelReader != null;

        Canvas canvas = new Canvas(levelReader.getSizeX() * TILE_SIZE, levelReader.getSizeY() * TILE_SIZE);
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //makes game scrollable by mouse and centers level.
        game.setCenter(canvas);
        gameScroll.setPannable(true);

        //checks if where loading new level or previously played level.
        if (!Level.isSave()) {
            //generates map and starts items generating.
            LevelFileGenerator generator = new LevelFileGenerator(levelReader.getTimeToGenerate(), gc,
                    levelReader.getSizeX(), levelReader.getSizeY(), levelReader.getLevel(),
                    levelReader.getExpectedTime(), levelReader.getMaxRats());
            level = generator.getLevel();
            level.setScore(0);
        } else {
            assert levelLoader != null;
            LevelLoadGenerator loadGenerator = new LevelLoadGenerator(levelReader.getTimeToGenerate(), gc,
                    levelReader.getSizeX(), levelReader.getSizeY(), levelReader.getLevel(), levelLoader.getRatSpawns(),
                    levelLoader.getItemSpawns(), levelReader.getExpectedTime(), levelReader.getMaxRats());
            level = loadGenerator.getLevel();
            currentTick = levelLoader.getCurrentTick();
            level.setScore(levelLoader.getScore());
        }

        //initializes progress bar and sets default level info properties.
        pr.setProgress(0);
        progressIncrease = (double) (COMPLETE_BAR / level.getMaxRats()) / COMPLETE_BAR;
        lblLevel.setText("Level: " + Level.getCurrentLevel());
        lblExpected.setText("Expected: " + level.getExpectedTime() + " seconds");

        //clears and begins item generation.
        Inventory.clear();
        new ItemGenerator(level, canvas, gc, abilities);
        if (Level.isSave()) {
            assert levelLoader != null;
            new ItemLoader(levelLoader.getInventory(), abilities);
        }

        //adds listeners to all fxml controls.
        onActions();
        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));

        //creates ticker and tasks to be completed.
        ticker = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //don't run if level is paused.
                if (Level.getPaused()) {
                    currentTick++;
                    if (currentTick % 2 == 0) {
                        lblTime.setText("Current: " + (int) currentTick / 2 + " seconds");
                    }

                    //runs tick (rats and items run for tick).
                    tick();

                    //updates all values related to new game state.
                    lblScore.setText("Score: " + Level.getScore());
                    pr.setProgress(level.getRats().size() * progressIncrease);

                    double maleRatCount = 0;
                    for (int i = 0; i < level.getRats().size(); i++) {
                        if (level.getRats().get(i).getGender() == Rat.Gender.MALE) {
                            maleRatCount++;
                        }
                    }

                    double maleRatPercentage = level.getRats().size() * progressIncrease * (maleRatCount
                            / level.getRats().size()) * COMPLETE_BAR;
                    double currentProgress = level.getRats().size() * progressIncrease * COMPLETE_BAR;
                    LinearGradient g = LinearGradient.valueOf("linear-gradient(to top, "
                            + "blue 0%, blue " + maleRatPercentage + "%, pink " + maleRatPercentage + "%, pink "
                            + currentProgress + "%, grey " + currentProgress + "%, grey 100%)");
                    percentage.setFill(g);

                    //checks if game has ended
                    if (level.getRats().size() >= level.getMaxRats()) {
                        Level.setGameOver(true);
                        Level.setGameWon(false);
                        level.setScore(0);
                    } else if (level.getRats().size() == 0) {
                        Level.setGameOver(true);
                        Level.setGameWon(true);
                        if ((int) currentTick / 2 < level.getExpectedTime()) {
                            int timeLeft = (int) (level.getExpectedTime() - (currentTick / 2));
                            level.setScore(Level.getScore() + timeLeft);
                        }
                    }

                    //if game has ended load end screen and display relevant info.
                    if (Level.getGameOver()) {
                        ticker.cancel();
                        currentTick = 0;

                        if (Level.getGameWon()) {
                            try {
                                Score score = new Score(Level.getCurrentLevel(), Player.getPlayerName(),
                                        Level.getScore());
                                score.addToScoreBoard();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            level.setScore(0);
                            lblScore.setText("Score: " + Level.getScore());
                        }

                        Platform.runLater(() -> {
                            try {
                                StageFunctions.openGameOver();
                            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        };

        //run tickers task every 500ms until stopped
        ticker.schedule(task, DELAY, DELAY);
    }

    /**
     * Adds listeners to each fxml control and when clicked perform set action.
     */
    private void onActions() {
        settings.setOnAction(e -> {
            try {
                Level.setPaused(true);
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
            Level.setPaused(true);
            ConfirmDialog dialog = new ConfirmDialog();
            boolean result = dialog.getDecision("Exit Warning", "Are you sure you want to exit?");

            if (result) {
                level.setCurrentTick((int) currentTick);
                new LevelSave(level).save();
                System.exit(1);
            } else {
                Level.setPaused(false);
            }
        });

        restartBtn.setOnAction(e -> {
            Level.setPaused(true);
            ConfirmDialog dialog = new ConfirmDialog();
            boolean result = dialog.getDecision("Restart Warning", "Are you sure you want to restart?");

            if (result) {
                Level.setIsSave(false);
                ticker.cancel();
                currentTick = 0;

                try {
                    StageFunctions.changeScene("game", "Level " + Level.getCurrentLevel());
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
            Level.setPaused(false);
        });

        mainMenu.setOnAction(e -> {
            Level.setPaused(true);
            ConfirmDialog dialog = new ConfirmDialog();
            boolean result = dialog.getDecision("Return Warning",
                    "Are you sure you want to return to Main Menu?");

            if (result) {
                level.setCurrentTick((int) currentTick);
                new LevelSave(level).save();
                ticker.cancel();
                currentTick = 0;

                try {
                    StageFunctions.changeScene("main_menu", "Main Menu");
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            } else {
                Level.setPaused(false);
            }
        });

    }

    /**
     * Moves the set rat.
     *
     * @param rat the rat in which is to be moved.
     */
    private static void move(final Rat rat) {
        //sets all relevant information to get correct movement.
        Movement.tiles = level.getTiles();
        Movement.rat = rat;
        Movement.current = level.getTiles()[rat.getCurrentPosY()][rat.getCurrentPosX()];
        Movement.curX = rat.getCurrentPosX();
        Movement.curY = rat.getCurrentPosY();

        //based on direction try and move.
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

    /**
     * This method basically runs all relevant methods to each entity,
     * for instance will move all rats and activate each item.
     */
    private static void tick() {
        ArrayList<Rat> rats = level.getRats();
        ArrayList<Item> items = level.getItems();

        //moves adult rats if the tick is divisible by 2 (every second).
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

        //moves adult rats if the tick is divisible by 1 (every half second).
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

        //activates each item.
        for (int i = 0; i < items.size(); i++) {
            items.get(i).activate(level, gc);
        }

        //draws the updated game state.
        draw();
    }

    /**
     * This method just gathers the new game state and draws it to the canvas.
     */
    private static void draw() {
        //executed on fx thread.
        Platform.runLater(() -> {
            Tile[][] tiles = level.getTiles();
            ArrayList<Rat> rats = level.getRats();
            ArrayList<Item> items = level.getItems();

            //draw tiles
            for (int y = 0; y < tiles.length; y++) {
                for (int x = 0; x < tiles[y].length; x++) {
                    Image image = tiles[y][x].getImage();
                    gc.drawImage(image, x * TILE_SIZE, y * TILE_SIZE);
                }
            }

            //draw rats
            for (Rat rat : rats) {
                if (level.getTiles()[rat.getCurrentPosY()][rat.getCurrentPosX()].isCovering()) {
                    gc.drawImage(rat.getRotatedImage(), rat.getCurrentPosX() * TILE_SIZE,
                            rat.getCurrentPosY() * TILE_SIZE);
                }
            }

            //draw items
            for (Item item : items) {
                if (level.getTiles()[item.getCurrentPosY()][item.getCurrentPosX()].isCovering()) {
                    if (item.getType() == Item.TYPE.DEATH_RAT) {
                        //draws death rat in correct rotation
                        DeathRat deathRat = (DeathRat) item;
                        gc.drawImage(deathRat.getRotatedImage(), item.getCurrentPosX() * TILE_SIZE + OFFSET,
                                item.getCurrentPosY() * TILE_SIZE + OFFSET);

                    } else if (item.getType() == Item.TYPE.GAS) {
                        Gas gas = (Gas) item;
                        gc.drawImage(item.getImage(), item.getCurrentPosX() * TILE_SIZE + OFFSET,
                                item.getCurrentPosY() * TILE_SIZE + OFFSET);

                        //draws gas clouds
                        for (Tile tile : gas.getTiles()) {
                            //don't draw if tile is tunnel
                            if (tile.isCovering()) {
                                //fade out image when gas' hp is getting low
                                if (gas.getHp() <= START_FADE) {
                                    gc.drawImage(Resources.getEntityImage("gas-cloud" + gas.getHp()),
                                            tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE);
                                } else {
                                    gc.drawImage(Resources.getEntityImage("gas-cloud"),
                                            tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE);
                                }
                            }
                        }
                    } else if (item.getType() == Item.TYPE.STERILISATION) {
                        Sterilisation sterilisation = (Sterilisation) item;
                        gc.drawImage(item.getImage(), item.getCurrentPosX() * TILE_SIZE + OFFSET,
                                item.getCurrentPosY() * TILE_SIZE + OFFSET);

                        //draws sterilisation water
                        for (Tile tile : sterilisation.getDrawableTiles()) {
                            gc.drawImage(Resources.getEntityImage("splash"), tile.getX() * TILE_SIZE,
                                    tile.getY() * TILE_SIZE);
                        }
                    } else {
                        gc.drawImage(item.getImage(), item.getCurrentPosX() * TILE_SIZE + OFFSET,
                                item.getCurrentPosY() * TILE_SIZE + OFFSET);
                    }
                }
            }
        });
    }

}
