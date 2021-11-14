package main;

import entity.Weapon;
import tile.Tile;

public class Level {

    protected void placeItem(Weapon selectedWeapon, Tile tile){

        if(tile.getClass().getName().equals("PathTile")){
            tile.getEntitiesOnTile().add(selectedWeapon);
        } else {
            System.out.println("Invalid placement");
        }

    }

}
