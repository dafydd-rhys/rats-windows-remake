package entity.weapon;

import static main.external.Audio.playGameEffect;
import entity.Item;
import javafx.scene.canvas.GraphicsContext;
import main.Resources;
import main.level.Level;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * No entry sign weapon class.
 *
 * Sprite created by Jackey.
 *
 * @author Harry Boyce
 * @author Bryan Kok
 * @author Dafydd -Rhys Maund
 */
public class NoEntrySign extends Item {
    /** No Entry Sign's HP. */
    private static final int NES_HP = 10;
    /** No Entry Sign's Y Offset. */
    private static final int NES_OFFSET_Y = 5;
    /** No Entry Sign's HP with 4 blocks remaining. */
    private static final int NES_4 = 8;
    /** No Entry Sign's HP with 3 blocks remaining. */
    private static final int NES_3 = 6;
    /** No Entry Sign's HP with 2 blocks remaining. */
    private static final int NES_2 = 4;
    /**
     * Constructor.
     */
    public NoEntrySign() {
        setEntityType(EntityType.ITEM);
        setEntityName("NoEntry");
        setImage(Resources.getEntityImage("no-entry-sign-5"));
        setHp(NES_HP);
        setDamage(0);
        setRange(1);
        setType(TYPE.NO_ENTRY);
        setOffsetY(NES_OFFSET_Y);
    }

    /**
     * Instantiates item.
     *
     * @return new no entry sign item
     */
    @Override
    public Item createNewInstance() {
        return new NoEntrySign();
    }

    /**
     * Plays sound effect.
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(Resources.getGameAudio("oof"));
    }

    /**
     * Prevents rats from walking into its tile until broken.
     *
     * @param level gets tiles
     * @param gc    unused attribute
     */
    public void activate(final Level level, final GraphicsContext gc) {
        switch (getHp()) {
            case NES_4 -> setImage(Resources.getEntityImage("no-entry-sign-4"));
            case NES_3 -> setImage(Resources.getEntityImage("no-entry-sign-3"));
            case NES_2 -> setImage(Resources.getEntityImage("no-entry-sign-2"));
            case 2 -> setImage(Resources.getEntityImage("no-entry-sign-1"));
            case 0 -> {
                level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(this);
                level.getItems().remove(this);
            }
        }
    }

}
