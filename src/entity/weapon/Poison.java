package entity.weapon;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.image.Image;
import entity.Entity;
import main.level.Level;

import java.util.ArrayList;

/**
 * Poison
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Poison extends Item {

    public Poison() {
        setEntityType(EntityType.ITEM);
        setEntityName("Poison");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/poison.png"));
        setHp(1);
        setDamage(5);
        setRange(1);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.POISON);
        setOffsetY(6);
    }

    @Override
    public Item createNewInstance() {
        return new Poison();
    }

    public void activate(Level level) {
        ArrayList<Entity> entitiesOnTile = level.getTiles()[this.currentPosY][this.currentPosX].getEntitiesOnTile();

        if (!entitiesOnTile.isEmpty()) {
            for (int k = 0; k < entitiesOnTile.size(); k++) {
                if (entitiesOnTile.get(k).getEntityType() == EntityType.RAT){
                    Rat targetRat = (Rat) entitiesOnTile.get(k);
                    inflictDamage(level, getDamage(), targetRat);
                    setHp(getHp() - 1);

                    if (getHp() <= 0) {
                        level.getItems().remove(this);
                        entitiesOnTile.remove(this);
                    }
                    break;
                }
            }
        }
    }
}
