package entity.weapon;

import entity.Item;
import entity.Rat;
import javafx.scene.image.Image;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;

/**
 * MaleSexChange
 *
 * @author Harry Boyce, Bryan Kok
 */

public class MaleSexChange extends Item {

    public MaleSexChange(){
        this.entityName = "MaleSexChange";
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/male-change.png");
        this.hp = 1;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = false;
        this.isAttackable = false;
        this.type = TYPE.MALE_CHANGE;
        this.yOffset = 4;
    }

    public void activate() {

        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (Entity entity : entitiesOnTile) {
                if (entity.getEntityName().equals("Rat")) {
                    Rat targetRat = (Rat) entity;
                    if (targetRat.getGender() != Rat.Gender.MALE) {
                        targetRat.setGender(Rat.Gender.MALE);
                        targetRat.setImage(new Image(System.getProperty("user.dir") + "\\src\\resources\\images\\game\\entities\\male-rat.png"));
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
