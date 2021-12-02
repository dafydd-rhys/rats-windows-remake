package entity;

import entity.rat.Rat;
import tile.Tile;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.level.Level;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Entity
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Stefan -Cristian Daitoiu
 * @author Harry Boyce
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
    protected void inflictDamage(Level level, int damageDealt, Entity damageTarget){
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
     * @param entityType the entity type
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
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
     * @param entityName the entity name
     */
    protected abstract void setEntityName(String entityName);

    /**
     * Gets image.
     *
     * @return the image
     */
    protected abstract Image getImage();

    /**
     * Sets image.
     *
     * @param image the image
     */
    protected abstract void setImage(Image image);

    /**
     * Gets hp.
     *
     * @return the hp
     */
    protected abstract int getHp();

    /**
     * Sets hp.
     *
     * @param hp the hp
     */
    protected abstract void setHp(int hp);

    /**
     * Gets current pos x.
     *
     * @return the current pos x
     */
    protected abstract int getCurrentPosX();

    /**
     * Sets current pos x.
     *
     * @param currentPosX the current pos x
     */
    protected abstract void setCurrentPosX(int currentPosX);

    /**
     * Gets current pos y.
     *
     * @return the current pos y
     */
    protected abstract int getCurrentPosY();

    /**
     * Sets current pos y.
     *
     * @param currentPosY the current pos y
     */
    protected abstract void setCurrentPosY(int currentPosY);

    /**
     * Sets damage.
     *
     * @param damage the damage
     */
    protected void setDamage(int damage) {
        this.damage = damage;
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
     * Abstract method to be implemented into subclass that plays audio file when called
     *
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     */
    public abstract void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException;


}
