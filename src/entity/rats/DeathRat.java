package entity.rats;

import entity.rats.Rat;
import entity.weapon.Item;

/**
 * DeathRat
 *
 * @author Harry Boyce, Bryan Kok
 */

public class DeathRat extends Rat {

    public DeathRat(boolean isFemale){
        super(isFemale);
        this.entityName = "DeathRat";
        this.image = null;
        this.hp = 5;
        this.damage = 1;
        this.range = 1;
        //to do: add more rat details later. leave for last
    }

    public void activate(Rat targetRat) {
        inflictDamage(this.damage, targetRat);
        this.hp -= 1;
    }
}
