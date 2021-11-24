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
 * @author Gareth Wade (1901805)
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */

public class Bomb extends Item {

    public Bomb() {
        setEntityType(EntityType.ITEM);
        setEntityName("Bomb");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-4.png"));
        setHp(8);
        setDamage(5);
        setRange(2);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.BOMB);
        setOffsetY(0);
    }

    public void countdown() {
        this.hp -= 1;
        switch (this.hp) {
            case 6 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-3.png"));
            case 4 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-2.png"));
            case 2 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-1.png"));
            case 0 -> {//this.image = null;
            }
        }
    }

    // TODO Need to make bomb destroy everything in its path not 1 tile radius
    public void activate() {
        if (this.hp > 0) {
            countdown();
        } else {
            Tile[][] tiles = Level.getTiles();
            for (int i = 0; i < getRange(); i++) {
                for (int j = 0; j < getRange(); j++) {
                    ArrayList<Entity> entitiesOnTile = tiles[this.currentPosY + j - 1][this.currentPosX + i - 1].getEntitiesOnTile();
                    if (entitiesOnTile != null) {
                        for (Entity entity : entitiesOnTile) {
                            if (entity.getEntityType() == EntityType.RAT) {
                                Rat targetRat = (Rat) entity;
                                inflictDamage(this.damage, targetRat);

                                if (targetRat.getHp() <= 0) {
                                    Level.getRats().remove(targetRat);
                                    tiles[this.currentPosY + j - 1][this.currentPosX + i - 1].removeEntityFromTile(targetRat);
                                    //entitiesOnTile.remove(targetRat);
                                }
                            }
                        }
                    }
                }
            }
            Level.getItems().remove(this);
            tiles[this.currentPosY][this.currentPosX].removeEntityFromTile(this);
        }


    }

}
