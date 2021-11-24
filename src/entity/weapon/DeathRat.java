package entity.weapon;

import entity.Item;
import javafx.scene.image.Image;

/**
 * DeathRat
 *
 * @author Harry Boyce, Bryan Kok
 */

public class DeathRat extends Item { //used to extend Entities.Item

    public DeathRat(){
        this.entityName = "DeathRat";
        this.hp = 8; // 8 ticks = 4 seconds
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/death-rat.png");
        this.damage = 5;
        this.range = 2;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.type = TYPE.DEATH_RAT;
        this.yOffset = 2;
    }

    // TODO

    public void countdown() {

    }

    public void activate(){

    }
}
