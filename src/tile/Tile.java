package tile;

import javafx.scene.image.Image;
import entity.Entity;
/**
 * Superclass for different kinds of Tiles - GrassTile, PathTile, TunnelTile
 */
import java.util.ArrayList;

public abstract class Tile {

    //these are common for all of the subclasses, they are not preset
    private int coordX;
    private int coordY;
    private ArrayList<Entity> entitiesOnTile;

    //these three are for the subclasses only, they are preset for each subclass with different values
    protected Image image;
    protected boolean isWalkable;
    protected boolean isCovering;

    /**
     * Constructor
     * @param coordX
     * @param coordY
     * @param entitiesOnTile
     */
    public Tile(int coordX, int coordY, ArrayList<Entity> entitiesOnTile) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.entitiesOnTile = entitiesOnTile;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
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
        return isCovering;
    }
}
