package entity.weapon;

import entity.Item;
import javafx.scene.image.Image;
import entity.Rat;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * NoEntrySign
 *
 * @author Harry Boyce, Bryan Kok
 */

public class NoEntrySign extends Item {

    public NoEntrySign() {
        this.entityName = "No Entry Sign";
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-5.png");
        this.hp = 5;
        this.damage = 0;
        this.range = 1;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.type = TYPE.NO_ENTRY;
        this.yOffset = 6;
    }

    public void activate() {

        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (Entity entity : entitiesOnTile) {
                if (entity.getEntityName().equals("Rat")) {
                    Rat targetRat = (Rat) entity;
                    // [needs a method to prevent targetRat from passing here]
                    this.hp -= 1;
                    switch (this.hp) {
                        case 4 -> this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-4.png");
                        case 3 -> this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-3.png");
                        case 2 -> this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-2.png");
                        case 1 -> this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-1.png");
                        case 0 -> {
                            this.image = null;
                            tile[this.currentPosY][this.currentPosX].removeEntityFromTile(this);
                        }
                    }

                }
            }
        }


    }

}
