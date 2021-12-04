package tile;

import javafx.scene.image.Image;
import entity.Entity;
import java.util.ArrayList;
import main.Resources;
import player.Player;

/**
 * The class responsible for the attributes of a Tile.
 * There are 3 kinds of Tiles: Path, Tunnel and Grass.
 * Each one of them has different properties and image (selected theme also affects it).
 *
 * @author Dawid Wisniewski
 * @author Dafydd -Rhys Maund (2003900)
 */
public class Tile {

    /** The list of entities that are currently standing on the Tile */
    protected final ArrayList<Entity> entitiesOnTile;
    private int x;
    private int y;
    /** The image/texture of the Tile */
    private final Image image;
    /** Indicates whether the Tile can be walked on */
    private final boolean isWalkable;
    /** Indicates whether the Tile should cover the entities that are standing on it */
    private final boolean isCovering;

    /**
     * The enum Type.
     */
    public enum TYPE {
        THEMED(),
        PATH(),
        TUNNEL()
    }

    /**
     * Constructor
     *
     * @param x              the x
     * @param y              the y
     * @param type           the type
     * @param entitiesOnTile the entities on tile
     */
    public Tile(int x, int y, TYPE type, ArrayList<Entity> entitiesOnTile) {
        this.x = x;
        this.y = y;
        this.entitiesOnTile = entitiesOnTile;

        if (type == TYPE.THEMED) {
            if (Player.getTheme() == Player.THEME.SPRING) {
                image = Resources.getTileImage("grass");
            } else if (Player.getTheme() == Player.THEME.BEACH) {
                image = Resources.getTileImage("sand");
            } else {
                image = Resources.getTileImage("snow");
            }

            this.isWalkable = false;
            this.isCovering = false;
        } else if (type == TYPE.PATH) {
            if (Player.getTheme() == Player.THEME.SPRING) {
                image = Resources.getTileImage("path");
            } else if (Player.getTheme() == Player.THEME.BEACH) {
                image = Resources.getTileImage("path_sand");
            } else {
                image = Resources.getTileImage("path_snow");
            }
            this.isWalkable = true;
            this.isCovering = false;
        } else {
            if (Player.getTheme() == Player.THEME.SPRING) {
                image = Resources.getTileImage("tunnel");
            } else if (Player.getTheme() == Player.THEME.BEACH) {
                image = Resources.getTileImage("tunnel_sand");
            } else {
                image = Resources.getTileImage("tunnel_snow");
            }
            this.isWalkable = true;
            this.isCovering = true;
        }
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Add entity to tile.
     *
     * @param entity the entity
     */
    public void addEntityToTile(Entity entity) {
        entitiesOnTile.add(entity);
    }

    /**
     * Remove entity from tile.
     *
     * @param entity the entity
     */
    public void removeEntityFromTile(Entity entity) {
        entitiesOnTile.remove(entity);
    }

    /**
     * Gets entities on tile.
     *
     * @return the entities on tile
     */
    public ArrayList<Entity> getEntitiesOnTile() {
        return entitiesOnTile;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Is walkable boolean.
     *
     * @return the boolean
     */
    public boolean isWalkable() {
        return isWalkable;
    }

    /**
     * Is covering boolean.
     *
     * @return the boolean
     */
    public boolean isCovering() {
        return !isCovering;
    }

}
