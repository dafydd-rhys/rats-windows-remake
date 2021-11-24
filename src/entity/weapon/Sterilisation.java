package entity.weapon;

import entity.Entity;
import entity.Item;
import entity.Rat;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * Sterilisation
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Sterilisation extends Item {

    public Sterilisation() {
        setEntityType(EntityType.ITEM);
        setEntityName("Sterilisation");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/sterilisation.png"));
        setHp(8);
        setDamage(0);
        setRange(2);
        setFriendlyFire(false);
        setCanBeAttacked(false);
        setType(TYPE.STERILISATION);
        setOffsetY(7);
    }

    public void activate() {
        Tile[][] tiles = Level.getTiles();
        for (int i = 0; i < getRange(); i++) {
            for (int j = 0; j < getRange(); j++) {
                ArrayList<Entity> entitiesOnTile = tiles[this.currentPosY + j - 1][this.currentPosX + i - 1].getEntitiesOnTile();
                if (entitiesOnTile != null) {
                    for (Entity entity : entitiesOnTile) {
                        if (entity.getEntityType() == EntityType.RAT) {
                            Rat targetRat = (Rat) entity;
                            targetRat.setSterilised(true);
                        }
                    }
                }
            }
        }

        this.hp -= 1;
        if (this.hp == 0) {
            tiles[this.currentPosY][this.currentPosX].removeEntityFromTile(this);
        }
    }

}
