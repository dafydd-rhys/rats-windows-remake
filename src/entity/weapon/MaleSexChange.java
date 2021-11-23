package entity.weapon;

import entity.rats.Rat;
import javafx.scene.image.Image;

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

    public void activate(Rat targetRat) {
        if (targetRat.isFemale()) {
            targetRat.setFemale(false);
            //add sprite change
        }
        this.hp -= 1;
        // remove
    }

}
