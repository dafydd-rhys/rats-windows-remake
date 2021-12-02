package tile;

import javafx.scene.image.Image;
import entity.Entity;
import java.util.ArrayList;
import player.Player;

/**
 * LevelFileGenerator
 *
 * @author Dawid Wisniewski
 * @author Dafydd -Rhys Maund (2003900)
 */
public class Tile {

    /** Entities on tile */
    protected final ArrayList<Entity> entitiesOnTile;
    /** */
    private int x;
    /** */
    private int y;
    /** */
    private final Image image;
    /** */
    private final boolean isWalkable;
    /** */
    private final boolean isCovering;

    /**
     * The enum Type.
     */
    public enum TYPE {
        /**
         * Themed type.
         */
        THEMED(),
        /**
         * Path type.
         */
        PATH(),
        /**
         * Tunnel type.
         */
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
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/grass.png");
            } else if (Player.getTheme() == Player.THEME.BEACH) {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/sand.png");
            } else {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/snow.png");
            }

            this.isWalkable = false;
            this.isCovering = false;
        } else if (type == TYPE.PATH) {
            if (Player.getTheme() == Player.THEME.SPRING) {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/path.png");
            } else if (Player.getTheme() == Player.THEME.BEACH) {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/path_sand.png");
            } else {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/path_snow.png");
            }
            this.isWalkable = true;
            this.isCovering = false;
        } else {
            if (Player.getTheme() == Player.THEME.SPRING) {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/tunnel.png");
            } else if (Player.getTheme() == Player.THEME.BEACH) {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/tunnel_sand.png");
            } else {
                image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/tiles/tunnel_snow.png");
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
