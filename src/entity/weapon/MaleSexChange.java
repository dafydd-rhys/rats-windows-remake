package entity.weapon;

import entity.rats.Rat;
import javafx.scene.image.Image;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * MaleSexChange
 *
 * @author Harry Boyce, Bryan Kok
 */

public class MaleSexChange extends Item {

    public MaleSexChange(int x, int y){
        this.entityName = "MaleSexChange";
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/male-change.png");
        this.hp = 1;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = false;
        this.isAttackable = false;
        this.currentPosX = x;
        this.currentPosY = y;
    }

    public void activate() {

        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (Entity entity : entitiesOnTile) {
                if (entity.getEntityName().equals("Rat")) {
                    Rat targetRat = (Rat) entity;
                    if (targetRat.isFemale()) {
                        targetRat.setFemale(false);
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
