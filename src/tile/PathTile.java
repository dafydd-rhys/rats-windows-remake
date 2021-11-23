package tile;

import entity.Entity;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * LevelFileGenerator
 *
 * @author Dawid Wisniewski
 */
public class PathTile extends Tile {

    public PathTile(int coordX, int coordY, ArrayList<Entity> entitiesOnTile) {
        super(coordX, coordY, entitiesOnTile);
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/path.png");
        this.isWalkable = false;
        this.isCovering = false;
        this.type = TYPE.PATH;
    }

}
