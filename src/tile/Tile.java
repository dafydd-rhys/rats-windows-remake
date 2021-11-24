package tile;

import javafx.scene.image.Image;
import entity.Entity;
import java.util.ArrayList;
import player.Player;

/**
 * LevelFileGenerator
 *
 * @author Dawid Wisniewski, Dafydd-Rhys Maund
 */
public class Tile {

    protected final ArrayList<Entity> entitiesOnTile;
    private int x;
    private int y;

    private final Image image;
    private final boolean isWalkable;
    private final boolean isCovering;

    public enum TYPE {
        THEMED(),
        PATH(),
        TUNNEL()
    }

    /**
     * Constructor
     * @param x
     * @param y
     * @param entitiesOnTile
     */
    public Tile(int x, int y, TYPE type, ArrayList<Entity> entitiesOnTile) {
        this.x = x;
        this.y = y;
        this.entitiesOnTile = entitiesOnTile;

        if (type == TYPE.THEMED) {
            if (Player.getTheme() == Player.THEME.SPRING) {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/grass.png");
            } else if (Player.getTheme() == Player.THEME.BEACH) {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/sand.png");
            } else {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/snow.png");
            }

            this.isWalkable = false;
            this.isCovering = false;
        } else if (type == TYPE.PATH) {
            this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/path.png");
            this.isWalkable = true;
            this.isCovering = false;
        } else {
            this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/tunnel.png");
            this.isWalkable = false;
            this.isCovering = true;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void addEntityToTile(Entity entity) {
        entitiesOnTile.add(entity);
    }

    public void removeEntityFromTile(Entity entity) {
        entitiesOnTile.remove(entity);
    }

    public ArrayList<Entity> getEntitiesOnTile() {
        return entitiesOnTile;
    }

    public Image getImage() {
        return image;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public boolean isCovering() {
        return !isCovering;
    }

}
