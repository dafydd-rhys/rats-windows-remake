package entity.weapon;

import static main.external.Audio.playGameEffect;
import entity.Entity;
import entity.Item;
import entity.rat.Rat;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import main.Resources;
import main.level.Level;
import tile.Tile;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Gas
 *
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Gas extends Item {

    private int count = 0;
    private final ArrayList<Tile> drawableTiles = new ArrayList<>();

    /**
     * Instantiates a new Gas.
     */
    public Gas() {
        setEntityType(EntityType.ITEM);
        setEntityName("Gas");
        setImage(Resources.getEntityImage("gas"));
        setHp(12);
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
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(Resources.getGameAudio("gas"));
    }

    /**
     * @param level the level
     * @param gc    the gc
     */
    public void activate(Level level, GraphicsContext gc) {
        setHp(getHp() - 1);
        if (getHp() % 3 == 0 && getHp() <= 12) {
            //runs at 0, 1, 2 - range = 3
            count++;
        }

        try {
            playSound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        if (getHp() > 0) {
            if (count >= 0) {
                Tile startingTile = level.getTiles()[getCurrentPosY()][getCurrentPosX()];
                checkTile(startingTile, level);

                ArrayList<Tile> fTiles = checkAdjacent(level, count);
                ArrayList<Tile> sTiles = checkAdjacent(level, -(count));
                for (Tile fTile : fTiles) {
                    if (!drawableTiles.contains(fTile)) {
                        drawableTiles.add(fTile);
                    }
                }
                for (Tile sTile : sTiles) {
                    if (!drawableTiles.contains(sTile)) {
                        drawableTiles.add(sTile);
                    }
                }

                for (Tile tile : drawableTiles) {
                    checkTile(tile, level);
                }
                drawableTiles.remove(startingTile);
            }
        } else {
            level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(this);
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

        if (getCurrentPosX() + i < level.getCols() && getCurrentPosX() + i >= 0) {
            if (tiles[getCurrentPosY()][getCurrentPosX() + i].isWalkable()) {
                seenTiles.add(tiles[getCurrentPosY()][getCurrentPosX() + i]);

                for (int j = -count; j < count; j++) {
                    if (getCurrentPosY() + j < level.getRows() && getCurrentPosY() + j >= 0) {
                        if (tiles[getCurrentPosY() + j][getCurrentPosX() + i].isWalkable()) {
                            seenTiles.add(tiles[getCurrentPosY() + j][getCurrentPosX() + i]);
                        }
                    }
                }
            }
        }

        if (getCurrentPosY() + i < level.getRows() && getCurrentPosY() + i >= 0) {
            if (tiles[getCurrentPosY() + i][getCurrentPosX()].isWalkable()) {
                seenTiles.add(tiles[getCurrentPosY() + i][getCurrentPosX()]);

                for (int j = -count; j < count; j++) {
                    if (getCurrentPosX() + j < level.getCols() && getCurrentPosX() + j >= 0) {
                        if (tiles[getCurrentPosY() + i][getCurrentPosX() + j].isWalkable()) {
                            seenTiles.add(tiles[getCurrentPosY() + i][getCurrentPosX() + j]);
                        }
                    }
                }
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
        return drawableTiles;
    }

}
