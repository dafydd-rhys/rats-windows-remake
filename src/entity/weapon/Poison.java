package entity.weapon;

import entity.Item;

public class Poison extends Item {

    public Poison(){
        this.entityName = "Poison";
        this.image = null;
        this.hp = 1;
        this.damage = 1;
        this.range = 1;
        this.friendlyFire = false;
        this.isAttackable = false;
    }

    public void activate(entity.Rat targetRat){
        inflictDamage(this.damage, targetRat);
        setHp(getHp() - 1);
    }

}
