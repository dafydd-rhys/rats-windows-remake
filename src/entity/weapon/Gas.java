package entity.weapon;

import entity.Entity;
import entity.Item;
import entity.rat.Rat;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

import static main.external.Audio.playGameEffect;

/**
 * Gas
 *
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Gas extends Item {

    private int count = -1;
    private final ArrayList<Tile> tiles = new ArrayList<>();

    /**
     * Instantiates a new Gas.
     */
    public Gas() {
        setEntityType(EntityType.ITEM);
        setEntityName("Gas");
        setImage(new Image(System.getProperty("user.dir") +
                "/src/resources/images/game/entities/gas-grenade.png"));
        setHp(10);
        setDamage(2);
        setRange(3);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.GAS);
        setOffsetY(3);
    }

    @Override
    public Item createNewInstance() {
        return new Gas();
    }

    @Override
    public void playSound()
            throws UnsupportedAudioFileException, LineUnavailableException,
            IOException {
        playGameEffect(System.getProperty("user.dir") +
                "/src/resources/audio/game/harry_gas.wav");
    }

    /**
     * @param level the level
     * @param gc    the gc
     */
    public void activate(Level level, GraphicsContext gc) {
        setHp(getHp() - 1);
        if (getHp() % 2 == 0 && getHp() < 10) {
            //runs at 0, 1, 2, 3 - range = 3
            count++;
        }

        try {
            playSound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        if (getHp() > 0) {
            if (count >= 0) {
                ArrayList<Tile> fTiles = checkAdjacent(level, count);
                ArrayList<Tile> sTiles = checkAdjacent(level, -(count));

                for (Tile fTile : fTiles) {
                    if (!tiles.contains(fTile)) {
                        tiles.add(fTile);
                    }
                }
                for (Tile sTile : sTiles) {
                    if (!tiles.contains(sTile)) {
                        tiles.add(sTile);
                    }
                }

                for (Tile tile : tiles) {
                    checkTile(tile, level);
                }
            }
        } else {
            level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(
                    this);
            level.getItems().remove(this);
        }
    }

    /**
     * @param level
     * @param i
     * @return
     */
    private ArrayList<Tile> checkAdjacent(Level level, int i) {
        ArrayList<Tile> seenTiles = new ArrayList<>();
        Tile[][] tiles = level.getTiles();

        if (getCurrentPosX() + i < level.getCols() &&
                getCurrentPosX() + i >= 0) {
            if (tiles[getCurrentPosY()][getCurrentPosX() + i].isWalkable()) {
                seenTiles.add(tiles[getCurrentPosY()][getCurrentPosX() + i]);
            }
        }

        if (getCurrentPosY() + i < level.getRows() &&
                getCurrentPosY() + i >= 0) {
            if (tiles[getCurrentPosY() + i][getCurrentPosX()].isWalkable()) {
                seenTiles.add(tiles[getCurrentPosY() + i][getCurrentPosX()]);
            }
        }
        return seenTiles;
    }

    /**
     * @param tile
     * @param level
     */
    private void checkTile(Tile tile, Level level) {
        ArrayList<Entity> entities = new ArrayList<>(tile.getEntitiesOnTile());

        for (int j = 0; j < entities.size(); j++) {
            Entity entity = entities.get(j);

            if (entity.getEntityType() == EntityType.RAT) {
                Rat target = (Rat) entity;
                inflictDamage(level, getDamage(), target);
            }
        }
    }

    /**
     * Gets tiles.
     *
     * @return the tiles
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

}
