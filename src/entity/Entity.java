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
 * @author Dafydd-Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Stefan-Cristian Daitoiu
 * @author Harry Boyce
 */
public abstract class Entity {

    protected EntityType entityType;
    protected String entityName;
    protected Image image;
    protected int hp;
    protected int damage;
    protected int currentPosX;
    protected int currentPosY;

    protected void inflictDamage(Level level, int damageDealt, Entity damageTarget){
        damageTarget.setHp(damageTarget.getHp() - damageDealt);

        if (damageTarget.getEntityType() == EntityType.RAT) {
            if (damageTarget.getHp() <= 0) {
                Rat rat = (Rat) damageTarget;
                rat.kill();
                level.setScore(Level.getScore() + 3);
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

    /**
     * Abstract method to be implemented into subclass that plays audio file when called
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     * @throws IOException
     */

    public abstract void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException;


}
