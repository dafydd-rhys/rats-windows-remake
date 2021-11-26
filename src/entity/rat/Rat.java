package entity.rat;

import entity.Entity;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

import java.util.*;

/**
 * Rat.java
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Maurice Petersen
 */
public class Rat extends Entity {

    private Direction direction;
    private Gender gender;

    private Image rotatedImage;
    private Image upImage;
    private Image downImage;
    private Image leftImage;
    private Image rightImage;

    private int hp;
    private boolean isAdult;
    private boolean isSterilised;
    private boolean isPregnant;
    private int pregnancyStage;
    private int growingStage;
    private Queue<Rat> babyQueue;


    public enum Gender {
        MALE(),
        FEMALE()
    }

    public enum Direction {
        UP(),
        LEFT(),
        RIGHT(),
        DOWN()
    }

    public Rat(Gender gender, boolean isAdult) {
        setEntityType(EntityType.RAT);
        setEntityName("Rat");
        setHp(1);
        setDamage(1);
        setGender(gender);
        setAdult(isAdult);
        setSterilised(false);
        setPregnant(false);
        setPregnancyStage(0);
        setGrowingStage(0);
        this.babyQueue = new LinkedList<>();

        if (!isAdult) {
            setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/baby-rat.png"));
        } else if (gender == Gender.FEMALE) {
            setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/death-rat.png"));
        } else if (gender == Gender.MALE){
            setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/male-rat.png"));
        }

        getImages();

        List<Direction> values = List.of(Direction.values());
        setDirection(values.get(new Random().nextInt(values.size())));
    }

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
        } else if (isSterilised() && getGender()== Gender.FEMALE) {
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

                if (this.getGender() != partner.getGender() && this.isAdult() && partner.isAdult()
                        && this.isSterilised() && partner.isSterilised()) {
                    this.mate(partner);
                }
            }
        }
    }

    /**
     * Mate with another rat.
     * @param rat other rat.
     */
    public void mate(Rat rat) {
        if (this.getGender() == Gender.MALE && !rat.isPregnant()) {
            rat.setPregnant(true);
        } else if (this.getGender() == Gender.FEMALE && !this.isPregnant()) {
            this.setPregnant(true);
        }
        // TODO Make rats stop on tile while reproducing ??
    }

    /**
     * Allows pregnant female rats to give birth to multiple baby rats.
     * Checks if rat is female
     * Checks if rat is pregnant
     * Checks if rat pregnancy stage is at max value
     */
    public void giveBirth() {
        if (this.getGender() == Gender.FEMALE && this.isPregnant()) {
            if (this.pregnancyStage == 10) {
                Random rand = new Random();
                int randomNum = rand.nextInt((5) + 1);

                for (int i = 0; i < randomNum; i++) {
                    if (randomNum % 2 == 0) {
                        babyQueue.add(new Rat(Gender.FEMALE, false));
                    } else {
                        babyQueue.add(new Rat(Gender.MALE, false));
                    }
                }
            } else if (this.getPregnancyStage() > 10) {

                if (babyQueue.size() > 0) {
                    Level.placeRat(babyQueue.poll(),
                            Level.getTiles()[this.getCurrentPosX()][this.getCurrentPosY()]);
                } else {
                    this.setPregnancyStage(0);
                }
            }
            this.pregnancyStage += 1;
        }
    }

    /**
     * Allows baby rats to grow into adult rats.
     */
    public void growUp() {
        if (this.getGrowingStage() >= 10) {
            if (!this.isAdult()) {
                this.setAdult(true);
                if (this.getGender() == Gender.FEMALE) {
                    this.setImage((new Image(System.getProperty("user.dir") +
                            "\\src\\resources\\images\\game\\entities\\female-rat.png")));
                } else {
                    this.setImage((new Image(System.getProperty("user.dir") +
                            "\\src\\resources\\images\\game\\entities\\male-rat.png")));
                }

                this.getImages();
            }
        } else {
            this.growingStage += 1;
        }
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
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

    public void kill() {
        Level.getTiles()[getCurrentPosY()][getCurrentPosY()].removeEntityFromTile(this);
        Level.getRats().remove(this);
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public int getPregnancyStage() {
        return pregnancyStage;
    }

    public void setPregnancyStage(int pregnancyStage) {
        this.pregnancyStage = pregnancyStage;
    }

    public void setRotatedImage(Image image) {
        rotatedImage = image;
    }

    public Image getRotatedImage() {
        return rotatedImage;
    }

    public Image getImage() {
        return image;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public boolean isSterilised() {
        return !isSterilised;
    }

    public void setSterilised(boolean sterilised) {
        isSterilised = sterilised;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Image getUpImage() {
        return upImage;
    }

    public Image getDownImage() {
        return downImage;
    }

    public Image getLeftImage() {
        return leftImage;
    }

    public Image getRightImage() {
        return rightImage;
    }

    public void setGrowingStage(int value) {
        this.growingStage = value;
    }

    public int getGrowingStage() {
        return growingStage;
    }

}
