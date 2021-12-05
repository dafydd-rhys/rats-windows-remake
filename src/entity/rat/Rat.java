package entity.rat;

import main.external.Audio;
import static main.external.Audio.playGameEffect;
import entity.Entity;
import javafx.scene.image.Image;
import main.Resources;
import main.level.Level;
import tile.Tile;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.List;

/**
 * This class represents the rat.
 * All rat sprites were created by us.
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Maurice Petersen
 */
public class Rat extends Entity {

    /**
     * Rat HP value.
     */
    private static final int RAT_HP = 5;

    /**
     * Max pregnancy stage - how long until giving birth.
     */
    private static final int MAX_PREGNANCY_STAGE = 6;

    /**
     * Mate for this long.
     */
    private static final int MAX_MATE_STAGE = 4;

    /**
     * Max growing stage.
     */
    private static final int MAX_GROWING_STAGE = 10;

    /**
     * Amount of score a player gets for killing a rat.
     */
    private static final int RAT_SCORE = 10;

    /**
     * Range for the amount of babies a rat can give birth to.
     */
    private static final int BABY_RANGE = 5;

    /**
     * Direction the rat is facing.
     */
    private Direction direction;

    /**
     * Rat's gender.
     */
    private Gender gender;

    /**
     * Rat's rotated image.
     */
    private Image rotatedImage;

    /**
     * Rat's up image.
     */
    private Image upImage;

    /**
     * Rat's down image.
     */
    private Image downImage;

    /**
     * Rat's left image.
     */
    private Image leftImage;

    /**
     * Rat's right image.
     */
    private Image rightImage;

    /**
     * Rat's HP (Hit points).
     */
    private int hp;

    /**
     * Whether the rat is adult or not.
     */
    private boolean isAdult;

    /**
     * Whether the rat is sterilized or not.
     */
    private boolean isSterilised;

    /**
     * Whether the rat is pregnant or not.
     */
    private boolean isPregnant;

    /**
     * Rat's pregnancy stage.
     */
    private int pregnancyStage;

    /**
     * Rat's growing stage.
     */
    private int growingStage;

    /**
     * Pregnant rat's babies.
     */
    private final Queue<Rat> babyQueue;

    /**
     * Current level.
     */
    private static Level level;

    /**
     * how long rat has been mating.
     */
    private int mateCounter = 0;

    /**
     * whether rat is mating.
     */
    private boolean isMating;

    /**
     * The enum Gender.
     */
    public enum Gender {
        /**
         * Male gender.
         */
        MALE(),
        /**
         * Female gender.
         */
        FEMALE()
    }

    /**
     * The enum Direction.
     */
    public enum Direction {
        /**
         * Up direction.
         */
        UP(),
        /**
         * Left direction.
         */
        LEFT(),
        /**
         * Right direction.
         */
        RIGHT(),
        /**
         * Down direction.
         */
        DOWN()
    }

    /**
     * Instantiates a new Rat.
     *
     * @param ratGender  the gender.
     * @param ratIsAdult if rat is adult.
     */
    public Rat(final Gender ratGender, final boolean ratIsAdult) {
        setEntityType(EntityType.RAT);
        setEntityName("Rat");
        setHp(RAT_HP);
        setDamage(0);
        setGender(ratGender);
        setAdult(ratIsAdult);
        setSterilised(false);
        setPregnant(false);
        setPregnancyStage(0);
        setGrowingStage(0);
        this.babyQueue = new LinkedList<>();

        setSprites();

        List<Direction> values = List.of(Direction.values());
        setDirection(values.get(new Random().nextInt(values.size())));
    }

    /**
     * Set rat sprites based on rat type.
     */
    private void setSprites() {
        if (!isAdult) {
            setImage(Resources.getEntityImage("baby-rat"));
        } else if (gender == Gender.FEMALE) {
            setImage(Resources.getEntityImage("death-rat"));
        } else if (gender == Gender.MALE) {
            setImage(Resources.getEntityImage("male-rat"));
        }

        getImages();
        List<Direction> values = List.of(Direction.values());
        setDirection(values.get(new Random().nextInt(values.size())));
    }

