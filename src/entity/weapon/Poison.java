package entity.weapon;

import static main.external.Audio.playGameEffect;
import entity.Item;
import entity.rat.Rat;
import javafx.scene.canvas.GraphicsContext;
import entity.Entity;
import main.Resources;
import main.level.Level;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Poison weapon class.
 *
 * Sprite created by Stefan.
 *
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Poison extends Item {
    /** Poison's Damage. */
    private static final int POISON_DAMAGE = 5;
    /** Poison's Y Offset. */
    private static final int POISON_OFFSET_Y = 6;
    /**
     * Constructor.
     */
    public Poison() {
        setEntityType(EntityType.ITEM);
        setEntityName("Poison");
        setImage(Resources.getEntityImage("poison"));
        setHp(1);
        setDamage(POISON_DAMAGE);
        setRange(1);
        setType(TYPE.POISON);
        setOffsetY(POISON_OFFSET_Y);
    }

    /**
     * Instantiates item.
     *
     * @return new poison item
     */
    @Override
    public Item createNewInstance() {
        return new Poison();
    }

    /**
     * Plays sound effect.
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(Resources.getGameAudio("poison"));
    }

    /**
     * Inflicts damage to rat on tile.
     *
     * @param level gets tiles
     * @param gc    unused attribute
     */
    public void activate(final Level level, final GraphicsContext gc) {
        ArrayList<Entity> entitiesOnTile = level.getTiles()[getCurrentPosY()][getCurrentPosX()].getEntitiesOnTile();

        if (!entitiesOnTile.isEmpty()) {
            for (int k = 0; k < entitiesOnTile.size(); k++) {
                if (entitiesOnTile.get(k).getEntityType() == EntityType.RAT) {
                    Rat targetRat = (Rat) entitiesOnTile.get(k);
                    inflictDamage(level, getDamage(), targetRat);
                    setHp(getHp() - 1);

                    try {
                        playSound();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                    }

                    if (getHp() <= 0) {
                        level.getItems().remove(this);
                        entitiesOnTile.remove(this);
                    }
                    return;
                }
            }
        }
    }
}
