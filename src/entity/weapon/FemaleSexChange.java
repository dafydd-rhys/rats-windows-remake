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
 * Female Sex Change weapon class.
 *
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class FemaleSexChange extends Item {

    /**
     * Constructor.
     */
    public FemaleSexChange() {
        setEntityType(EntityType.ITEM);
        setEntityName("FemaleSexChange");
        setImage(Resources.getEntityImage("female-change"));
        setHp(1);
        setDamage(0);
        setRange(1);
        setType(TYPE.FEMALE_CHANGE);
        setOffsetY(2);
    }

    /**
     * Instantiates item.
     *
     * @return new female sex change item
     */
    @Override
    public Item createNewInstance() {
        return new FemaleSexChange();
    }

    /**
     * Plays sound effect.
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(Resources.getGameAudio("sex_change"));
    }

    /**
     * Changes affected rats' gender to female.
     *
     * @param level gets tiles
     * @param gc    unused attribute
     */
    public void activate(Level level, GraphicsContext gc) {
        Tile[][] tile = level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[getCurrentPosY()][getCurrentPosX()].getEntitiesOnTile();

        if (!entitiesOnTile.isEmpty()) {
            for (int i = 0; i < entitiesOnTile.size(); i++) {
                if (entitiesOnTile.get(i).getEntityType() == EntityType.RAT) {
                    Rat target = (Rat) entitiesOnTile.get(i);

                    if (target.getGender() == Rat.Gender.MALE) {
                        target.setGender(Rat.Gender.FEMALE);
                        target.getImages();
                    }
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
