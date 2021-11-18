package entity.weapon;

import entity.Item;

public class NoEntrySign extends Item{

    public NoEntrySign(){
        this.entityName = "No Entry Sign";
        this.image = null;
        this.hp = 5;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
    }

}
