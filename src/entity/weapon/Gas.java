package entity.weapon;

/**
 * Gas
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Gas  extends Item{

    public Gas(){
        this.entityName = "Gas";
        this.image = null;
        this.hp = 6;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
    }

    public void activate() {
        expand();
        // remove
    }

    public void expand() {
        do {
            if (this.hp > 2) {
                // expanding
            } else {
                //shrinking
            }
            //this.hp -= 1;
        } while (this.hp > 0);
    }

}
