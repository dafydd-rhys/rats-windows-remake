package controllers;

import com.jfoenix.controls.JFXButton;
import entity.rats.Rat;
import java.io.IOException;
import java.net.URL;
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

import main.level.Inventory;
import main.stage.StageFunctions;
import main.level.LevelFileGenerator;
import main.level.LevelFileReader;

/**
 * Main
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */
public class GameController implements Initializable {

    private Canvas canvas;
    private final String dir = System.getProperty("user.dir") + "/src/resources/images/game/entities/";
    private final Image bomb = new Image(dir + "bomb.png");
    private final Image deathRat = new Image(dir + "male-rat.png");

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

        draggableImage(bomb, "bomb", 0);
        draggableImage(deathRat, "deathRat", 1);
        setImages();
        onActions();
    }

    private void draggableImage(Image image, String itemString, int xOffset) {
        ImageView draggableImage = new ImageView();
        draggableImage.setImage(image);
        draggableImage.setFitHeight(50);
        draggableImage.setFitWidth(50);
        AnchorPane.setRightAnchor(draggableImage, 150.0);
        AnchorPane.setTopAnchor(draggableImage, 50.0 * xOffset);
        abilities.getChildren().add(draggableImage);

        draggableImage.setOnDragDetected(event -> {
            Dragboard db = draggableImage.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemString);
            db.setContent(content);
        });

        canvas.setOnDragOver(event -> {
            if (event.getGestureSource() == draggableImage) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });

        canvas.setOnDragDropped(event -> {
            dragAndDrop(event, canvas, draggableImage.getImage());
        });
    }

    public void dragAndDrop(DragEvent event, Canvas canvas, Image image) {
        int x = ((int) event.getX() / 50);
        int y = ((int) event.getY() / 50);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, x * 50 + 12.5, y * 50 + 12.5);

        if (this.bomb.equals(image)) {
            Inventory.createItem("bomb", x, y);
        } else if (this.deathRat.equals(image)) {
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

    private void tick() {
        //TickTimer.start();
    }

}
