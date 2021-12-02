package entity.weapon;

import entity.Item;
import entity.rat.Rat;
import java.net.URL;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;
import entity.Entity;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

import static main.external.Audio.playGameEffect;

/**
 * Bomb
 *
 * @author Gareth Wade (1901805)
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 * @author Stefan -Cristian Daitoiu(2033160)
 */
public class Bomb extends Item {

    private static final String BOMB_IMAGE = System.getProperty("user.dir")
            + "\\src\\resources\\images\\game\\entities\\bomb-4.png";
    private static final String EXPLOSION_IMAGE = System.getProperty("user.dir")
            + "\\src\\resources\\images\\game\\entities\\bomb-4.png";

    /**
     * sets bomb attributes
     */
    public Bomb() {
        URL oi = this.getClass().getResource("images\\Bomb.png");
        setEntityType(EntityType.ITEM);
        setEntityName("Bomb");
        setImage(new Image(BOMB_IMAGE));
        setHp(8);
        setDamage(99);
        setRange(0);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.BOMB);
        setOffsetY(0);
    }

    /**
     * instantiates new Bomb
     */
    @Override
    public Item createNewInstance() {
        return new Bomb();
    }

    /**
     * plays sound effect
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(System.getProperty("user.dir") + "/src/resources/audio/game/bomb.wav");
    }

    /**
     * causes bomb to explode after some time
     *
     * @param level used to pass to explode method
     * @param gc    used to pass to explode method
     */
    public void activate(Level level, GraphicsContext gc) {
        setHp(getHp() - 1);
        switch (getHp()) {
            case 6 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-3.png"));
            case 4 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-2.png"));
            case 2 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-1.png"));
            case 0 -> explode(level, gc);
        }
    }

    /**
     * inflict damage to all rats in cardinal directions from bomb
     *
     * @param level gets tiles
     * @param gc    draws effect on affected tiles
     */
    private void explode(Level level, GraphicsContext gc) {
        Tile[][] tiles = level.getTiles();
        Tile startingTile = tiles[getCurrentPosY()][getCurrentPosX()];
        try {
            playSound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

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
                if (current.isWalkable() && current.isCovering()) {
                    gc.drawImage(new Image(EXPLOSION_IMAGE), current.getX() * 50, current.getY() * 50);
                }
            }
        }
        level.getItems().remove(this);
        startingTile.removeEntityFromTile(this);
    }

    /**
     * finds tile in bomb's area of effect one at a time
     *
     * @param direction gets direction to next tile
     * @param distance gets next tile in range
     * @param tiles gets tile using parameters
     * @return next tile in bomb's area of effect
     */
    private Tile getDirection(Rat.Direction direction, int distance, Tile[][] tiles) {
        return switch (direction) {
            case LEFT -> tiles[getCurrentPosY()][getCurrentPosX() - distance];
            case RIGHT -> tiles[getCurrentPosY()][getCurrentPosX() + distance];
            case UP -> tiles[getCurrentPosY() + distance][getCurrentPosX()];
            case DOWN -> tiles[getCurrentPosY() - distance][getCurrentPosX()];
        };
    }

}
