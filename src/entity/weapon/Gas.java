package entity.weapon;

import entity.Entity;
import entity.Item;
import entity.rat.Rat;
import java.util.ArrayList;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

/**
 * Gas
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class Gas extends Item {

    public Gas() {
        setEntityType(EntityType.ITEM);
        setEntityName("Gas");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/gas-grenade.png"));
        setHp(10);
        setDamage(1);
        setRange(3);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.GAS);
        setOffsetY(3);
    }

    @Override
    public Item createNewInstance() {
        return new Gas();
    }

    public void activate(Level level) {
        setHp(getHp() - 1);

        if (getHp() > 0) {
            for (int i = 0; i < getRange() + 1; i++) {
                checkAdjacent(level, i);
                checkAdjacent(level, -(i));
            }
        } else {
            level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(this);
            level.getItems().remove(this);
        }
    }

    private void checkAdjacent(Level level, int i) {
        Tile[][] tiles = level.getTiles();

        if (getCurrentPosX() + i < level.getCols() - 1 && getCurrentPosX() + i >= 0) {
            if (tiles[getCurrentPosY()][getCurrentPosX() + i].isWalkable()) {
                ArrayList<Entity> entities = new ArrayList<>(tiles[getCurrentPosY()][getCurrentPosX() + i].getEntitiesOnTile());

                for (int j = 0; j < entities.size(); j++) {
                    Entity entity = entities.get(j);

                    if (entity.getEntityType() == EntityType.RAT) {
                        Rat target = (Rat) entity;
                        inflictDamage(level, getDamage(), target);
                    }
                }
            }
        }

        if (getCurrentPosY() + i < level.getRows() - 1 && getCurrentPosY() + i >= 0)  {
            if (tiles[getCurrentPosY() + i][getCurrentPosX()].isWalkable()) {
                ArrayList<Entity> entities = new ArrayList<>(tiles[getCurrentPosY() + i][getCurrentPosX()].getEntitiesOnTile());

                for (int j = 0; j < entities.size(); j++) {
                    Entity entity = entities.get(j);

                    if (entity.getEntityType() == EntityType.RAT) {
                        Rat target = (Rat) entity;
                        inflictDamage(level, getDamage(), target);
                    }
                }
            }
        }
    }

}
