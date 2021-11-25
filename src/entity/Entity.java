package entity;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.level.Level;

/**
 * Entity
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Dawid Wisniewski
 */
public abstract class Entity {

    protected EntityType entityType;
    protected String entityName;
    protected Image image;
    protected int hp;
    protected int damage;
    protected int currentPosX;
    protected int currentPosY;

    protected void inflictDamage(int damageDealt, Entity damageTarget){
        damageTarget.setHp(damageTarget.getHp() - damageDealt);
        System.out.println(damageTarget.getEntityName() + " is dealt " + damageDealt + " damage!");
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public enum EntityType {
        ITEM(),
        RAT()
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    protected abstract String getEntityName();

    protected abstract void setEntityName(String entityName);

    protected abstract Image getImage();

    protected abstract void setImage(Image image);

    protected abstract int getHp();

    protected abstract void setHp(int hp);

    protected abstract int getCurrentPosX();

    protected abstract void setCurrentPosX(int currentPosX);

    protected abstract int getCurrentPosY();

    protected abstract void setCurrentPosY(int currentPosY);

    protected void setDamage(int damage) {
        this.damage = damage;
    }

    protected int getDamage() {
        return damage;
    }

}
