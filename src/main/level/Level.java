package main.level;

import entity.weapon.Item;
import tile.GrassTile;
import tile.PathTile;
import tile.Tile;
import tile.TunnelTile;

/**
 * LevelFileGenerator
 *
 * @author Dawid Wisniewski
 */
public class Level {

    private static Tile[][] tiles;

    public Level(Tile[][] tiles) {
        Level.tiles = tiles;
    }

    protected void placeItem(Item selectedItem, Tile tile) {
        if (tile.getClass().getName().equals("PathTile")) {
            tile.getEntitiesOnTile().add(selectedItem);
        } else {
            System.out.println("Invalid placement");
        }
    }

    public static void getEntitiesOnTile() {
        for (Tile[] tileY : tiles) {
            for (Tile tileX : tileY) {
                System.out.println(tileX.getEntitiesOnTile());
            }
        }
    }
    
    public static Tile[][] getTiles() {
        return tiles;
    }

}
