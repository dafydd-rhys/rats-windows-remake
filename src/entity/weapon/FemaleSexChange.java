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
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */

public class FemaleSexChange extends Item {

    public FemaleSexChange() {
        setEntityType(EntityType.ITEM);
        setEntityName("FemaleSexChange");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/female-change.png"));
        setHp(1);
        setDamage(0);
        setRange(1);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.FEMALE_CHANGE);
        setOffsetY(2);
    }

    public void activate() {

        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (Entity entity : entitiesOnTile) {
                if (entity.getEntityType() == EntityType.RAT) {
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
