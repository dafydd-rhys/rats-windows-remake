package entity.weapon;

import static main.external.Audio.playGameEffect;
import entity.Item;
import entity.rat.Rat;
import javafx.scene.canvas.GraphicsContext;
import entity.Entity;
import main.Resources;
import main.level.Level;
import tile.Tile;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Male Sex Change weapon class.
 *
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class MaleSexChange extends Item {
    /** Male Sex Change's Y Offset. */
    private static final int MSC_OFFSET_Y = 4;
    /**
     * Constructor.
     */
    public MaleSexChange() {
        setEntityType(EntityType.ITEM);
        setEntityName("MaleSexChange");
        setImage(Resources.getEntityImage("male-change"));
        setHp(1);
        setDamage(0);
        setRange(1);
        setType(TYPE.MALE_CHANGE);
        setOffsetY(MSC_OFFSET_Y);
    }

    /**
     * Instantiates item.
     *
     * @return new male sex change item
     */
    @Override
    public Item createNewInstance() {
        return new MaleSexChange();
    }

    /**
     * Plays sound effect.
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(Resources.getGameAudio("sex_change"));
    }

    /**
     * Changes affected rats' gender to male.
     *
     * @param level gets tiles
     * @param gc    unused attribute
     */
    public void activate(final Level level, final GraphicsContext gc) {
        Tile[][] tile = level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[getCurrentPosY()][getCurrentPosX()].getEntitiesOnTile();

        if (!entitiesOnTile.isEmpty()) {
            for (int i = 0; i < entitiesOnTile.size(); i++) {
                if (entitiesOnTile.get(i).getEntityType() == EntityType.RAT) {
                    Rat target = (Rat) entitiesOnTile.get(i);

                    if (target.getGender() == Rat.Gender.FEMALE) {
                        target.setGender(Rat.Gender.MALE);
                        target.getImages();
                    }

                    // TODO audio here
                    try {
                        playSound();
                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                        e.printStackTrace();
                    }

                    setHp(getHp() - 1);
                    level.getItems().remove(this);
                    entitiesOnTile.remove(this);
                    return;
                }
            }
        }
    }
}
