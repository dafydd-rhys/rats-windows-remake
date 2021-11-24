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
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */

public class MaleSexChange extends Item {

    public MaleSexChange() {
        setEntityType(EntityType.ITEM);
        setEntityName("MaleSexChange");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/male-change.png"));
        setHp(1);
        setDamage(0);
        setRange(1);
        setFriendlyFire(false);
        setCanBeAttacked(false);
        setType(TYPE.MALE_CHANGE);
        setOffsetY(4);
    }

    public void activate() {

        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (Entity entity : entitiesOnTile) {
                if (entity.getEntityType() == EntityType.RAT) {
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
