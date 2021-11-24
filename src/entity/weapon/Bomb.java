package entity.weapon;

import entity.Item;
import entity.Rat;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;
import entity.Entity;

import java.util.ArrayList;

/**
 * Bomb
 *
 * @author Harry Boyce
 * @author Gareth Wade (1901805)
 * @author Bryan Kok
 */

public class Bomb extends Item { //used to extend Entities.Item

    public Bomb() {
        this.entityName = "Bomb";
        this.hp = 8; // 8 ticks = 4 seconds
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb.png");
        this.damage = 5;
        this.range = 2;
        this.friendlyFire = true;
        this.isAttackable = false;
        this.type = TYPE.BOMB;
        this.yOffset = 1;
    }

    public void countdown() {
        this.hp -= 1;
        if (this.hp == 0) activate();
    }

    public void activate() {
        Tile[][] tiles = Level.getTiles();
        for (int i = 0; i < this.range; i++) {
            for (int j = 0; j < this.range; j++) {
                ArrayList<Entity> entitiesOnTile = tiles[this.currentPosY + j - 1][this.currentPosX + i - 1].getEntitiesOnTile();
                if (entitiesOnTile != null) {
                    for (int k = 0; k < entitiesOnTile.size(); k++) {
                        if (entitiesOnTile.get(k).getEntityName().equals("Rat")) {
                            Rat targetRat = (Rat) entitiesOnTile.get(k);
                            inflictDamage(this.damage, targetRat);
                            // Removes dead rat from list of entities on the tile
                            if (targetRat.getHp() <= 0) {
                                tiles[this.currentPosY + j - 1][this.currentPosX + i - 1].removeEntityFromTile(targetRat);
                                entitiesOnTile.remove(targetRat);
                            }
                        }
                    }
                }
            }
        }
        tiles[this.currentPosY][this.currentPosX].removeEntityFromTile(this);
    }
}
