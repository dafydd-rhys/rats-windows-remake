package entity.weapon;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.image.Image;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * MaleSexChange
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */

public class MaleSexChange extends Item {

    public MaleSexChange() {
        setEntityType(EntityType.ITEM);
        setEntityName("MaleSexChange");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/male-change.png"));
        setHp(1);
        setDamage(0);
        setRange(1);
        setFriendlyFire(false);
        setCanBeAttacked(false);
        setType(TYPE.MALE_CHANGE);
        setOffsetY(4);
    }

    @Override
    public Item createNewInstance() {
        return new MaleSexChange();
    }

    public void activate(Level level) {
        Tile[][] tile = level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[getCurrentPosY()][getCurrentPosX()].getEntitiesOnTile();

        if (entitiesOnTile != null) {
            for (int i = 0; i < entitiesOnTile.size(); i++) {
                if (entitiesOnTile.get(i).getEntityType() == EntityType.RAT) {
                    Rat target = (Rat) entitiesOnTile.get(i);

                    if (target.getGender() == Rat.Gender.FEMALE) {
                        target.setGender(Rat.Gender.MALE);
                        if (target.isAdult()) {
                            if (target.isSterilised()) {
                                target.setImage(new Image(System.getProperty("user.dir") +
                                        "\\src\\resources\\images\\game\\entities\\sterilised-male-rat.png"));
                            } else {
                                target.setImage(new Image(System.getProperty("user.dir") +
                                        "\\src\\resources\\images\\game\\entities\\male-rat.png"));
                            }
                        }
                        target.getImages();
                    }
                    this.hp -= 1;
                    level.getItems().remove(this);
                    entitiesOnTile.remove(this);
                }
            }
        }

    }

}
