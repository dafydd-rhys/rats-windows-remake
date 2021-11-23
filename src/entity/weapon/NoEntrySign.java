package entity.weapon;

import javafx.scene.image.Image;

/**
 * NoEntrySign
 *
 * @author Harry Boyce, Bryan Kok
 */

public class NoEntrySign extends Item{

    public NoEntrySign(int x, int y) {
        this.entityName = "No Entry Sign";
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign.png");
        this.hp = 5;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.currentPosX = x;
        this.currentPosY = y;
    }

    public void activate() {
        // prevent rat from passing
            // take damage
            // new image after damage
    }

}
