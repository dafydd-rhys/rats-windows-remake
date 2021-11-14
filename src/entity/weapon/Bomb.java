package entity.weapon;

import entity.Weapon;

public class Bomb extends Weapon { //used to extend Entities.Item

    public Bomb(){
        this.entityName = "Bomb";
        this.image = null;
        this.hp = 4;
        this.damage = 1;
        this.range = 2;
        this.friendlyFire = true;
        this.isAttackable = false;
    }

    //some methods and mechanics of Bomb
}
