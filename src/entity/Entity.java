package entity;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Entity
 *
 * @author Dawid Wisniewski, Bryan Kok
 * @version 1.1
 */
public abstract class Entity {

    protected String entityName;
    protected Image image;
    protected int hp;
    protected int damage;
    protected int range;

    protected int currentPosX;
    protected int currentPosY;
    protected boolean isActive;

    protected void inflictDamage(int damageDealt, Entity damageTarget){
        damageTarget.setHp(damageTarget.getHp() - damageDealt);
        System.out.println(damageTarget.getEntityName() + " is dealt " + damageDealt + " damage!");
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Image getImage() {
        return image;
    }

    public abstract void setImage(Image image);

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

    public static Image rotate(Image img, int angle) {
        ImageView iv = new ImageView(img);
        iv.setRotate(angle);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        return iv.snapshot(params, null);
    }

}
