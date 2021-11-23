package tile;

import entity.Entity;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * LevelFileGenerator
 *
 * @author Dawid Wisniewski
 */
public class GrassTile extends Tile{

    public GrassTile(int x, int y, ArrayList<Entity> entitiesOnTile) {
        super(x, y, entitiesOnTile);
        image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/grass.png");
        this.isWalkable = false;
        this.isCovering = false;
        this.type = TYPE.GRASS;
    }

}
