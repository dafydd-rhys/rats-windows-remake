package entity.weapon;

import entity.Item;
import javafx.scene.image.Image;

/**
 * DeathRat
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */

public class DeathRat extends Item { //used to extend Entities.Item

    public DeathRat() {
        setEntityType(EntityType.ITEM);
        setEntityName("DeathRat");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/death-rat.png"));
        setHp(8);
        setDamage(5);
        setRange(2);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.DEATH_RAT);
        setOffsetY(1);
    }

    // TODO

    public void countdown() {

    }

    public void activate() {

    }
}
