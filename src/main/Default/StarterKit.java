package main.Default;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StarterKit extends Application {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 400;
    private static final int GRID_CELL_WIDTH = 50;
    private static final int GRID_CELL_HEIGHT = 50;
    private static final int GRID_WIDTH = 12;

    private Canvas canvas;
    private Image playerImage;
    private Image dirtImage;
    private Image iconImage;
    private int playerX = 0;
    private int playerY = 0;
    private Timeline tickTimeline;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Set up the new application.
     * @param primaryStage The stage that is to be used for the application.
     */
    public void start(Stage primaryStage) {
        playerImage = new Image("player.png");
        dirtImage = new Image("dirt.png");
        iconImage = new Image("icon.png");

        Pane root = buildGUI();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::processKeyEvent);

        tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);

        drawGame();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Process a key event due to a key being pressed, e.g., to move the player.
     * @param event The key event that was pressed.
     */
    public void processKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT) {
            playerX = playerX + 1;
        }

        drawGame();
        event.consume();
    }

    /**
     * Draw the game on the canvas.
     */
    public void drawGame() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int x = 0; x < GRID_WIDTH; x++) {
            gc.drawImage(dirtImage, x * GRID_CELL_WIDTH, 2 * GRID_CELL_HEIGHT);
        }
        gc.drawImage(playerImage, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);
    }

    /**
     * Reset the player's location and move them back to (0,0).
     */
    public void resetPlayerLocation() {
        playerX = 0;
        playerY = 0;
        drawGame();
    }

    /**
     * Move the player to roughly the center of the grid.
     */
    public void movePlayerToCenter() {
        // We just move the player to cell (5, 2)
        playerX = 5;
        playerY = 2;
        drawGame();
    }

    /**
     * This method is called periodically by the tick timeline
     * and would for, example move, perform logic in the game,
     * this might cause the bad guys to move (by e.g., looping
     * over them all and calling their own tick method).
     */
    public void tick() {
        playerX = playerX + 1;
        if (playerX > 11) {
            playerX = 0;
        }
        drawGame();
    }

    /**
     * React when an object is dragged onto the canvas.
     * @param event The drag event itself which contains data about the drag that occurred.
     */
    public void canvasDragDroppedOccurred(DragEvent event) {
        double x = event.getX();
        double y = event.getY();

        String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
        System.out.println(s);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(iconImage, x, y);
    }

    /**
     * Create the GUI.
     * @return The panel that contains the created GUI.
     */
    private Pane buildGUI() {
        BorderPane root = new BorderPane();
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        HBox toolbar = new HBox();
        toolbar.setSpacing(10);
        toolbar.setPadding(new Insets(10, 10, 10, 10));
        root.setTop(toolbar);

        Button resetPlayerLocationButton = new Button("Reset Player");
        toolbar.getChildren().add(resetPlayerLocationButton);
        resetPlayerLocationButton.setOnAction(e -> resetPlayerLocation());

        Button centerPlayerLocationButton = new Button("Center Player");
        toolbar.getChildren().add(centerPlayerLocationButton);
        centerPlayerLocationButton.setOnAction(e -> movePlayerToCenter());

        Button startTickTimelineButton = new Button("Start Ticks");
        Button stopTickTimelineButton = new Button("Stop Ticks");
        toolbar.getChildren().addAll(startTickTimelineButton, stopTickTimelineButton);
        stopTickTimelineButton.setDisable(true);

        startTickTimelineButton.setOnAction(e -> {
            startTickTimelineButton.setDisable(true);
            tickTimeline.play();
            stopTickTimelineButton.setDisable(false);
        });

        stopTickTimelineButton.setOnAction(e -> {
            stopTickTimelineButton.setDisable(true);
            tickTimeline.stop();
            startTickTimelineButton.setDisable(false);
        });
        ImageView draggableImage = new ImageView();
        draggableImage.setImage(iconImage);
        toolbar.getChildren().add(draggableImage);

        draggableImage.setOnDragDetected(event -> {
            Dragboard db = draggableImage.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("Hello");
            db.setContent(content);
            event.consume();
        });

        canvas.setOnDragOver(event -> {
            if (event.getGestureSource() == draggableImage) {
                event.acceptTransferModes(TransferMode.ANY);
                event.consume();
            }
        });

        canvas.setOnDragDropped(event -> {
            canvasDragDroppedOccurred(event);
            event.consume();
        });

        return root;
    }

}
