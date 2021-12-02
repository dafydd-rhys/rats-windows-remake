package entity.weapon;

import entity.Entity;
import entity.Item;
import entity.rat.Rat;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import static main.external.Audio.playGameEffect;

/**
 * Sterilisation
 *
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Sterilisation extends Item {

    /**
     * sets item attributes
     */
    public Sterilisation() {
        setEntityType(EntityType.ITEM);
        setEntityName("Sterilisation");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/sterilisation.png"));
        setHp(10);
        setDamage(0);
        setRange(2);
        setFriendlyFire(false);
        setCanBeAttacked(false);
        setType(TYPE.STERILISATION);
        setOffsetY(7);
    }

    /**
     * instantiates item
     *
     * @return new sterilisation item
     */
    @Override
    public Item createNewInstance() {
        return new Sterilisation();
    }


    /**
     * plays sound effect
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(System.getProperty("user.dir") + "/src/resources/audio/game/sterilisation.wav");
    }

    /**
     * permanently prevents all rats in effective area from mating. lasts temporarily
     *
     * @param level gets tiles
     * @param gc unused attribute
     */
    public void activate(Level level, GraphicsContext gc) {
        Tile[][] tiles = level.getTiles();
        try {
            playSound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        for (int i = -getRange(); i < getRange() + 1; i++) {
            for (int j = -getRange(); j < getRange() + 1; j++) {
                if (getCurrentPosY() + j >= 0 && getCurrentPosY() + j < level.getRows()
                && getCurrentPosX() + i >= 0 && getCurrentPosX() + i < level.getCols()) {
                    ArrayList<Entity> entities = tiles[getCurrentPosY() + j][getCurrentPosX() + i].getEntitiesOnTile();
                    if (!entities.isEmpty()) {
                        for (int k = 0; k < entities.size(); k++) {
                            if (entities.get(k).getEntityType() == EntityType.RAT) {
                                Rat target = (Rat) entities.get(k);
                                target.setSterilised(true);
                                target.getImages();
                            }
                        }
                    }
                }
            }
        }
        setHp(getHp() - 1);
        if (getHp() <= 0) {
            level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(this);
            level.getItems().remove(this);
        }
    }
}

