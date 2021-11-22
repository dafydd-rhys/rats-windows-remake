package entity.weapon;

import entity.rats.Rat;

/**
 * Poison
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Poison extends Item {

    public Poison(){
        this.entityName = "Poison";
        this.image = null;
        this.hp = 1;
        this.damage = 5;
        this.range = 1;
        this.friendlyFire = false;
        this.isAttackable = false;
    }

    public void activate(Rat targetRat) {
        inflictDamage(this.damage, targetRat);
        this.hp -= 1;
        // remove
    }
}
