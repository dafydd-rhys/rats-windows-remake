package entity.weapon;

import entity.Item;
import entity.Rat;
import javafx.scene.image.Image;
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
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/female-change.png");
        this.hp = 1;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.type = TYPE.FEMALE_CHANGE;
        this.yOffset = 3;
    }

    public void activate() {

        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (Entity entity : entitiesOnTile) {
                if (entity.getEntityName().equals("Rat")) {
                    Rat targetRat = (Rat) entity;
                    if (targetRat.getGender() != Rat.Gender.FEMALE) {
                        targetRat.setGender(Rat.Gender.FEMALE);
                        targetRat.setImage(new Image(System.getProperty("user.dir") + "\\src\\resources\\images\\game\\entities\\female-rat.png"));
                        // TODO add sprite change
                    }
                    this.hp -= 1;
                    Level.getItems().remove(this);
                    entitiesOnTile.remove(this);
                    break;
                }
            }
        }

    }

}
