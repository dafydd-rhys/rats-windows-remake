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
 * Gas weapon class.
 *
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Gas extends Item {
    /** Range of the Bomb at the current tick. */
    private int count = 0;
    /** List of tiles that can be drawn on. */
    private final ArrayList<Tile> drawableTiles = new ArrayList<>();
    /** Gas' HP. */
    private static final int GAS_HP = 12;
    /** Gas' Range. */
    private static final int GAS_RANGE = 3;
    /** Gas' Y Offset. */
    private static final int GAS_OFFSET_Y = 3;
    /** Gas' max HP. */
    private static final int MAX_HP = 12;
    /** Gas expands every 3 ticks. */
    private static final int EXPAND_EVERY = 3;

    /**
     * Constructor.
     */
    public Gas() {
        setEntityType(EntityType.ITEM);
        setEntityName("Gas");
        setImage(Resources.getEntityImage("gas"));
        setHp(GAS_HP);
        setDamage(2);
        setRange(GAS_RANGE);
        setType(TYPE.GAS);
        setOffsetY(GAS_OFFSET_Y);
    }

    /**
     * Instantiates item.
     *
     * @return new gas item
     */
    @Override
    public Item createNewInstance() {
        return new Gas();
    }

    /**
     * Plays sound effect.
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException,
            LineUnavailableException, IOException {
        playGameEffect(Resources.getGameAudio("gas"));
    }

    /**
     * Activates the gas with a range of three tiles,
     * searching all adjacent tiles for rat entities and inflicting 2 damage.
     *
     * @param level used to get the tiles
     * @param gc    unused attribute
     */
    public void activate(final Level level, final GraphicsContext gc) {
        setHp(getHp() - 1);
        if (getHp() % EXPAND_EVERY == 0 && getHp() <= MAX_HP) {
            //runs at 0, 1, 2 - range = 3
            count++;
        }

        try {
            playSound();
        } catch (UnsupportedAudioFileException | IOException
                | LineUnavailableException e) {
            e.printStackTrace();
        }

        if (getHp() > 0) {
            if (count >= 0) {
                Tile startingTile =
                        level.getTiles()[getCurrentPosY()][getCurrentPosX()];
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
            level.getTiles()[getCurrentPosY()][getCurrentPosX()].
                    removeEntityFromTile(this);
            level.getItems().remove(this);
        }
    }

    /**
     * Checks if tiles adjacent to the current tile are walkable.
     *
     * @param level used to get the tiles
     * @param i range of the bomb at the current tick (expanding)
     * @return list of walkable tiles
     */
    private ArrayList<Tile> checkAdjacent(final Level level, final int i) {
        ArrayList<Tile> seenTiles = new ArrayList<>();
        Tile[][] tiles = level.getTiles();

        if (getCurrentPosX() + i < level.getCols()
                && getCurrentPosX() + i >= 0) {
            if (tiles[getCurrentPosY()][getCurrentPosX() + i]
                    .isWalkable()) {
                seenTiles.add(tiles[getCurrentPosY()]
                        [getCurrentPosX() + i]);

                for (int j = -count; j < count; j++) {
                    if (getCurrentPosY() + j < level.getRows()
                            && getCurrentPosY() + j >= 0) {
                        if (tiles[getCurrentPosY() + j][getCurrentPosX() + i]
                                .isWalkable()) {
                            seenTiles.add(tiles[getCurrentPosY() + j]
                                    [getCurrentPosX() + i]);
                        }
                    }
                }
            }
        }

        if (getCurrentPosY() + i < level.getRows()
                && getCurrentPosY() + i >= 0) {
            if (tiles[getCurrentPosY() + i][getCurrentPosX()]
                    .isWalkable()) {
                seenTiles.add(tiles[getCurrentPosY() + i][getCurrentPosX()]);

                for (int j = -count; j < count; j++) {
                    if (getCurrentPosX() + j < level.getCols()
                            && getCurrentPosX() + j >= 0) {
                        if (tiles[getCurrentPosY() + i]
                                [getCurrentPosX() + j].isWalkable()) {
                            seenTiles.add(tiles[getCurrentPosY() + i]
                                    [getCurrentPosX() + j]);
                        }
                    }
                }
            }
        }
        return seenTiles;
    }

    /**
     * Checks for entities on the tile and inflicts 2 damage on any rats.
     *
     * @param tile used to get list of entities on tile
     * @param level needed to inflict damage
     */
    private void checkTile(final Tile tile, final Level level) {
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
