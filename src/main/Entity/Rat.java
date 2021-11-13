package Entities;

public class Rat extends Entity {

    private boolean isFemale;
    private boolean isAdult;
    private boolean isPregnant;
    private int moveSpeed;

    public Rat(boolean isFemale) {
        this.entityName = "Rat";
        this.image = null;
        this.hp = 1;
        this.damage = 1;
        this.range = 0;
        this.isAdult = false;
        this.isPregnant = false;
    }

}
