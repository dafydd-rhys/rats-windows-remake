package entity.weapon;

import entity.Item;

public class FemaleSexChange extends Item {

    public FemaleSexChange(){
        this.entityName = "FemaleSexChange";
        this.image = null;
        this.hp = 1;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
    }

    public void activate(entity.Rat targetRat){
        if (targetRat.getGender() == false) {
            targetRat.setGender(true);
        }
        setHp(getHp() - 1);
    }

}
