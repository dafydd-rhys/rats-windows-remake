package entity.rat;

import entity.Entity;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static main.external.Audio.playGameEffect;

/**
 * Rat.java
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Maurice Petersen
 */
public class Rat extends Entity {

    /**
     *
     */
    private static Level level;
    /**
     *
     */
    private final Queue<Rat> babyQueue;
    /**
     *
     */
    private Direction direction;
    /**
     *
     */
    private Gender gender;
    /**
     *
     */
    private Image rotatedImage;
    /**
     *
     */
    private Image upImage;
    /**
     *
     */
    private Image downImage;
    /**
     *
     */
    private Image leftImage;
    /**
     *
     */
    private Image rightImage;
    /**
     *
     */
    private int hp;
    /**
     *
     */
    private boolean isAdult;
    /**
     *
     */
    private boolean isSterilised;
    /**
     *
     */
    private boolean isPregnant;
    /**
     *
     */
    private int pregnancyStage;
    /**
     *
     */
    private int growingStage;

    /**
     * Instantiates a new Rat.
     *
     * @param gender  the gender
     * @param isAdult the is adult
     */
    public Rat(Gender gender, boolean isAdult) {
        setEntityType(EntityType.RAT);
        setEntityName("Rat");
        setHp(5);
        setDamage(0);
        setGender(gender);
        setAdult(isAdult);
        setSterilised(false);
        setPregnant(false);
        setPregnancyStage(0);
        setGrowingStage(0);
        this.babyQueue = new LinkedList<>();

        setSprites();

        List<Direction> values = List.of(Direction.values());
        setDirection(values.get(new Random().nextInt(values.size())));
    }

