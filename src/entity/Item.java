package entity;

import entity.Entity;
import javafx.scene.image.Image;

/**
 * LevelFileGenerator
 *
 * @author Dawid Wisniewski
 * @author Gareth Wade (1901805)
 */
public abstract class Item extends Entity {

    protected boolean friendlyFire; //whether we want an item to affect our friends- deathrats
    protected boolean isAttackable;
    protected Image image;//whether we want rats to attack/decrease hp of the item. In case of e.g. Gas/Bomb we want it false

    public Item() {
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public Image getImage() {
        return image;
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
