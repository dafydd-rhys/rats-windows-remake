package entity.weapon;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.canvas.GraphicsContext;
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
 * The type No entry sign.
 */
public class NoEntrySign extends Item {

    /**
     * sets item attributes
     */
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

    /**
     * instantiates item
     *
     * @return new no entry sign item
     */
    @Override
    public Item createNewInstance() {
        return new NoEntrySign();
    }

    /**
     * plays sound effect
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(System.getProperty("user.dir") + "/src/resources/audio/game/oof.wav");
    }

    /**
     * prevents rats from going through its tile until broken
     *
     * @param level gets tiles
     * @param gc unused attribute
     */
    public void activate(Level level, GraphicsContext gc) {
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

}
