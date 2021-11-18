package entity.weapon;

import entity.Item;

/**
 * FemaleSexChange
 *
 * @author Harry Boyce, Bryan Kok
 */

public class FemaleSexChange extends Item {

    public FemaleSexChange(){
        this.entityName = "FemaleSexChange";
        this.image = null;
        this.hp = 1;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
    }

    public void activate(entity.Rat targetRat) {
        if (targetRat.getGender == false) {
            targetRat.setGender(true);
        }
        this.hp -= 1;
        // remove
    }

}