    private void setSprites() {
        if (!isAdult) {
            setImage(new Image(System.getProperty("user.dir") +
                    "/src/resources/images/game/entities/baby-rat.png"));
        } else if (gender == Gender.FEMALE) {
            setImage(new Image(System.getProperty("user.dir") +
                    "/src/resources/images/game/entities/death-rat.png"));
        } else if (gender == Gender.MALE) {
            setImage(new Image(System.getProperty("user.dir") +
                    "/src/resources/images/game/entities/male-rat.png"));
        }

        getImages();

        List<Direction> values = List.of(Direction.values());
        setDirection(values.get(new Random().nextInt(values.size())));
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(Level level) {
        Rat.level = level;
    }

    /**
     * Gets images.
     */
    public void getImages() {
        if (!isAdult()) {
            image = RatSprites.upBaby;
            upImage = RatSprites.upBaby;
            rightImage = RatSprites.rightBaby;
            downImage = RatSprites.downBaby;
            leftImage = RatSprites.leftBaby;
        } else if (isSterilised() && getGender() == Gender.MALE) {
            image = RatSprites.upMale;
            upImage = RatSprites.upMale;
            rightImage = RatSprites.rightMale;
            downImage = RatSprites.downMale;
            leftImage = RatSprites.leftMale;
        } else if (isSterilised() && getGender() == Gender.FEMALE) {
            image = RatSprites.upFemale;
            upImage = RatSprites.upFemale;
            rightImage = RatSprites.rightFemale;
            downImage = RatSprites.downFemale;
            leftImage = RatSprites.leftFemale;
        } else if (!isSterilised() && getGender() == Gender.MALE) {
            image = RatSprites.upMaleSterilised;
            upImage = RatSprites.upMaleSterilised;
            rightImage = RatSprites.rightMaleSterilised;
            downImage = RatSprites.downMaleSterilised;
            leftImage = RatSprites.leftMaleSterilised;
        } else if (!isSterilised() && getGender() == Gender.FEMALE) {
            image = RatSprites.upFemaleSterilised;
            upImage = RatSprites.upFemaleSterilised;
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
    public void findPartner(Tile currentTile) {
        ArrayList<Entity> entities = currentTile.getEntitiesOnTile();
        for (Entity e : entities) {
            if (e.getEntityType() == EntityType.RAT) {
                Rat partner = (Rat) e;

                if (getGender() != partner.getGender() && isAdult() &&
                        partner.isAdult()
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
    public void mate(Rat rat) {
        if (getGender() == Gender.MALE && !rat.isPregnant()) {
            rat.setPregnant(true);
        } else if (getGender() == Gender.FEMALE && !isPregnant()) {
            setPregnant(true);
        }
    }

    /**
     * Allows pregnant female rats to give birth to multiple baby rats.
     * Checks if rat is female
     * Checks if rat is pregnant
     * Checks if rat pregnancy stage is at max value
     */
    public void giveBirth() {
        if (getGender() == Gender.FEMALE && isPregnant()) {
            if (getPregnancyStage() == 10) {
                Random rand = new Random();
                int randomNum = rand.nextInt((5) + 1);

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
            } else if (getPregnancyStage() > 10) {
                if (babyQueue.size() > 0) {
                    level.placeRat(babyQueue.poll(),
                            level.getTiles()[getCurrentPosY()][getCurrentPosX()]);
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
        if (getGrowingStage() >= 10) {
            if (!isAdult()) {
                setAdult(true);
                getImages();
            }
        } else {
            setGrowingStage(getGrowingStage() + 1);
        }
    }

    /**
     * @param
     */
    @Override
    public void playSound()
            throws UnsupportedAudioFileException, LineUnavailableException,
            IOException {
        try {
            playGameEffect(System.getProperty("user.dir") +
                    "/src/resources/audio/game/rat_dying.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String result = "";

        result += this.getGender() == Gender.FEMALE
                ? "F"
                : "M";

        result += this.isAdult()
                ? "A"
                : "B";

        result += this.isPregnant()
                ? "P"
                : "N";

        result += this.isSterilised()
                ? "G"
                : "S";

        // TODO add direction
        if (this.direction == Direction.LEFT) {
            result += "L";
        } else if (this.direction == Direction.RIGHT) {
            result += "R";
        } else if (this.direction == Direction.UP) {
            result += "U";
        } else if (this.direction == Direction.DOWN) {
            result += "D";
        }

        result += ":";
        result += String.format("%02d", this.getCurrentPosX());
        result += ":";
        result += String.format("%02d", this.getCurrentPosY());

        return result;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getCurrentPosX() {
        return this.currentPosX;
    }

    @Override
    public void setCurrentPosX(int currentPosX) {
        this.currentPosX = currentPosX;
    }

    @Override
    public int getCurrentPosY() {
        return this.currentPosY;
    }

    @Override
    public void setCurrentPosY(int currentPosY) {
        this.currentPosY = currentPosY;
    }

    @Override
    public String getEntityName() {
        return this.entityName;
    }

    @Override
    protected void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public EntityType getEntityType() {
        return super.getEntityType();
    }

    /**
     * Kill.
     */
    public void kill() {
        level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(
                this);
        level.getRats().remove(this);
        level.setScore(Level.getScore() + 10);
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
     * @param gender the gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
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
    public void setPregnant(boolean pregnant) {
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
     * @param pregnancyStage the pregnancy stage
     */
    public void setPregnancyStage(int pregnancyStage) {
        this.pregnancyStage = pregnancyStage;
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
     * Sets rotated image.
     *
     * @param image the image
     */
    public void setRotatedImage(Image image) {
        this.rotatedImage = image;
    }

    public Image getImage() {
        return this.image;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
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
    public void setAdult(boolean adult) {
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
    public void setSterilised(boolean sterilised) {
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
     * @param direction the direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
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
     * Gets growing stage.
     *
     * @return the growing stage
     */
    public int getGrowingStage() {
        return this.growingStage;
    }

    /**
     * Sets growing stage.
     *
     * @param value the value
     */
    public void setGrowingStage(int value) {
        this.growingStage = value;
    }

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

}
