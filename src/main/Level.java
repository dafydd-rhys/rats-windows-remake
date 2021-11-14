package main;

import entity.Item;
import tile.Tile;

public class Level {

    protected void placeItem(Item selectedItem, Tile tile){

        if(tile.getClass().getName().equals("PathTile")){
            tile.getEntitiesOnTile().add(selectedItem);
        } else {
            System.out.println("Invalid placement");
        }

    }

}