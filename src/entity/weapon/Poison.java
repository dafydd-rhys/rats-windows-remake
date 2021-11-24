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
        ArrayList<Entity> entitiesOnTile = Level.getTiles()[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (int k = 0; k < entitiesOnTile.size(); k++) {
                if (entitiesOnTile.get(k).getEntityName().equals("Rat")){
                    Rat targetRat = (Rat) entitiesOnTile.get(k);
                    inflictDamage(this.damage, targetRat);
                    this.hp -= 1;
                    if (targetRat.getHp() <= 0 || this.hp <= 0) {
                        Level.getItems().remove(this);
                        Level.getRats().remove(targetRat);
                        entitiesOnTile.remove(this);
                        entitiesOnTile.remove(targetRat);
                    }
                    break;
                }
            }
        }
    }
}
