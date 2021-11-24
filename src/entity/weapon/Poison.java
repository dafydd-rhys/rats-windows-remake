package entity.weapon;

import entity.Item;
import entity.Rat;
import javafx.scene.image.Image;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * Poison
 *
 * @author Harry Boyce, Bryan Kok
 */

public class Poison extends Item {

    public Poison() {
        this.entityName = "Poison";
        this.image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/poison.png");
        this.hp = 1;
        this.damage = 5;
        this.range = 1;
        this.friendlyFire = false;
        this.isAttackable = false;
        this.type = TYPE.POISON;
        this.yOffset = 7;
    }

    public void activate() {
        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();
        System.out.println(entitiesOnTile);
        if (entitiesOnTile != null) {
            for (int k = 0; k < entitiesOnTile.size(); k++) {
                if (entitiesOnTile.get(k).getEntityName().equals("Rat")){
                    ArrayList<Entity> toRemove = new ArrayList<>();
                    Rat targetRat = (Rat) entitiesOnTile.get(k);
                    inflictDamage(this.damage, targetRat);

                    if (targetRat.getHp() <= 0) {
                        Level.getRats().remove(targetRat);
                        //tile[this.currentPosY][this.currentPosX].removeEntityFromTile(targetRat);
                        //entitiesOnTile.remove(targetRat);
                        toRemove.add(targetRat);
                        toRemove.add(this);
                    }
                    this.hp -= 1;
                    // FIXME removing weapon from game board
                    Level.getItems().remove(this);
                    entitiesOnTile.removeAll(toRemove);
                    break;
                }
            }
        }
    }
}
