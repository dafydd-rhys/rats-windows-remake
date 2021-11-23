package entity.weapon;

import entity.rats.Rat;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * Poison
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Poison extends Item {

    public Poison(){
        this.entityName = "Poison";
        this.image = null;
        this.hp = 1;
        this.damage = 5;
        this.range = 1;
        this.friendlyFire = false;
        this.isAttackable = false;
    }

    public void activate() {

        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (int k = 0; k < entitiesOnTile.size(); k++) {
                if (entitiesOnTile.get(k).getEntityName().equals("Rat")){
                    Rat targetRat = (Rat) entitiesOnTile.get(k);
                    inflictDamage(this.damage, targetRat);
                    if (targetRat.getHp() <= 0) {
                        tile[this.currentPosY][this.currentPosX].removeEntityFromTile(targetRat);
                        entitiesOnTile.remove(targetRat);
                    }
                    this.hp -= 1;
                    tile[this.currentPosY][this.currentPosX].removeEntityFromTile(this);
                    break;
                }
            }
        }
    }
}
