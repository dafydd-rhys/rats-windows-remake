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
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */

public class NoEntrySign extends Item {

    public NoEntrySign() {
        setEntityType(EntityType.ITEM);
        setEntityName("NoEntry");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-5.png"));
        setHp(8);
        setDamage(0);
        setRange(1);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.NO_ENTRY);
        setOffsetY(5);
    }

    public void activate() {

        Tile[][] tile = Level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (Entity entity : entitiesOnTile) {
                if (entity.getEntityType() == EntityType.RAT) {
                    // TODO needs a method to prevent targetRat from passing here
                    // FIXME tile[this.currentPosY][this.currentPosX].isWalkable = false;
                    this.hp -= 1;
                    switch (this.hp) {
                        case 4 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-4.png"));
                        case 3 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-3.png"));
                        case 2 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-2.png"));
                        case 1 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/no-entry-sign-1.png"));
                        case 0 -> {
                            setImage(null);
                            tile[this.currentPosY][this.currentPosX].removeEntityFromTile(this);
                        }
                    }

                }
            }
        }


    }

}
