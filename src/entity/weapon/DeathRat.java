package entity.weapon;

import entity.Entity;
import entity.rats.Rat;
import java.util.ArrayList;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

/**
 * DeathRat
 *
 * @author Harry Boyce, Bryan Kok
 */

public class DeathRat extends Item { //used to extend Entities.Item

    public DeathRat(int x, int y){
        this.entityName = "DeathRat";
        this.hp = 8; // 8 ticks = 4 seconds
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/death-rat.png");
        this.damage = 5;
        this.range = 2;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.currentPosX = x;
        this.currentPosY = y;
    }

    public void countdown() {

    }

    public void activate(){

    }
}
