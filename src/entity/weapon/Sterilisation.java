package entity.weapon;

import entity.Item;

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

}
