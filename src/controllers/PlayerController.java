package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

import static java.lang.Math.floor;

public class PlayerController {
    private final String dir = System.getProperty("user.dir") + "/src/resources/images/";
    private final Image bomb = new Image(dir + "bomb.png");
    Point2D dragDistance = null;

    public void canvasDragDroppedOccurred(DragEvent event, Canvas canvas) {
        double x = ((int) event.getX() / 50) * 50;
        double y = ((int) event.getY() / 50) * 50;

        String s = String.format("player dropped bomb at (%f, %f) of the canvas.", x, y);
        System.out.println(s);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bomb, x, y);
    }

    public PlayerController(AnchorPane ap,Canvas canvas) {
        //System.out.println("Inside the playerController!");

        /*
        ImageView imageView = new ImageView();
        imageView.setImage(bomb);
        imageView.setFitHeight(35);
        imageView.setFitWidth(35);
        imageView.setX(400);
        imageView.setY(400);
        AnchorPane.setRightAnchor(imageView, 150.0);
        AnchorPane.setTopAnchor(imageView, 30.0);
        ap.getChildren().add(imageView);
         */

        /*
        imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imageView.startDragAndDrop(TransferMode.MOVE);
                System.out.println("Drag deticity!");
                ap.setOnMouseMoved((MouseEvent me)->
                {
                    imageView.setX(me.getX());
                    imageView.setY(me.getY());
                });
            }
        });

         */

        ImageView draggableImage = new ImageView();
        draggableImage.setImage(bomb);
        draggableImage.setFitHeight(40);
        draggableImage.setFitWidth(40);
        AnchorPane.setRightAnchor(draggableImage, 150.0);
        AnchorPane.setTopAnchor(draggableImage, 30.0);
        ap.getChildren().add(draggableImage);

        draggableImage.setOnDragDetected(event -> {
            Dragboard db = draggableImage.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("bomb");
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
            canvasDragDroppedOccurred(event,canvas);
            event.consume();
        });

        /*
        ap.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imageView.setY((newValue.doubleValue() - imageView.getFitHeight()) / 2);
            }
        });
        ap.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imageView.setY((newValue.doubleValue() - imageView.getFitHeight()) / 2);
            }
        });
        imageView.addEventFilter(MouseDragEvent.MOUSE_PRESSED, event -> {
            dragDistance = new Point2D(event.getSceneX(), event.getSceneY());
            dragDistance = dragDistance.subtract(ap.localToScene(new Point2D(imageView.getX(), imageView.getY())));
        });

        imageView.addEventFilter(MouseDragEvent.MOUSE_DRAGGED, event -> {
            if (event.isPrimaryButtonDown()) {
                Point2D px = new Point2D(event.getSceneX(), event.getSceneY());
                px = ap.sceneToLocal(px.subtract(dragDistance));
                imageView.setX(px.getX());
                imageView.setY(px.getY());
            }
        });

 */
    }
}
