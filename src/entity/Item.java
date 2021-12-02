package entity;

import entity.rat.Rat;
import java.util.ArrayList;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Movement;

/**
 * Item
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Gareth Wade (1901805)
 */
public abstract class Item extends Entity {

    private boolean friendlyFire;
    private boolean canBeAttacked;
    private Image image;
    private TYPE type;
    private int yOffset;
    private int range;

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

    public abstract void activate(Level level);

    public abstract Item createNewInstance();

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
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

    public boolean canBeAttacked() {
        return canBeAttacked;
    }

    public void setCanBeAttacked(boolean canBeAttacked) {
        this.canBeAttacked = canBeAttacked;
    }

    public void setOffsetY(int yOffset) {
        this.yOffset = yOffset;
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

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    @Override
    public String toString() {
        String result = "";

        result += this.getEntityName().charAt(0);

        if (this.getCurrentPosX() != 0 && this.getCurrentPosY() != 0) {
            result += this.getHp();

            result += ":";
            result += String.format("%02d", this.getCurrentPosX());
            result += ":";
            result += String.format("%02d", this.getCurrentPosY());
        }

        return result;
    }

}
