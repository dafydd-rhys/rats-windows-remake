package entity.weapon;

import entity.Item;

/**
 * MaleSexChange
 *
 * @author Harry Boyce, Bryan Kok
 */

public class MaleSexChange extends Item {

    public MaleSexChange(){
        this.entityName = "MaleSexChange";
        this.image = null;
        this.hp = 1;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = false;
        this.isAttackable = false;
    }

    public void activate(entity.Rat targetRat) {
        if (targetRat.getGender == true) {
            targetRat.setGender(false);
        }
        this.hp -= 1;
        // remove
    }
}
