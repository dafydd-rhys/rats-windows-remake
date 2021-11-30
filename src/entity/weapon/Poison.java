package entity.weapon;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import entity.Entity;
import main.level.Level;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

import static main.external.Audio.playGameEffect;

/**
 * Poison
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Poison extends Item {

    public Poison() {
        setEntityType(EntityType.ITEM);
        setEntityName("Poison");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/poison.png"));
        setHp(1);
        setDamage(5);
        setRange(1);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.POISON);
        setOffsetY(6);
    }

    @Override
    public Item createNewInstance() {
        return new Poison();
    }

    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(System.getProperty("user.dir") + "/src/resources/audio/game/poison.wav");
    }

    public void activate(Level level, GraphicsContext gc) {
        ArrayList<Entity> entitiesOnTile = level.getTiles()[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (!entitiesOnTile.isEmpty()) {
            for (int k = 0; k < entitiesOnTile.size(); k++) {
                if (entitiesOnTile.get(k).getEntityType() == EntityType.RAT){
                    Rat targetRat = (Rat) entitiesOnTile.get(k);
                    inflictDamage(level, getDamage(), targetRat);
                    setHp(getHp() - 1);
                    // TODO audio here
                    try {
                        playSound();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                    }
                    if (getHp() <= 0) {
                        level.getItems().remove(this);
                        entitiesOnTile.remove(this);
                    }
                    break;
                }
            }
        }
    }
}