    /**
     * Sets level.
     *
     * @param newLevel the level
     */
    public void setLevel(final Level newLevel) {
        Rat.level = newLevel;
    }

    /**
     * Gets images.
     */
    public void getImages() {
        if (!isAdult()) {
            image = RatSprites.UP_BABY;
            upImage = RatSprites.UP_BABY;
            rightImage = RatSprites.rightBaby;
            downImage = RatSprites.downBaby;
            leftImage = RatSprites.leftBaby;
        } else if (isSterilised() && getGender() == Gender.MALE) {
            image = RatSprites.UP_MALE;
            upImage = RatSprites.UP_MALE;
            rightImage = RatSprites.rightMale;
            downImage = RatSprites.downMale;
            leftImage = RatSprites.leftMale;
        } else if (isSterilised() && getGender() == Gender.FEMALE) {
            image = RatSprites.UP_FEMALE;
            upImage = RatSprites.UP_FEMALE;
            rightImage = RatSprites.rightFemale;
            downImage = RatSprites.downFemale;
            leftImage = RatSprites.leftFemale;
        } else if (!isSterilised() && getGender() == Gender.MALE) {
            image = RatSprites.UP_MALE_STERILISED;
            upImage = RatSprites.UP_MALE_STERILISED;
            rightImage = RatSprites.rightMaleSterilised;
            downImage = RatSprites.downMaleSterilised;
            leftImage = RatSprites.leftMaleSterilised;
        } else if (!isSterilised() && getGender() == Gender.FEMALE) {
            image = RatSprites.UP_FEMALE_STERILISED;
            upImage = RatSprites.UP_FEMALE_STERILISED;
            rightImage = RatSprites.rightFemaleSterilised;
            downImage = RatSprites.downFemaleSterilised;
            leftImage = RatSprites.leftFemaleSterilised;
        }
    }

    /**
     * Finds a potential mating partner.
     * Checks if opposite genders,
     * Checks if both adults,
     * Checks if both not sterilised
     *
     * @param currentTile current tile the rat is standing on.
     */
    public void findPartner(final Tile currentTile) {
        ArrayList<Entity> entities = currentTile.getEntitiesOnTile();
        for (Entity e : entities) {
            if (e.getEntityType() == EntityType.RAT) {
                Rat partner = (Rat) e;

                if (getGender() != partner.getGender() && isAdult() && partner.isAdult()
                        && isSterilised() && partner.isSterilised()) {
                    mate(partner);
                }
            }
        }
    }

    /**
     * Mate with another rat.
     *
     * @param rat other rat.
     */
    public void mate(final Rat rat) {
        if (getGender() == Gender.MALE && !rat.isPregnant()) {
            rat.setPregnant(true);
            this.setMating(true);
            rat.setMating(true);
        } else if (getGender() == Gender.FEMALE && !isPregnant()) {
            setPregnant(true);
            this.setMating(true);
            rat.setMating(true);
        }
    }


    /**
     * Adds to mate counter.
     *
     * @param rat the rat that needs increasing
     * @throws UnsupportedAudioFileException incorrect audio file
     * @throws LineUnavailableException audio file
     * @throws IOException audio file not found
     */
    public void keepMating(final Rat rat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        rat.setMateCounter(rat.getMateCounter() + 1);

        if (rat.getMateCounter() >= MAX_MATE_STAGE) {
            rat.setMating(false);
            rat.setMateCounter(0);
        } else {
            Audio.playGameEffect(Resources.getGameAudio("mating"));
        }
    }

