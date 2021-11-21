package entity.weapon;

import entity.rats.Rat;

/**
 * Bomb
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Bomb extends Item { //used to extend Entities.Item

    public Bomb(){
        this.entityName = "Bomb";
        this.image = null;
        this.hp = 4;
        this.damage = 1;
        this.range = 2;
        this.friendlyFire = true;
        this.isAttackable = false;
    }

    public void activate(Rat targetRat) {
        countdown();
        explode();

        // kill
        // destroy
    }

    private void countdown() {

    }

    private void explode() {

    }

}
