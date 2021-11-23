package tile;

import javafx.scene.image.Image;
import entity.Entity;
import java.util.ArrayList;
import main.level.Level;

/**
 * LevelFileGenerator
 *
 * @author Dawid Wisniewski, Dafydd-Rhys Maund
 */
public abstract class Tile {

    //these are common for all the subclasses, they are not preset

    private int x;
    private int y;
    private final ArrayList<Entity> entitiesOnTile;

    //these three are for the subclasses only, they are preset for each subclass with different values
    protected TYPE type;
    protected Image image;
    protected boolean isWalkable;
    protected boolean isCovering;

    public enum TYPE {
        GRASS(),
        PATH(),
        TUNNEL()
    }

    /**
     * Constructor
     * @param x
     * @param y
     * @param entitiesOnTile
     */
    public Tile(int x, int y, ArrayList<Entity> entitiesOnTile) {
        this.x = x;
        this.y = y;
        this.entitiesOnTile = entitiesOnTile;
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

    public TYPE getType() {
        return type;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public boolean isCovering() {
        return isCovering;
    }

}
