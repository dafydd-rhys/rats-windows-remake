package main.stage;

import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * ResizeHelper.java,
 * Can resize window and also drag window by the top bar.
 *
 * @author Dafydd -Rhys Maund (2003900)
 */
public class StageResizer {


    /**
     * Value represents how far off border mouse must be to trigger listener.
     */
    private final static int BORDER_SIZE = 4;

    /**
     * Add resize listener - so the stage listens for appropriate inputs.
     *
     * @param stage the stage
     */
    public static void addResizeListener(final Stage stage) {
        ResizeListener resizeListener = new ResizeListener(stage);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);

        //for each child of stage
        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            //adds further listener
            addListenerDeeply(child, resizeListener);
        }
    }

    /**
     * Adds listener to each child of node of stage - recursively.
     *
     * @param node node of stage
     * @param listener listener wanted to be added
     */
    private static void addListenerDeeply(final Node node, final EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);

        //recursively add listener
        if (node instanceof Parent parent) {
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

    /**
     * Resize listener added to nodes, allows for resize and drag.
     */
    private static class ResizeListener implements EventHandler<MouseEvent> {

        /**
         * Stage to be resizes/dragged.
         */
        private final Stage stage;
        /**
         * Default cursor image.
         */
        private Cursor cursorEvent = Cursor.DEFAULT;
        /**
         * startX for resize.
         */
        private double startX = 0;
        /**
         * startY for resize.
         */
        private double startY = 0;
        /**
         * startX for drag.
         */
        private double startScreenX = 0;
        /**
         * startY for resize.
         */
        private double startScreenY = 0;

        /**
         * Instantiates a new Resize listener.
         *
         * @param paramStage the stage
         */
        ResizeListener(final Stage paramStage) {
            this.stage = paramStage;
        }

        @Override
        public void handle(final MouseEvent mouseEvent) {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
            Scene scene = stage.getScene();

            double mouseEventX = mouseEvent.getSceneX();
            double mouseEventY = mouseEvent.getSceneY();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();

            //if mouse enters an area around stage allow for appropriate resize and add appropriate image
            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                if (mouseEventX < BORDER_SIZE && mouseEventY < BORDER_SIZE) {
                    cursorEvent = Cursor.NW_RESIZE;
                } else if (mouseEventX < BORDER_SIZE && mouseEventY > sceneHeight - BORDER_SIZE) {
                    cursorEvent = Cursor.SW_RESIZE;
                } else if (mouseEventX > sceneWidth - BORDER_SIZE && mouseEventY < BORDER_SIZE) {
                    cursorEvent = Cursor.NE_RESIZE;
                } else if (mouseEventX > sceneWidth - BORDER_SIZE && mouseEventY > sceneHeight - BORDER_SIZE) {
                    cursorEvent = Cursor.SE_RESIZE;
                } else if (mouseEventX < BORDER_SIZE) {
                    cursorEvent = Cursor.W_RESIZE;
                } else if (mouseEventX > sceneWidth - BORDER_SIZE) {
                    cursorEvent = Cursor.E_RESIZE;
                } else if (mouseEventY < BORDER_SIZE) {
                    cursorEvent = Cursor.N_RESIZE;
                } else if (mouseEventY > sceneHeight - BORDER_SIZE) {
                    cursorEvent = Cursor.S_RESIZE;
                } else {
                    cursorEvent = Cursor.DEFAULT;
                }

                //sets appropriate cursor
                scene.setCursor(cursorEvent);
            } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType)
                    || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)) {
                scene.setCursor(Cursor.DEFAULT);
            } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                //sets start X and Y for resize
                startX = stage.getWidth() - mouseEventX;
                startY = stage.getHeight() - mouseEventY;
            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
                //resizes window
                if (!Cursor.DEFAULT.equals(cursorEvent)) {
                    if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                        double minHeight = stage.getMinHeight() > (BORDER_SIZE * 2) ? stage.getMinHeight()
                                : (BORDER_SIZE * 2);
                        double maxHeight = stage.getMaxHeight();

                        if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent)
                                || Cursor.NE_RESIZE.equals(cursorEvent)) {
                            double newHeight = stage.getHeight() - (mouseEvent.getScreenY() - stage.getY());

                            if (newHeight >= minHeight && newHeight <= maxHeight) {
                                stage.setHeight(newHeight);
                                stage.setY(mouseEvent.getScreenY());
                            } else {
                                newHeight = Math.min(Math.max(newHeight, minHeight), maxHeight);
                                stage.setY(stage.getY() + stage.getHeight() - newHeight);
                                stage.setHeight(newHeight);
                            }
                        } else {
                            stage.setHeight(Math.min(Math.max(mouseEventY + startY, minHeight), maxHeight));
                        }
                    }

                    if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                        double minWidth = stage.getMinWidth() > (BORDER_SIZE * 2) ? stage.getMinWidth()
                                : (BORDER_SIZE * 2);
                        double maxWidth = stage.getMaxWidth();

                        if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent)
                                || Cursor.SW_RESIZE.equals(cursorEvent)) {
                            double newWidth = stage.getWidth() - (mouseEvent.getScreenX() - stage.getX());

                            if (newWidth >= minWidth && newWidth <= maxWidth) {
                                stage.setWidth(newWidth);
                                stage.setX(mouseEvent.getScreenX());
                            } else {
                                newWidth = Math.min(Math.max(newWidth, minWidth), maxWidth);
                                stage.setX(stage.getX() + stage.getWidth() - newWidth);
                                stage.setWidth(newWidth);
                            }
                        } else {
                            stage.setWidth(Math.min(Math.max(mouseEventX + startX, minWidth), maxWidth));
                        }
                    }
                }
            }

            //drags window
            if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                //sets start X and Y for drag
                startScreenX = mouseEvent.getScreenX();
                startScreenY = mouseEvent.getScreenY();
            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
                //drags window
                if (Cursor.DEFAULT.equals(cursorEvent)) {
                    ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();

                    for (Node child : children) {
                        if (child.getId() != null) {
                            //child must be bar to be able to drag (top of stage)
                            if (Objects.equals(child.getId(), "bar")) {
                                child.setOnMouseDragged(e -> {
                                    stage.setX(stage.getX() + mouseEvent.getScreenX() - startScreenX);
                                    startScreenX = mouseEvent.getScreenX();
                                    stage.setY(stage.getY() + mouseEvent.getScreenY() - startScreenY);
                                    startScreenY = mouseEvent.getScreenY();
                                });
                            }
                        }
                    }
                }
            }
        }
    }
}


