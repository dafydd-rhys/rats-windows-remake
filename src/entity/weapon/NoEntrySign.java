package entity.weapon;

import entity.rats.Rat;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * NoEntrySign
 *
 * @author Harry Boyce, Bryan Kok
 */

public class NoEntrySign extends Item{

    public NoEntrySign(){
        this.entityName = "No Entry Sign";
        this.image = null;
        this.hp = 5;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
    }

    public void activate() {

        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (int k = 0; k < entitiesOnTile.size(); k++) {
                if (entitiesOnTile.get(k).getEntityName().equals("Rat")){
                    Rat targetRat = (Rat) entitiesOnTile.get(k);
                    // [needs a method to prevent targetRat from passing here]
                    this.hp -= 1;
                    switch (this.hp) {
                        case 4:
                            this.image = null;
                            break;
                        case 3:
                            this.image = null;
                            break;
                        case 2:
                            this.image = null;
                            break;
                        case 1:
                            this.image = null;
                            break;
                        case 0:
                            this.image = null;
                            tile[this.currentPosY][this.currentPosX].removeEntityFromTile(this);
                            break;
                    }

                }
            }
        }


    }

}
