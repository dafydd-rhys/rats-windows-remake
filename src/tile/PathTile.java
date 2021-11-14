package tile;

import entity.Entity;

import java.util.ArrayList;

public class PathTile extends Tile {

    public PathTile(int coordX, int coordY, ArrayList<Entity> entitiesOnTile) {
        super(coordX, coordY, entitiesOnTile);
        this.image = null; //replace with file path
        this.isWalkable = false;
        this.isCovering = false;
    }
}
