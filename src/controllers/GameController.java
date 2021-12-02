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
import javafx.scene.layout.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;
import main.level.*;
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
 * Main
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 * @author Harry Boyce (2011556)
 * @author Maurice Petersen (2013396)
 */
public class GameController implements Initializable {

    /**  */
    @FXML private AnchorPane abilities;
    /**  */
    @FXML private ScrollPane gameScroll;
    /**  */
    @FXML private BorderPane game;
    /**  */
    @FXML private JFXButton sfx;
    /**  */
    @FXML private JFXButton music;
    /**  */
    @FXML private JFXButton settings;
    /**  */
    @FXML private JFXButton minimize;
    /**  */
    @FXML private JFXButton maximise;
    /**  */
    @FXML private JFXButton exit;
    /**  */
    @FXML private ImageView musicImage;
    /**  */
    @FXML private ImageView effectsImage;
    /**  */
    @FXML private JFXTextArea lblLevel;
    /**  */
    @FXML private JFXTextArea lblTime;
    /**  */
    @FXML private JFXTextArea lblScore;
    /**  */
    @FXML private JFXTextArea lblExpected;
    /**  */
    @FXML private JFXButton restartBtn;
    /**  */
    @FXML private JFXButton mainMenu;
    @FXML
    private ProgressBar pr;
    @FXML
    private Rectangle percentage;

    private static Level level;
    private static double progressIncrease;
    private static GraphicsContext gc;
    private static double currentTick;
    private Timer ticker;

    /**
     *
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Level.setGameOver(false);
        Level.setGameWon(false);
        lblTime.setEditable(false);
        lblExpected.setEditable(false);
        pr.setStyle("-fx-accent: #00ff00;");

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

        Canvas canvas = new Canvas(levelReader.getSizeX() * 50, levelReader.getSizeY() * 50);
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        game.setCenter(canvas);
        gameScroll.setPannable(true);
        
        if (!Level.isSave()) {
            //generates map and starts items generating
            LevelFileGenerator generator = new LevelFileGenerator(levelReader.getTimeToGenerate(), gc, levelReader.getSizeX(),
                    levelReader.getSizeY(), levelReader.getLevel(), levelReader.getSpawns(),
                    levelReader.getExpectedTime(), levelReader.getMaxRats());
            level = generator.getLevel();
            level.setScore(0);
        } else {
            assert levelLoader != null;
            LevelLoadGenerator loadGenerator = new LevelLoadGenerator(levelReader.getTimeToGenerate(), gc, levelReader.getSizeX(),
                    levelReader.getSizeY(), levelReader.getLevel(), levelLoader.getRatSpawns(), levelLoader.getItemSpawns(),
                    levelReader.getExpectedTime(), levelReader.getMaxRats());
            level = loadGenerator.getLevel();
            currentTick = levelLoader.getCurrentTick();
            level.setScore(levelLoader.getScore());
        }

        pr.setProgress(0);
        progressIncrease = (double) (100 / level.getMaxRats()) / 100;
        lblLevel.setText("Level: " + Level.getCurrentLevel());
        lblExpected.setText("Expected: " + level.getExpectedTime() + " seconds");

        Inventory.clear();
        new ItemGenerator(level, canvas, gc, abilities);
        if (Level.isSave()) {
            assert levelLoader != null;
            new ItemLoader(levelLoader.getInventory(), abilities);
        }

        onActions();

        musicImage.setOpacity(Audio.isMuted("music"));
        effectsImage.setOpacity(Audio.isMuted("effects"));

        ticker = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (Level.getPaused()) {
                    currentTick += 1;
                    if (currentTick % 2 == 0) {
                        lblTime.setText("Current: " + (int) currentTick / 2 + " seconds");
                    }
                    lblScore.setText("Score: " + Level.getScore());
                    tick();
                    pr.setProgress(level.getRats().size() * progressIncrease);

                    double maleRatCount = 0;
                    for (int i = 0; i<level.getRats().size(); i++){
                        if (level.getRats().get(i).getGender() == Rat.Gender.MALE ){
                            maleRatCount += 1;
                        }
                    }
                    double maleRatPercentage = level.getRats().size() * progressIncrease * (maleRatCount / level.getRats().size() ) * 100;
                    double currentProgress = level.getRats().size() * progressIncrease * 100;
                    LinearGradient g = LinearGradient.valueOf("linear-gradient(to top, " +
                            "blue 0%, blue " + maleRatPercentage + "%, pink " + maleRatPercentage + "%, pink "
                            + currentProgress + "%, grey " + currentProgress + "%, grey 100%)");
                    percentage.setFill(g);

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

                    if (Level.getGameOver()) {
                        ticker.cancel();
                        currentTick = 0;

                        if (Level.getGameWon()) {
                            try {
                                Score score = new Score(Level.getCurrentLevel(), Player.getPlayerName(), Level.getScore());
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

        //run tick method every 500ms until stopped
        ticker.schedule(task, 500, 500);
    }

    /**
     *
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
            try {
                level.setCurrentTick((int) currentTick);
                LevelSave ls = new LevelSave(level);
                ls.save();
                StageFunctions.exit();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        });

        restartBtn.setOnAction(e -> {
            try {
                Level.setIsSave(false);
                ticker.cancel();
                currentTick = 0;
                StageFunctions.changeScene("\\src\\resources\\fxml\\game.fxml", "Level " + Level.getCurrentLevel());
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        mainMenu.setOnAction(e -> {
            try {
                level.setCurrentTick((int) currentTick);
                LevelSave ls = new LevelSave(level);
                ls.save();
                ticker.cancel();
                currentTick = 0;
                StageFunctions.changeScene("\\src\\resources\\fxml\\main_menu.fxml", "Main Menu");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

    }

    /**
     *
     *
     * @param rat
     */
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

    /**
     *
     */
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
            items.get(i).activate(level, gc);
        }
        draw();
    }

    /**
     *
     */
    private static void draw() {
        Platform.runLater(() -> {
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
                    } else if (item.getType() == Item.TYPE.GAS) {
                        Gas gas = (Gas) item;
                        Image smoke = new Image(System.getProperty("user.dir") +
                                "\\src\\resources\\images\\game\\entities\\gas-cloud.png");
                        String opacity = System.getProperty("user.dir") +
                                "\\src\\resources\\images\\game\\entities\\gas-cloud-opacity";

                        gc.drawImage(item.getImage(), item.getCurrentPosX() * 50 + 10, item.getCurrentPosY() * 50 + 10);
                        for (Tile tile : gas.getTiles()) {
                            if (gas.getHp() <= 3) {
                                gc.drawImage(new Image(opacity + gas.getHp() + ".png"), tile.getX() * 50, tile.getY() * 50);
                            } else {
                                gc.drawImage(smoke, tile.getX() * 50, tile.getY() * 50);
                            }
                        }
                    } else if (item.getType() == Item.TYPE.STERILISATION) {
                        Sterilisation sterilisation = (Sterilisation) item;
                        Image splash = new Image(System.getProperty("user.dir") +
                                "\\src\\resources\\images\\game\\entities\\splash.png");

                        gc.drawImage(item.getImage(), item.getCurrentPosX() * 50 + 10, item.getCurrentPosY() * 50 + 10);
                        for (Tile tile : sterilisation.getDrawableTiles()) {
                            gc.drawImage(splash, tile.getX() * 50, tile.getY() * 50);
                        }
                    } else {
                        gc.drawImage(item.getImage(), item.getCurrentPosX() * 50 + 10, item.getCurrentPosY() * 50 + 10);
                    }
                }
            }
        });
    }

}