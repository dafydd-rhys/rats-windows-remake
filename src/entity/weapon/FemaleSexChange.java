package entity.weapon;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.image.Image;
import entity.Entity;
import main.level.Level;
import tile.Tile;

import java.util.ArrayList;

/**
 * FemaleSexChange
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class FemaleSexChange extends Item {

    public FemaleSexChange() {
        setEntityType(EntityType.ITEM);
        setEntityName("FemaleSexChange");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/female-change.png"));
        setHp(1);
        setDamage(0);
        setRange(1);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.FEMALE_CHANGE);
        setOffsetY(2);
    }

    @Override
    public Item createNewInstance() {
        return new FemaleSexChange();
    }

    public void activate(Level level) {
        Tile[][] tile = level.getTiles();
        ArrayList<Entity> entitiesOnTile = tile[getCurrentPosY()][getCurrentPosX()].getEntitiesOnTile();

        if (!entitiesOnTile.isEmpty()) {
            for (int i = 0; i < entitiesOnTile.size(); i++) {
                if (entitiesOnTile.get(i).getEntityType() == EntityType.RAT) {
                    Rat target = (Rat) entitiesOnTile.get(i);

                    if (target.getGender() == Rat.Gender.MALE) {
                        target.setGender(Rat.Gender.FEMALE);
                        target.setImage(new Image(System.getProperty("user.dir") +
                                "\\src\\resources\\images\\game\\entities\\female-rat.png"));
                        target.getImages();

                        this.hp -= 1;
                        level.getItems().remove(this);
                        entitiesOnTile.remove(this);
                        break;
                    }
                }
            }
        }

    }

}
