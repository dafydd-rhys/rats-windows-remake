package entity.rats;

import entity.Entity;
import javafx.scene.image.Image;
import tile.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Rat.java
 *
 * @author Dawid Wisniewski, Maurice Petersen
 */
public abstract class Rat extends Entity {

    protected String image;
    protected int hp;

    private final int MOVEMENT_SPEED_ADULT = 10;
    private final int MOVEMENT_SPEED_BABY = 20;
    private final int MIN_BABY_RATS = 0;
    private final int MAX_BABY_RATS = 5;

    private boolean isFemale;
    private boolean isAdult;
    private boolean isPregnant;
    private boolean isSterilised;
    private int moveSpeed;
    private int pregnancyStage;

    public Rat(boolean isFemale) {
        this.entityName = "Rat";
        this.image = null;
        this.hp = 1;
        this.damage = 1;
        this.range = 0;
        this.isFemale = isFemale;
        this.isAdult = false;
        this.isPregnant = false;
        this.isSterilised = false;
        this.moveSpeed = MOVEMENT_SPEED_BABY;
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
        int maxRats = MAX_BABY_RATS;
        int minRats = MIN_BABY_RATS;
        int randomNum = rand.nextInt((maxRats - minRats) + 1) + minRats;
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
            moveSpeed = MOVEMENT_SPEED_ADULT;
        }
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
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

}
