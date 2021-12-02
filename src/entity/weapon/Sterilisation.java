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
     * Instantiates a new Sterilisation.
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

    @Override
    public Item createNewInstance() {
        return new Sterilisation();
    }

    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(System.getProperty("user.dir") + "/src/resources/audio/game/sterilisation.wav");
    }

    /**
     *
     *
     * @param level the level
     * @param gc    the gc
     */
    public void activate(Level level, GraphicsContext gc) {
        setHp(getHp() - 1);
        // TODO audio here
        try {
            playSound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        if (getHp() > 0) {
            for (int i = 0; i < getRange() + 1; i++) {
                checkAdjacent(level, i);
                checkAdjacent(level, -(i));
            }
        } else {
            level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(this);
            level.getItems().remove(this);
        }
    }

    /**
     *
     *
     * @param level
     * @param i
     */
    private void checkAdjacent(Level level, int i) {
        Tile[][] tiles = level.getTiles();

        if (getCurrentPosX() + i < level.getCols() && getCurrentPosX() + i >= 0) {
            if (tiles[getCurrentPosY()][getCurrentPosX() + i].isWalkable()) {
                ArrayList<Entity> entities = new ArrayList<>(tiles[getCurrentPosY()][getCurrentPosX() + i].getEntitiesOnTile());
                for (Entity entity : entities) {
                    if (entity.getEntityType() == EntityType.RAT) {
                        Rat target = (Rat) entity;

                        target.setSterilised(true);
                        target.getImages();
                    }
                }
            }
        }

        if (getCurrentPosY() + i < level.getRows() && getCurrentPosY() + i >= 0) {
            if (tiles[getCurrentPosY() + i][getCurrentPosX()].isWalkable()) {
                ArrayList<Entity> entities = new ArrayList<>(tiles[getCurrentPosY() + i][getCurrentPosX()].getEntitiesOnTile());

                for (Entity entity : entities) {
                    if (entity.getEntityType() == EntityType.RAT) {
                        Rat target = (Rat) entity;

                        target.setSterilised(true);
                        target.getImages();
                    }
                }
            }
        }
    }

}
