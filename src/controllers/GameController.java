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

import javafx.event.Event;
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
import javax.swing.*;

import javafx.scene.paint.Paint;
import player.Inventory.ItemGenerator;
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
    private JFXTextArea levelBox;
    @FXML
    private JFXTextArea timerBox;
    @FXML
    private JFXTextArea scoreBox;

    private static GraphicsContext gc;
    private static double currentTick;
    private int score = 0;
    private static boolean gameOver = false;

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
        checkMouseEnter();

        try {
            LevelFileReader level = new LevelFileReader(Level.currentLevel);
            new LevelFileGenerator(level.getTimeToGenerate(), gc, level.getSizeX(), level.getSizeY(),
                    level.getLevel(), level.getSpawns(), level.getExpectedTime(), level.getMaxRats());
        } catch (IOException e) {
            e.printStackTrace();
        }

        levelBox.setText("Level: " + Level.currentLevel);
        timerBox.setText("Time Left: " + Level.getExpectedTime());
        scoreBox.setText("Score: " + score);

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
    }

    private static final Timer ticker = new Timer(500, e -> {
        currentTick += 1;

        tick();
    });

    private static void tick() {
        ArrayList<Rat> rats = Level.getRats();
        ArrayList<Item> items = Level.getItems();

        for (int i = 0; i < items.size(); i++) {
            items.get(i).activate();
        }

        if (currentTick % 2 == 0) {
            for (Rat rat : new ArrayList<>(rats)) {
                if (rat.isAdult()) {
                    move(rat);
                    // TODO Fix baby rats not spawning on right tile.
                    rat.findPartner(Level.getTiles()[rat.getCurrentPosX()][rat.getCurrentPosY()]);
                    rat.giveBirth();
                }
            }
        } else {
            for (Rat rat : rats) {
                if (!rat.isAdult()) {
                    move(rat);
                    rat.growUp();
                }
            }
        }
        draw();
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

        for (Item item : items) {
            if (item.getType() == Item.TYPE.DEATH_RAT) {
                DeathRat deathRat = (DeathRat) item;
                gc.drawImage(deathRat.getRotatedImage(), item.getCurrentPosX() * 50 + 10, item.getCurrentPosY() * 50 + 10);
            } else {
                gc.drawImage(item.getImage(), item.getCurrentPosX() * 50 + 10, item.getCurrentPosY() * 50 + 10);
            }
        }
    }

    private static HBox BombBox = new HBox();
    private static HBox DeathRatBox = new HBox();
    private static HBox FemaleSexChangeBox = new HBox();
    private static HBox GasBox = new HBox();
    private static HBox MaleSexChangeBox = new HBox();
    private static HBox NoEntryBox = new HBox();
    private static HBox PoisonBox = new HBox();
    private static HBox SterilisationBox = new HBox();
    //private static HBox BombBox = new HBox();
    public void checkMouseEnter()
    {
        //HBox hbox = new HBox();
        //hbox.setStyle("-fx-background-color:#ffff55");
        BombBox.setPrefWidth(143);
        BombBox.setPrefHeight(40);
        AnchorPane.setRightAnchor(BombBox, 0.0);
        AnchorPane.setTopAnchor(BombBox, 5.0);
        DeathRatBox.setPrefWidth(143);
        DeathRatBox.setPrefHeight(40);
        AnchorPane.setRightAnchor(DeathRatBox, 0.0);
        AnchorPane.setTopAnchor(DeathRatBox, 45.0);
        FemaleSexChangeBox.setPrefWidth(143);
        FemaleSexChangeBox.setPrefHeight(40);
        AnchorPane.setRightAnchor(FemaleSexChangeBox, 0.0);
        AnchorPane.setTopAnchor(FemaleSexChangeBox, 85.0);
        GasBox.setPrefWidth(143);
        GasBox.setPrefHeight(40);
        AnchorPane.setRightAnchor(GasBox, 0.0);
        AnchorPane.setTopAnchor(GasBox, 125.0);
        MaleSexChangeBox.setPrefWidth(143);
        MaleSexChangeBox.setPrefHeight(40);
        AnchorPane.setRightAnchor(MaleSexChangeBox, 0.0);
        AnchorPane.setTopAnchor(MaleSexChangeBox, 165.0);
        NoEntryBox.setPrefWidth(143);
        NoEntryBox.setPrefHeight(40);
        AnchorPane.setRightAnchor(NoEntryBox, 0.0);
        AnchorPane.setTopAnchor(NoEntryBox, 205.0);
        PoisonBox.setPrefWidth(143);
        PoisonBox.setPrefHeight(40);
        AnchorPane.setRightAnchor(PoisonBox, 0.0);
        AnchorPane.setTopAnchor(PoisonBox, 245.0);
        SterilisationBox.setPrefWidth(143);
        SterilisationBox.setPrefHeight(40);
        AnchorPane.setRightAnchor(SterilisationBox, 0.0);
        AnchorPane.setTopAnchor(SterilisationBox, 285.0);
        abilities.getChildren().addAll(BombBox,DeathRatBox,FemaleSexChangeBox,GasBox,MaleSexChangeBox,NoEntryBox,PoisonBox,SterilisationBox);

        BombBox.setOnMouseEntered(e ->
        {
            showSquare("Bomb");
        });
        BombBox.setOnMouseExited(e ->
        {
            BombBox.setBorder(null);
        });

        DeathRatBox.setOnMouseEntered(e ->
        {
            showSquare("DeathRat");
        });
        DeathRatBox.setOnMouseExited(e ->
        {
            DeathRatBox.setBorder(null);
        });

        FemaleSexChangeBox.setOnMouseEntered(e ->
        {
            showSquare("FemaleSexChange");
        });
        FemaleSexChangeBox.setOnMouseExited(e ->
        {
            FemaleSexChangeBox.setBorder(null);
        });

        GasBox.setOnMouseEntered(e ->
        {
            showSquare("Gas");
        });
        GasBox.setOnMouseExited(e ->
        {
            GasBox.setBorder(null);
        });

        MaleSexChangeBox.setOnMouseEntered(e ->
        {
            showSquare("MaleSexChange");
        });
        MaleSexChangeBox.setOnMouseExited(e ->
        {
            MaleSexChangeBox.setBorder(null);
        });

        NoEntryBox.setOnMouseEntered(e ->
        {
            showSquare("NoEntry");
        });
        NoEntryBox.setOnMouseExited(e ->
        {
            NoEntryBox.setBorder(null);
        });

        PoisonBox.setOnMouseEntered(e ->
        {
            showSquare("Poison");
        });
        PoisonBox.setOnMouseExited(e ->
        {
            PoisonBox.setBorder(null);
        });
        SterilisationBox.setOnMouseEntered(e ->
        {
            showSquare("Sterilisation");
        });
        SterilisationBox.setOnMouseExited(e ->
        {
            SterilisationBox.setBorder(null);
        });
    }
    public static void showSquare(String item)
    {
        if(item.equals("Bomb"))
        {
            BombBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#ff0000"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
        }
        else if(item.equals("DeathRat"))
        {
            DeathRatBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#ff0000"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
        }
        else if(item.equals("FemaleSexChange"))
        {
            FemaleSexChangeBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#ff0000"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
        }
        else if(item.equals("Gas"))
        {
            GasBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#ff0000"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
        }
        else if(item.equals("MaleSexChange"))
        {
            MaleSexChangeBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#ff0000"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
        }
        else if(item.equals("NoEntry"))
        {
            NoEntryBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#ff0000"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
        }
        else if(item.equals("Poison"))
        {
            PoisonBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#ff0000"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
        }
        else if(item.equals("Sterilisation"))
        {
            SterilisationBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#ff0000"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
        }
    }

    public static void hideSquare(String item)
    {
        if(item.equals("Bomb"))
        {
            BombBox.setBorder(null);
        }
        else if(item.equals("DeathRat"))
        {
            DeathRatBox.setBorder(null);
        }
        else if(item.equals("FemaleSexChange"))
        {
            FemaleSexChangeBox.setBorder(null);
        }
        else if(item.equals("Gas"))
        {
            GasBox.setBorder(null);
        }
        else if(item.equals("MaleSexChange"))
        {
            MaleSexChangeBox.setBorder(null);
        }
        else if(item.equals("NoEntryBox"))
        {
            MaleSexChangeBox.setBorder(null);
        }
        else if(item.equals("Poison"))
        {
            PoisonBox.setBorder(null);
        }
        else if(item.equals("Sterilisation"))
        {
            SterilisationBox.setBorder(null);
        }
    }

}