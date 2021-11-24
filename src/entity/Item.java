package entity;

import entity.Entity;
import javafx.scene.image.Image;

/**
 * Item
 *
 * @author Dawid Wisniewski
 * @author Gareth Wade (1901805)
 */
public abstract class Item extends Entity {

    protected boolean friendlyFire;
    protected boolean isAttackable;
    protected Image image;
    protected TYPE type;
    protected int yOffset;

    public abstract void activate();

    public enum TYPE {
        BOMB(),
        GAS(),
        DEATH_RAT(),
        FEMALE_CHANGE(),
        MALE_CHANGE(),
        NO_ENTRY(),
        POISON(),
        STERILISATION()
    }

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

    public int getYOffset() {
        return yOffset;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }

}
