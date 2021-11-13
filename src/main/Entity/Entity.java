package Entities;

import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Entity {

    protected String entityName;
    protected Image image;
    protected int hp;
    protected int damage;
    protected int range;

    protected int currentPosX;
    protected int currentPosY;
    protected boolean isActive;

    protected void inflictDamage(Entity targetedEntity){
        System.out.println("This is test. Damage inflicted!");
    };

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCurrentPosX() {
        return currentPosX;
    }

    public void setCurrentPosX(int currentPosX) {
        this.currentPosX = currentPosX;
    }

    public int getCurrentPosY() {
        return currentPosY;
    }

    public void setCurrentPosY(int currentPosY) {
        this.currentPosY = currentPosY;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
