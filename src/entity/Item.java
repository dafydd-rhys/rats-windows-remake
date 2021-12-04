package entity;

import java.io.IOException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.level.Level;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * An abstract class representing Items (Weapons).
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Gareth Wade (1901805)
 * @author Maurice Petersen (2013396)
 */
public abstract class Item extends Entity {

    /**
     * The Image.
     */
    private Image image;
    /**
     * The Type.
     */
    private TYPE type;
    /**
     * The Y Offset.
     */
    private int yOffset;
    /**
     * The Range.
     */
    private int range;

    /**
     * The enum Type.
     */
    public enum TYPE {
        /**
         * Bomb type.
         */
        BOMB(),
        /**
         * Gas type.
         */
        GAS(),
        /**
         * Death rat type.
         */
        DEATH_RAT(),
        /**
         * Female change type.
         */
        FEMALE_CHANGE(),
        /**
         * Male change type.
         */
        MALE_CHANGE(),
        /**
         * No entry type.
         */
        NO_ENTRY(),
        /**
         * Poison type.
         */
        POISON(),
        /**
         * Sterilisation type.
         */
        STERILISATION()
    }

    /**
     * Activate.
     *
     * @param level the level
     * @param gc    the gc
     */
    public abstract void activate(Level level, GraphicsContext gc);

    /**
     * Create new instance item.
     *
     * @return the item
     */
    public abstract Item createNewInstance();

    /**
     * Plays sound.
     */
    public abstract void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException;

    /**
     * Set image.
     *
     * @param paramImage the image
     */
    @Override
    public void setImage(final Image paramImage) {
        this.image = paramImage;
    }

    /**
     * Get HP.
     *
     * @return the HP
     */
    @Override
    public int getHp() {
        return this.hp;
    }

    /**
     * Set HP.
     *
     * @param hp the hp
     */
    @Override
    public void setHp(final int hp) {
        this.hp = hp;
    }

    /**
     * Get current X position.
     *
     * @return the current X position
     */
    @Override
    public int getCurrentPosX() {
        return this.currentPosX;
    }

    /**
     * Set the current X position.
     *
     * @param currentPosX the current x position
     */
    @Override
    public void setCurrentPosX(final int currentPosX) {
        this.currentPosX = currentPosX;
    }

    /**
     * Get the current Y position.
     *
     * @return the current Y position
     */
    @Override
    public int getCurrentPosY() {
        return this.currentPosY;
    }

    /**
     * Set the current Y position.
     *
     * @param currentPosY the current Y position
     */
    @Override
    public void setCurrentPosY(final int currentPosY) {
        this.currentPosY = currentPosY;
    }

    /**
     * Get the entity name.
     *
     * @return the entity name
     */
    @Override
    public String getEntityName() {
        return this.entityName;
    }

    /**
     * Set the entity name.
     *
     * @param entityName the entity name
     */
    @Override
    protected void setEntityName(final String entityName) {
        this.entityName = entityName;
    }

    /**
     * Get the image.
     *
     * @return the image
     */
    @Override
    public Image getImage() {
        return image;
    }

    /**
     * Sets offset y.
     *
     * @param paramYOffset the y offset
     */
    public void setOffsetY(final int paramYOffset) {
        this.yOffset = paramYOffset;
    }

    /**
     * Gets y offset.
     *
     * @return the y offset
     */
    public int getYOffset() {
        return yOffset;
    }

    /**
     * Sets type.
     *
     * @param paramType the type
     */
    public void setType(final TYPE paramType) {
        this.type = paramType;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public TYPE getType() {
        return type;
    }

    /**
     * Sets range.
     *
     * @param paramRange the range
     */
    public void setRange(final int paramRange) {
        this.range = paramRange;
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * Creates a string of the current position.
     *
     * @return the current position as a string
     */
    @Override
    public String toString() {
        String result = "";
        result += getEntityName().charAt(0);

        if (getCurrentPosX() != 0 && getCurrentPosY() != 0) {
            result += getHp();

            result += ":";
            result += String.format("%02d", getCurrentPosX());
            result += ":";
            result += String.format("%02d", getCurrentPosY());
        }

        return result;
    }

}