    /**
     * Allows pregnant female rats to give birth to multiple baby rats.
     * Checks if rat is female
     * Checks if rat is pregnant
     * Checks if rat pregnancy stage is at max value
     */
    public void giveBirth() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (getGender() == Gender.FEMALE && isPregnant()) {
            if (getPregnancyStage() == 1) {
                Random rand = new Random();
                int randomNum = rand.nextInt((BABY_RANGE) + 1);

                for (int i = 0; i < randomNum; i++) {
                    Rat rat;
                    if (randomNum % 2 == 0) {
                        rat = new Rat(Gender.FEMALE, false);
                    } else {
                        rat = new Rat(Gender.MALE, false);
                    }
                    rat.setEntityType(EntityType.RAT);
                    babyQueue.add(rat);
                }
            } else if (getPregnancyStage() > MAX_PREGNANCY_STAGE) {
                if (babyQueue.size() > 0) {
                    level.placeRat(babyQueue.poll(), level.getTiles()[getCurrentPosY()][getCurrentPosX()]);
                    Audio.playGameEffect(Resources.getGameAudio("spawn"));
                } else {
                    setPregnancyStage(0);
                }
            }
            setPregnancyStage(getPregnancyStage() + 1);
        }
    }

    /**
     * Allows baby rats to grow into adult rats.
     */
    public void growUp() {
        if (getGrowingStage() >= MAX_GROWING_STAGE) {
            if (!isAdult()) {
                setAdult(true);
                getImages();
            }
        } else {
            setGrowingStage(getGrowingStage() + 1);
        }
    }

    /**
     * Returns the number of babies a pregnant rat is carrying. Then clears the baby queue.
     * @return baby queue size
     */
    public int killBabies() {
        int size = babyQueue.size();
        babyQueue.clear();

        return size;
    }

    /**
     * Plays a sound.
     * @throws UnsupportedAudioFileException if audio file is unrecognized/unsupported.
     * @throws LineUnavailableException if audio file is unavailable.
     * @throws IOException if unable to read file.
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {
            playGameEffect(Resources.getGameAudio("oof"));
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        String result = "";

        // Add rat attributes.
        result += getGender() == Gender.FEMALE
                ? "F"
                : "M";

        result += isAdult()
                ? "A"
                : "B";

        result += isPregnant()
                ? "P"
                : "N";

        result += isSterilised()
                ? "G"
                : "S";

        // Add current direction rat is facing.
        if (this.direction == Direction.LEFT) {
            result += "L";
        } else if (this.direction == Direction.RIGHT) {
            result += "R";
        } else if (this.direction == Direction.UP) {
            result += "U";
        } else if (this.direction == Direction.DOWN) {
            result += "D";
        }

        // Add current position
        result += ":";
        result += String.format("%02d", this.getCurrentPosX());
        result += ":";
        result += String.format("%02d", this.getCurrentPosY());

        return result;
    }

    /**
     * Set rat image.
     * @param image the image
     */
    @Override
    public void setImage(final Image image) {
        this.image = image;
    }

    /**
     * Returns rat hp.
     * @return rat hp value.
     */
    @Override
    public int getHp() {
        return this.hp;
    }

    /**
     * Sets rat hp.
     * @param newHp the hp value.
     */
    @Override
    public void setHp(final int newHp) {
        this.hp = newHp;
    }

    /**
     * Returns current rat position X.
     * @return rat position X.
     */
    @Override
    public int getCurrentPosX() {
        return this.currentPosX;
    }

    /**
     * Sets current rat position X.
     * @param currentPosX the current pos X.
     */
    @Override
    public void setCurrentPosX(final int currentPosX) {
        this.currentPosX = currentPosX;
    }

    /**
     * Returns current rat position Y.
     * @return rat position Y.
     */
    @Override
    public int getCurrentPosY() {
        return this.currentPosY;
    }

    /**
     * Sets current rat position Y.
     * @param currentPosY the current pos Y.
     */
    @Override
    public void setCurrentPosY(final int currentPosY) {
        this.currentPosY = currentPosY;
    }

    /**
     * Returns entity name.
     * @return entity name.
     */
    @Override
    public String getEntityName() {
        return this.entityName;
    }

    /**
     * Sets the entity name.
     * @param entityName the entity name.
     */
    @Override
    protected void setEntityName(final String entityName) {
        this.entityName = entityName;
    }

    /**
     * Returns entity type.
     * @return entity type.
     */
    @Override
    public EntityType getEntityType() {
        return super.getEntityType();
    }

    /**
     * set whether rat is mating.
     *
     * @param value is rat mating
     */
    public void setMating(final boolean value) {
        this.isMating = value;
    }

    /**
     * @return whether rat is mating.
     */
    public boolean getMating() {
        return this.isMating;
    }

    /**
     * sets mate counter.
     *
     * @param counter new counter value
     */
    public void setMateCounter(final int counter) {
        mateCounter = counter;
    }

    /**
     * @return mate counter
     */
    public int getMateCounter() {
        return mateCounter;
    }

    /**
     * Kills the rat.
     */
    public void kill() {
        if (isPregnant()) {
            level.setScore(Level.getScore() + (killBabies() * RAT_SCORE));
        }

        level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(this);
        level.getRats().remove(this);
        level.setScore(Level.getScore() + RAT_SCORE);
        try {
            playSound();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Sets gender.
     *
     * @param newGender the gender
     */
    public void setGender(final Gender newGender) {
        this.gender = newGender;
    }

    /**
     * Is pregnant boolean.
     *
     * @return the boolean
     */
    public boolean isPregnant() {
        return this.isPregnant;
    }

    /**
     * Sets pregnant.
     *
     * @param pregnant the pregnant
     */
    public void setPregnant(final boolean pregnant) {
        this.isPregnant = pregnant;
    }

    /**
     * Gets pregnancy stage.
     *
     * @return the pregnancy stage
     */
    public int getPregnancyStage() {
        return this.pregnancyStage;
    }

    /**
     * Sets pregnancy stage.
     *
     * @param newPregnancyStage the pregnancy stage
     */
    public void setPregnancyStage(final int newPregnancyStage) {
        this.pregnancyStage = newPregnancyStage;
    }

    /**
     * Sets rotated image.
     *
     * @param image the image
     */
    public void setRotatedImage(final Image image) {
        this.rotatedImage = image;
    }

    /**
     * Gets rotated image.
     *
     * @return the rotated image
     */
    public Image getRotatedImage() {
        return this.rotatedImage;
    }

    /**
     * Returns the rat image.
     * @return rat image.
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Is adult boolean.
     *
     * @return the boolean
     */
    public boolean isAdult() {
        return this.isAdult;
    }

    /**
     * Sets adult.
     *
     * @param adult the adult
     */
    public void setAdult(final boolean adult) {
        this.isAdult = adult;
    }

    /**
     * Is sterilised boolean.
     *
     * @return the boolean
     */
    public boolean isSterilised() {
        return !isSterilised;
    }

    /**
     * Sets sterilised.
     *
     * @param sterilised the sterilised
     */
    public void setSterilised(final boolean sterilised) {
        this.isSterilised = sterilised;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Sets direction.
     *
     * @param newDirection the direction
     */
    public void setDirection(final Direction newDirection) {
        this.direction = newDirection;
    }

    /**
     * Gets up image.
     *
     * @return the up image
     */
    public Image getUpImage() {
        return this.upImage;
    }

    /**
     * Gets down image.
     *
     * @return the down image
     */
    public Image getDownImage() {
        return this.downImage;
    }

    /**
     * Gets left image.
     *
     * @return the left image
     */
    public Image getLeftImage() {
        return this.leftImage;
    }

    /**
     * Gets right image.
     *
     * @return the right image
     */
    public Image getRightImage() {
        return this.rightImage;
    }

    /**
     * Sets growing stage.
     *
     * @param value the value
     */
    public void setGrowingStage(final int value) {
        this.growingStage = value;
    }

    /**
     * Gets growing stage.
     *
     * @return the growing stage
     */
    public int getGrowingStage() {
        return this.growingStage;
    }

}
