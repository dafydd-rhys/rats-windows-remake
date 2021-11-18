package entity.weapon;

import entity.Item;

/**
 * Gas
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Gas  extends Item{

    public Gas(){
        this.entityName = "Gas";
        this.image = null;
        this.hp = 3;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
    }

    public void activate() {

    }

}
