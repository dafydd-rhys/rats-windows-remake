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
 * @author Dawid Wisniewski, Dafydd Maund, Maurice Petersen
 */
public class Rat extends Entity {

    private Direction direction;

    private Image rotatedImage;
    private final Image image;
    private final Image upImage;
    private final Image downImage;
    private final Image leftImage;
    private final Image rightImage;

    private final int hp;
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
        this.entityName = "Rat";
        this.hp = 1;
        this.damage = 1;
        this.range = 0;
        this.gender = gender;
        this.isAdult = isAdult;
        this.isSterilised = false;
        this.isPregnant = false;
        this.pregnancyStage = 0;
        this.growingStage = 0;

        if (isAdult) {
            this.moveSpeed = 1;
        } else {
            this.moveSpeed = 2;
        }

        if (!isAdult) {
            image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/baby-rat.png");
        } else if (gender == Gender.FEMALE) {
            image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/female-rat.png");
        } else {
            image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/male-rat.png");
        }

        upImage = image;
        leftImage = Entity.rotate(image, 270);
        rightImage = Entity.rotate(image, 90);
        downImage = Entity.rotate(image, 180);

        List<Direction> values = List.of(Direction.values());
        direction = values.get(new Random().nextInt(values.size()));
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

    @Override
    public void setImage(Image image) {
        setImage(image);
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

}
