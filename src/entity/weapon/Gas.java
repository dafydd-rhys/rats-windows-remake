package entity.weapon;

import entity.Item;
import javafx.scene.image.Image;

/**
 * Gas
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Gas extends Item {

    public Gas() {
        setEntityType(EntityType.ITEM);
        setEntityName("Gas");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/gas-grenade.png"));
        setHp(6);
        setDamage(2);
        setRange(1);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.GAS);
        setOffsetY(3);
    }

    // TODO
    public void activate() {
        if (this.hp > 2) {
            expand();
        } else {
            shrink();
        }
    }

    public void expand() {

    }

    private void shrink() {
    }

}
