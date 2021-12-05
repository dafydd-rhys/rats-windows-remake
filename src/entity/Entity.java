package entity;

import entity.rat.Rat;
import javafx.scene.image.Image;
import main.level.Level;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Entity.
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Stefan -Cristian Daitoiu
 * @author Harry Boyce
 * @author Bryan Kok
 */
public abstract class Entity {

    /**
     * The Entity type.
     */
    protected EntityType entityType;
    /**
     * The Entity name.
     */
    protected String entityName;
    /**
     * The Image.
     */
    protected Image image;
    /**
     * The Hp.
     */
    protected int hp;
    /**
     * The Damage.
     */
    protected int damage;
    /**
     * The Current pos x.
     */
    protected int currentPosX;
    /**
     * The Current pos y.
     */
    protected int currentPosY;

    /**
     * Inflict damage.
     *
     * @param level        the level
     * @param damageDealt  the damage dealt
     * @param damageTarget the damage target
     */
    protected void inflictDamage(final Level level, final int damageDealt, final Entity damageTarget) {
        damageTarget.setHp(damageTarget.getHp() - damageDealt);

        if (damageTarget.getEntityType() == EntityType.RAT) {
            if (damageTarget.getHp() <= 0) {
                Rat rat = (Rat) damageTarget;
                rat.kill();
            }
        }
        if (damageTarget.getEntityType() == EntityType.ITEM) {
            if (damageTarget.getHp() <= 0) {
                Item item = (Item) damageTarget;
                level.getItems().remove(item);
                level.getTiles()[item.getCurrentPosY()][item.getCurrentPosX()].removeEntityFromTile(item);
            }
        }
    }

    /**
     * Gets entity type.
     *
     * @return the entity type
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * The enum Entity type.
     */
    public enum EntityType {
        /**
         * Item entity type.
         */
        ITEM(),
        /**
         * Rat entity type.
         */
        RAT()
    }

    /**
     * Sets entity type.
     *
     * @param newEntityType the entity type
     */
    public void setEntityType(final EntityType newEntityType) {
        entityType = newEntityType;
    }

    /**
     * Gets entity name.
     *
     * @return the entity name
     */
    protected abstract String getEntityName();

    /**
     * Sets entity name.
     *
     * @param newEntityName the entity name
     */
    protected abstract void setEntityName(String newEntityName);

    /**
     * Gets image.
     *
     * @return the image
     */
    protected abstract Image getImage();

    /**
     * Sets image.
     *
     * @param newImage the image
     */
    protected abstract void setImage(Image newImage);

    /**
     * Gets hp.
     *
     * @return the hp
     */
    protected abstract int getHp();

    /**
     * Sets hp.
     *
     * @param newHp the set hp
     */
    protected abstract void setHp(int newHp);

    /**
     * Gets current pos x.
     *
     * @return the current pos x
     */
    protected abstract int getCurrentPosX();

    /**
     * Sets current pos x.
     *
     * @param newPosX the changed pos x
     */
    protected abstract void setCurrentPosX(int newPosX);

    /**
     * Gets current pos y.
     *
     * @return the current pos y
     */
    protected abstract int getCurrentPosY();

    /**
     * Sets current pos y.
     *
     * @param newPosY the changed pos y
     */
    protected abstract void setCurrentPosY(int newPosY);

    /**
     * Sets damage.
     *
     * @param dealtDamage the damage
     */
    protected void setDamage(final int dealtDamage) {
        this.damage = dealtDamage;
    }

    /**
     * Gets damage.
     *
     * @return the damage
     */
    protected int getDamage() {
        return damage;
    }

    /**
     * Abstract method to be implemented into subclass that plays audio file when called.
     *
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public abstract void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException;


}
