package main.level;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * LevelFileGenerator
 *
 * @author Dafydd-Rhys Maund (2003900)
 */
public class LevelFileGenerator {

    private final char[][] level;
    private final char[][] spawns;
    private final Image male = new Image("src/resources/images/male-rat.png");
    private final Image female = new Image("src/resources/images/female-rat.png");
    private final Image tunnel = new Image("src/resources/images/tunnel.png");
    private final Image path = new Image("src/resources/images/path.png");
    private final Image grass = new Image("src/resources/images/grass.png");

    public LevelFileGenerator(GridPane pane, char[][] level, char[][] spawns) {
        this.level = level;
        this.spawns = spawns;
        generateLevel(pane);
    }

    private void generateLevel(GridPane pane) {
        RowConstraints constraints = new RowConstraints(50);
        pane.getRowConstraints().add(constraints);

        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[i].length; j++) {
                if (level[i][j] == 'G') {
                    pane.add(new ImageView(grass), i, j);
                } else if (level[i][j] == 'P') {
                    pane.add(new ImageView(path), i, j);
                } else {
                    pane.add(new ImageView(tunnel), i, j);
                }
            }
        }

        for (int i = 0; i < spawns.length; i++) {
            for (int j = 0; j < spawns[i].length; j++) {
                Node tile = getNode(pane, i, j);
                if (spawns[i][j] == 'M') {
                    //add male rat to tile
                } else if (spawns[i][j] == 'F') {
                    //add female rat to tile
                }
            }
        }
    }

    private Node getNode(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

}
