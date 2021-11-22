package entity.weapon;

/**
 * DeathRat
 *
 * @author Harry Boyce, Bryan Kok
 */

public class DeathRat extends Item {

    public DeathRat(int x, int y){
        this.entityName = "DeathRat";
        this.image = null;
        this.hp = 5;
        this.damage = 1;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.currentPosX = x;
        this.currentPosY = y;
    }

    public void activate(entity.Rat targetRat) {
        inflictDamage(this.damage, targetRat);
        this.hp -= 1;
    }

    public void delayTwoTicks() {

    }

}
