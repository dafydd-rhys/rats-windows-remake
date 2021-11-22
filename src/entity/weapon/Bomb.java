package entity.weapon;

import entity.rats.Rat;

/**
 * Bomb
 *
 * @author Harry Boyce
 * @author Gareth Wade (1901805)
 * @author Bryan Kok
 */

public class Bomb extends Item { //used to extend Entities.Item

    public Bomb(int x, int y){
        this.entityName = "Bomb";
        this.hp = 4;
        this.damage = 1;
        this.range = 2;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.currentPosX = x;
        this.currentPosY = y;
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
