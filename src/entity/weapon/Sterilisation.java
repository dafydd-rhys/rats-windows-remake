package entity.weapon;

import entity.Entity;
import entity.rats.Rat;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * Sterilisation
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Sterilisation extends Item{

    public Sterilisation(){
        this.entityName = "Sterilisation";
        this.image = null;
        this.hp = 8; //8 ticks = 4 seconds
        this.damage = 0;
        this.range = 2;
        this.friendlyFire = false;
        this.isAttackable = false;
    }

    public void activate() {
        Tile[][] tiles = Level.getTiles();
        for (int i = 0; i < this.range; i++) {
            for (int j = 0; j < this.range; j++) {
                ArrayList<Entity> entitiesOnTile = tiles[this.currentPosY + j - 1][this.currentPosX + i - 1].getEntitiesOnTile();
                if (entitiesOnTile != null) {
                    for (int k = 0; k < entitiesOnTile.size(); k++) {
                        if (entitiesOnTile.get(k).getEntityName().equals("Rat")){
                            Rat targetRat = (Rat) entitiesOnTile.get(k);
                            targetRat.setSterilised(true);
                        }
                    }
                }
            }
        }
        this.hp -= 1;
    }

}
