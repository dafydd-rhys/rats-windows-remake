package entity;

public abstract class Weapon extends Entity {

    protected boolean friendlyFire; //whether we want an item to affect our friends- deathrats
    protected boolean isAttackable; //whether we want rats to attack/decrease hp of the item. In case of e.g. Gas/Bomb we want it false

    public Weapon() {
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public boolean isAttackable() {
        return isAttackable;
    }

    public void setAttackable(boolean attackable) {
        isAttackable = attackable;
    }
}
