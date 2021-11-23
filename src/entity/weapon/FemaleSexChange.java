package entity.weapon;

import entity.rats.Rat;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * FemaleSexChange
 *
 * @author Harry Boyce, Bryan Kok
 */

public class FemaleSexChange extends Item {

    public FemaleSexChange(){
        this.entityName = "FemaleSexChange";
        this.image = null;
        this.hp = 1;
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
                    if (!targetRat.isFemale()) {
                        targetRat.setFemale(true);
                        //add sprite change
                    }
                    this.hp -= 1;
                    tile[this.currentPosY][this.currentPosX].removeEntityFromTile(this);
                    break;
                }
            }
        }

    }

}
