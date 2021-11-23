package entity.rats;

import entity.Entity;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import tile.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Rat.java
 *
 * @author Dawid Wisniewski, Dafydd Maund, Maurice Petersen
 */
public abstract class Rat extends Entity {

    protected Direction direction;
    protected Image image;
    protected Image upImage;
    protected Image downImage;
    protected Image leftImage;
    protected Image rightImage;
    protected Image rotatedImage;
    protected int hp;

    private final int SPEED = 1;

    private boolean isFemale;
    private boolean isAdult;
    private boolean isPregnant;
    private boolean isSterilised;
    private int moveSpeed;
    private int pregnancyStage;

    public enum Direction {
        UP(),
        LEFT(),
        RIGHT(),
        DOWN()
    }

    public Rat(boolean isFemale, boolean isAdult) {
        this.entityName = "Rat";
        this.hp = 1;
        this.damage = 1;
        this.range = 0;
        this.isFemale = isFemale;
        this.isAdult = isAdult;
        this.isPregnant = false;
        this.isSterilised = false;
        this.moveSpeed = SPEED;
        this.pregnancyStage = 0;
    }

    /**
     * Finds a potential mating partner.
     * @param currentTile current tile the rat is standing on.
     */
    public void findPartner(Tile currentTile) {
        ArrayList<Entity> entities = currentTile.getEntitiesOnTile();
        for (Entity e : entities) {
            if (e.getClass().getName().equals("entity.rats.Rat")) {
                Rat partner = (Rat) e;
                if (this.isFemale != partner.isFemale
                    && this.isAdult && partner.isAdult
                    && !this.isSterilised && !partner.isSterilised) {
                    this.mate(partner);
                }
            }
        }
    }

    /**
     *
     * @param rat rat to mate with.
     */
    private void mate(Rat rat) {
        if (this.isFemale && !this.isPregnant) {
            this.isPregnant = true;
        } else if (rat.isFemale && !rat.isPregnant) {
            rat.isPregnant = true;
        }
    }

    /**
     *
     */
    public void giveBirth() {
        Random rand = new Random();
        int randomNum = rand.nextInt((5) + 1);

        if (this.isAdult && this.isFemale && this.pregnancyStage == 10) {
            for (int i = 0; i < randomNum; i++) {
                System.out.println(i + "Born");
            }
        }
    }

    /**
     * Allows baby rats to grow into adult rats.
     */
    public void growUp() {
        if (!isAdult) {
            isAdult = true;
            moveSpeed = SPEED;
        }
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
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

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public boolean isSterilised() {
        return isSterilised;
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

    public int getPregnancyStage() {
        return pregnancyStage;
    }

    public void setPregnancyStage(int pregnancyStage) {
        this.pregnancyStage = pregnancyStage;
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
