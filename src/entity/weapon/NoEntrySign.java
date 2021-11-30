package entity.weapon;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.image.Image;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

import static main.external.Audio.playGameEffect;

/**
 * NoEntrySign
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */

public class NoEntrySign extends Item {

    public NoEntrySign() {
        setEntityType(EntityType.ITEM);
        setEntityName("NoEntry");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-5.png"));
        setHp(10);
        setDamage(0);
        setRange(1);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.NO_ENTRY);
        setOffsetY(5);
    }

    @Override
    public Item createNewInstance() {
        return new NoEntrySign();
    }

    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(System.getProperty("user.dir") + "/src/resources/audio/game/oof.wav");
    }

    public void activate(Level level) {
        checkNext(level,1, Rat.Direction.LEFT, Rat.Direction.UP);
        checkNext(level, -1, Rat.Direction.RIGHT, Rat.Direction.DOWN);

        switch (getHp()) {
            case 8 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-4.png"));
            case 6 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-3.png"));
            case 4 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-2.png"));
            case 2 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-1.png"));
            case 0 -> {
                level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(this);
                level.getItems().remove(this);
            }
        }
    }

    private void checkNext(Level level, int next, Rat.Direction horizontal, Rat.Direction vertical) {
        Tile[][] tiles = level.getTiles();

        if (tiles[getCurrentPosY()][getCurrentPosX() + next].isWalkable()) {
            if (!tiles[getCurrentPosY()][getCurrentPosX() + next].getEntitiesOnTile().isEmpty()) {
                ArrayList<Entity> entities = tiles[getCurrentPosY()][getCurrentPosX() + next].getEntitiesOnTile();

                for (Entity entity : entities) {
                    if (entity.getEntityType() == EntityType.RAT) {
                        Rat target = (Rat) entity;
                        if (target.getDirection() == horizontal) {
                            if (horizontal == Rat.Direction.LEFT) {
                                target.setDirection(Rat.Direction.RIGHT);
                            } else {
                                target.setDirection(Rat.Direction.LEFT);
                            }
                            // TODO audio here
                            try {
                                playSound();
                            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                                e.printStackTrace();
                            }
                            setHp(getHp() - 1);
                        }
                    }
                }
            }
        }

        if (tiles[getCurrentPosY() + next][getCurrentPosX()].isWalkable()) {
            if (!tiles[getCurrentPosY() + next][getCurrentPosX()].getEntitiesOnTile().isEmpty()) {
                ArrayList<Entity> entities = tiles[getCurrentPosY() + next][getCurrentPosX()].getEntitiesOnTile();

                for (Entity entity : entities) {
                    if (entity.getEntityType() == EntityType.RAT) {
                        Rat target = (Rat) entity;

                        if (target.getDirection() == vertical) {
                            if (vertical == Rat.Direction.UP) {
                                target.setDirection(Rat.Direction.DOWN);
                            } else {
                                target.setDirection(Rat.Direction.UP);
                            }
                            setHp(getHp() - 1);
                        }
                    }
                }
            }
        }
    }

}
