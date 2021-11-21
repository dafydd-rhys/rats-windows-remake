package entity.weapon;

/**
 * Sterilisation
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Sterilisation extends Item{

    public Sterilisation(){
        this.entityName = "Sterilisation";
        this.image = null;
        this.hp = 4;
        this.damage = 0;
        this.range = 2;
        this.friendlyFire = false;
        this.isAttackable = false;
    }

    public void activate() {
        // makeSterile
        // tick
        // sterile baby rat -> sterile adult rat
        // sterile adult rat -> cannot mate
    }

}
