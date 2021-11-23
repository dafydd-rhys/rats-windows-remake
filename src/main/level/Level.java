package main.level;

import entity.rats.Rat;
import entity.weapon.Item;
import java.util.ArrayList;
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
    private static ArrayList<Rat> rats;
    private static Tile[][] items;

    public Level(Tile[][] tiles, ArrayList<Rat> rats) {
        Level.tiles = tiles;
        Level.rats = rats;
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

    public static ArrayList<Rat> getRats() {
        return rats;
    }

}
