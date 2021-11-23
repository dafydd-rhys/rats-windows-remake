package entity.weapon;

import entity.rats.Rat;
import javafx.scene.image.Image;

/**
 * Poison
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Poison extends Item {

    public Poison(int x, int y) {
        this.entityName = "Poison";
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/poison.png");
        this.hp = 1;
        this.damage = 5;
        this.range = 1;
        this.friendlyFire = false;
        this.isAttackable = false;
        this.currentPosX = x;
        this.currentPosY = y;
    }

    public void activate(Rat targetRat) {
        inflictDamage(this.damage, targetRat);
        this.hp -= 1;
        // remove
    }
}
