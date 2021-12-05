package entity.weapon;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Resources;
import main.level.Level;
import tile.Tile;
import entity.Entity;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import static main.external.Audio.playGameEffect;

/**
 * Bomb weapon class.
 *
 * Sprite created by Jackey.
 *
 * @author Gareth Wade (1901805)
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 * @author Stefan -Cristian Daitoiu(2033160)
 */
public class Bomb extends Item {

    /** Bomb's HP. */
    private static final int BOMB_HP = 8;
    /** Bomb's Damage. */
    private static final int BOMB_DAMAGE = 99;
    /** Bomb's HP when countdown is at 3. */
    private static final int BOMB_COUNTDOWN_3 = 6;
    /** Bomb's HP when countdown is at 2. */
    private static final int BOMB_COUNTDOWN_2 = 4;
    /** Bomb's explosion image X position. */
    private static final int BOMB_EXPLOSION_X = 50;
    /** Bomb's explosion image X position. */
    private static final int BOMB_EXPLOSION_Y = 50;
    /**
     * Constructor.
     */
    public Bomb() {
        setEntityType(EntityType.ITEM);
        setEntityName("Bomb");
        setImage(Resources.getEntityImage("bomb-4"));
        setHp(BOMB_HP);
        setDamage(BOMB_DAMAGE);
        setRange(0);
        setType(TYPE.BOMB);
        setOffsetY(0);
    }

    /**
     * Instantiates new item.
     *
     * @return new bomb item
     */
    @Override
    public Item createNewInstance() {
        return new Bomb();
    }

    /**
     * Plays sound effect.
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(Resources.getGameAudio("bomb"));
    }

    /**
     * Causes bomb to explode after some time.
     *
     * @param level used to pass to explode method
     * @param gc    used to pass to explode method
     */
    public void activate(final Level level, final GraphicsContext gc) {
        setHp(getHp() - 1);
        switch (getHp()) {
            case BOMB_COUNTDOWN_3 -> setImage(Resources.getEntityImage("bomb-3"));
            case BOMB_COUNTDOWN_2 -> setImage(Resources.getEntityImage("bomb-2"));
            case 2 -> setImage(Resources.getEntityImage("bomb-1"));
            case 0 -> explode(level, gc);
        }
    }

    /**
     * Inflicts damage to all rats in cardinal directions from bomb.
     *
     * @param level gets tiles
     * @param gc    draws effect on affected tiles
     */
    private void explode(final Level level, final GraphicsContext gc) {
        Tile[][] tiles = level.getTiles();
        Tile startingTile = tiles[getCurrentPosY()][getCurrentPosX()];
        try {
            playSound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        Image explosion = Resources.getEntityImage("bomb-0");
        for (Rat.Direction direction : Rat.Direction.values()) {
            Tile current = tiles[getCurrentPosY()][getCurrentPosX()];

            int distance = 0;
            while (current.isWalkable()) {
                current = getDirection(direction, distance, tiles);

                ArrayList<Entity> entitiesOnTile = current.getEntitiesOnTile();
                if (!entitiesOnTile.isEmpty()) {
                    for (int i = 0; i < entitiesOnTile.size(); i++) {
                        Entity entity = entitiesOnTile.get(i);
                        inflictDamage(level, getDamage(), entity);
                    }
                }

                distance++;
                if (current.isCovering() && current.isWalkable()) {
                    gc.drawImage(explosion, current.getX() * BOMB_EXPLOSION_X, current.getY() * BOMB_EXPLOSION_Y);
                }
            }
        }
        level.getItems().remove(this);
        startingTile.removeEntityFromTile(this);
    }

    /**
     * Finds tile in bomb's area of effect one at a time.
     *
     * @param direction gets direction to next tile
     * @param distance  gets next tile in range
     * @param tiles     gets tile using parameters
     * @return next tile in bomb's area of effect
     */
    private Tile getDirection(final Rat.Direction direction, final int distance, final Tile[][] tiles) {
        return switch (direction) {
            case LEFT -> tiles[getCurrentPosY()][getCurrentPosX() - distance];
            case RIGHT -> tiles[getCurrentPosY()][getCurrentPosX() + distance];
            case UP -> tiles[getCurrentPosY() + distance][getCurrentPosX()];
            case DOWN -> tiles[getCurrentPosY() - distance][getCurrentPosX()];
        };
    }

}
