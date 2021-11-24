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
    protected int getHp() {
        return this.hp;
    }

    @Override
    protected void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    protected int getDamage() {
        return this.damage;
    }

    @Override
    protected void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    protected int getRange() {
        return this.range;
    }

    @Override
    protected void setRange(int range) {
        this.range = range;
    }

    @Override
    public int getCurrentPosX() {
        return this.currentPosX;
    }

    @Override
    public void setCurrentPosX(int currentPosX) {
        this.currentPosX = currentPosX;
    }

    @Override
    public int getCurrentPosY() {
        return this.currentPosY;
    }

    @Override
    public void setCurrentPosY(int currentPosY) {
        this.currentPosY = currentPosY;
    }

    @Override
    protected boolean isActive() {
        return isActive;
    }

    @Override
    protected void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public String getEntityName() {
        return this.entityName;
    }

    @Override
    protected void setEntityName(String entityName) {
        this.entityName = entityName;
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
