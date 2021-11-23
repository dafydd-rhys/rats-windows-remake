package entity.weapon;

import entity.rats.Rat;
import javafx.scene.image.Image;

/**
 * FemaleSexChange
 *
 * @author Harry Boyce, Bryan Kok
 */

public class FemaleSexChange extends Item {

    public FemaleSexChange(int x, int y){
        this.entityName = "FemaleSexChange";
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/female-change.png");
        this.hp = 1;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.currentPosX = x;
        this.currentPosY = y;
    }

    public void activate(Rat targetRat) {
        if (!targetRat.isFemale()) {
            targetRat.setFemale(true);
            //add sprite change
        }
        this.hp -= 1;
    }

}
