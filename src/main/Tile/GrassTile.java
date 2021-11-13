package Tiles;

import Entities.Entity;

import java.util.ArrayList;

public class GrassTile extends Tile{

    public GrassTile(int coordX, int coordY, ArrayList<Entity> entitiesOnTile) {
        super(coordX, coordY, entitiesOnTile);
        this.image = null; //replace with file path
        this.isWalkable = false;
        this.isCovering = false;
    }
}
