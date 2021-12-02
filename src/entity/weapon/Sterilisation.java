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

    private final ArrayList<Tile> drawableTiles = new ArrayList<>();

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
     * @param gc    unused attribute
     */
    public void activate(Level level, GraphicsContext gc) {
        try {
            playSound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        Tile[][] tiles = level.getTiles();
        Tile startingTile = tiles[getCurrentPosY()][getCurrentPosX()];
        if (getHp() > 0) {
            for (int i = -getRange(); i < getRange() + 1; i++) {
                for (int j = -getRange(); j < getRange() + 1; j++) {
                    if (getCurrentPosY() + j >= 0 && getCurrentPosY() + j < level.getRows()
                            && getCurrentPosX() + i >= 0 && getCurrentPosX() + i < level.getCols()) {

                        Tile tile = tiles[getCurrentPosY() + j][getCurrentPosX() + i];
                        ArrayList<Entity> entities = tile.getEntitiesOnTile();
                        if (!entities.isEmpty()) {
                            for (int k = 0; k < entities.size(); k++) {
                                if (entities.get(k).getEntityType() == EntityType.RAT) {
                                    Rat target = (Rat) entities.get(k);
                                    target.setSterilised(true);
                                    target.getImages();
                                }
                            }
                        }
                        if (tile.isWalkable() && tile.isCovering() && !drawableTiles.contains(tile)) {
                            drawableTiles.add(tile);
                        }
                        drawableTiles.remove(startingTile);
                    }
                }
            }
        } else {
            startingTile.removeEntityFromTile(this);
            level.getItems().remove(this);
        }
        setHp(getHp() - 1);
    }

    public ArrayList<Tile> getDrawableTiles() {
        return drawableTiles;
    }

}

