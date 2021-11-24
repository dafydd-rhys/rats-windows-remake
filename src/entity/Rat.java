package entity;

import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Rat.java
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Maurice Petersen
 */
public class Rat extends Entity {

    private Direction direction;

    private Image rotatedImage;
    private Image image;
    private final Image upImage;
    private final Image downImage;
    private final Image leftImage;
    private final Image rightImage;

    private int hp;
    private boolean isAdult;
    private boolean isSterilised;
    private Gender gender;
    private int moveSpeed;
    private boolean isPregnant;
    private int pregnancyStage;
    private int growingStage;

    public enum Gender {
        MALE,
        FEMALE
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

        if (isAdult) {
            setMoveSpeed(1);
        } else {
            setMoveSpeed(2);
        }

        if (!isAdult) {
            setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/baby-rat.png"));
        } else if (gender == Gender.FEMALE) {
            setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/female-rat.png"));
        } else {
            setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/male-rat.png"));
        }

        upImage = image;
        rightImage = Entity.rotate(image, 90);
        downImage = Entity.rotate(image, 180);
        leftImage = Entity.rotate(image, 270);

        List<Direction> values = List.of(Direction.values());
        setDirection(values.get(new Random().nextInt(values.size())));
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
            if (e.getEntityName().equals("Rat")) {
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
        if (this.getGender() == Gender.FEMALE && this.isPregnant() && this.getPregnancyStage() == 10) {
            Random rand = new Random();
            int randomNum = rand.nextInt((5) + 1);

            for (int i = 0; i < randomNum; i++) {
                if (randomNum % 2 == 0) {
                    Level.getTiles()[this.getCurrentPosY()][this.getCurrentPosX()]
                            .addEntityToTile(new Rat(Gender.FEMALE,false));
                } else {
                    Level.getTiles()[this.getCurrentPosY()][this.getCurrentPosX()]
                            .addEntityToTile(new Rat(Gender.MALE,false));
                }
            }
        }
    }

    /**
     * Allows baby rats to grow into adult rats.
     */
    public void growUp() {
        if (this.growingStage < 10)
            if (!this.isAdult()) {
                // TODO Change sprite.
                this.setAdult(true);
                this.setMoveSpeed(moveSpeed - 1);
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

    public Gender getGender() {
        return gender;
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

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
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