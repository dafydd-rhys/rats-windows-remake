package entity.weapon;

import entity.Item;
import javafx.scene.image.Image;

/**
 * Gas
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Gas  extends Item {

    public Gas(){
        this.entityName = "Gas";
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/gas-grenade.png");
        this.hp = 6;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.type = TYPE.GAS;
        this.yOffset = 5;
    }

    public void activate() {
        expand();
        // remove
    }

    public void expand() {
        do {
            if (this.hp > 2) {
                // expanding
            } else {
                //shrinking
            }
            //this.hp -= 1;
        } while (this.hp > 0);
    }

}
