package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

public class PlayerController {
    private final String dir = System.getProperty("user.dir") + "/src/resources/images/";
    private final Image bomb = new Image(dir + "bomb.png");
    Point2D dragDistance = null;

    public PlayerController(Canvas canvas) {
        System.out.println("Inside the playerController!");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bomb,10,10, 35, 35);
    }
}
