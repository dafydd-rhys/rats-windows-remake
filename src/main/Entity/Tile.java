package Tiles;

import Entities.Entity;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Tile {

    protected int coordX;
    protected int coordY;
    protected ArrayList<Entity> entitiesOnTile;
    protected Image image;
    protected boolean isWalkable;
    protected boolean isCovering;

    public Tile(int coordX, int coordY, ArrayList<Entity> entitiesOnTile) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.entitiesOnTile = entitiesOnTile;
    }

    public boolean isNextTileWalkable(Tile candidateTile){
        if(candidateTile.isWalkable){
            return candidateTile.isWalkable();
        } else {
            return false;
        }
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

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public ArrayList<Entity> getEntitiesOnTile() {
        return entitiesOnTile;
    }

    public void setEntitiesOnTile(ArrayList<Entity> entitiesOnTile) {
        this.entitiesOnTile = entitiesOnTile;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public boolean isCovering() {
        return isCovering;
    }

    public void setCovering(boolean covering) {
        isCovering = covering;
    }


}
