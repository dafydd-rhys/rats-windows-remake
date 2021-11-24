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

    public abstract String getEntityName();

    protected abstract void setEntityName(String entityName);

    protected abstract Image getImage();

    protected abstract void setImage(Image image);

    protected abstract int getHp();

    protected abstract void setHp(int hp);

    protected abstract int getDamage();

    protected abstract void setDamage(int damage);

    protected abstract int getRange();

    protected abstract void setRange(int range);

    protected abstract int getCurrentPosX();

    protected abstract void setCurrentPosX(int currentPosX);

    protected abstract int getCurrentPosY();

    protected abstract void setCurrentPosY(int currentPosY);

    protected abstract boolean isActive();

    protected abstract void setActive(boolean active);

    public static Image rotate(Image img, int angle) {
        ImageView iv = new ImageView(img);
        iv.setRotate(angle);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        return iv.snapshot(params, null);
    }

}
