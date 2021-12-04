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
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Poison extends Item {

    /**
     * Constructor
     */
    public Poison() {
        setEntityType(EntityType.ITEM);
        setEntityName("Poison");
        setImage(Resources.getEntityImage("poison"));
        setHp(1);
        setDamage(5);
        setRange(1);
        setType(TYPE.POISON);
        setOffsetY(6);
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
    public void activate(Level level, GraphicsContext gc) {
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
