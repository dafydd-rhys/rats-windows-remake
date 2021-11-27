package entity.weapon;

import entity.Entity;
import entity.Item;
import entity.rat.Rat;
import java.util.ArrayList;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

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
        setHp(10);
        setDamage(0);
        setRange(2);
        setFriendlyFire(false);
        setCanBeAttacked(false);
        setType(TYPE.STERILISATION);
        setOffsetY(7);
    }

    public void activate() {
        setHp(getHp() - 1);

        if (getHp() > 0) {
            for (int i = 0; i < getRange() + 1; i++) {
                checkAdjacent(i);
                checkAdjacent(-(i));
            }
        } else {
            Level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(this);
            Level.getItems().remove(this);
        }
    }

    private void checkAdjacent(int i) {
        Tile[][] tiles = Level.getTiles();

        if (getCurrentPosX() + i < 19 && getCurrentPosX() + i > 0) {
            if (tiles[getCurrentPosY()][getCurrentPosX() + i].isWalkable()) {
                ArrayList<Entity> entities = new ArrayList<>(tiles[getCurrentPosY()][getCurrentPosX() + i].getEntitiesOnTile());

                for (Entity entity : entities) {
                    if (entity.getEntityType() == EntityType.RAT) {
                        Rat target = (Rat) entity;

                        target.setSterilised(true);
                        target.getImages();
                    }
                }
            }
        }
        
        if (getCurrentPosY() + i < Level.rows - 1 && getCurrentPosY() + i > 0) {
            if (tiles[getCurrentPosY() + i][getCurrentPosX()].isWalkable()) {
                ArrayList<Entity> entities = new ArrayList<>(tiles[getCurrentPosY() + i][getCurrentPosX()].getEntitiesOnTile());

                for (Entity entity : entities) {
                    if (entity.getEntityType() == EntityType.RAT) {
                        Rat target = (Rat) entity;

                        target.setSterilised(true);
                        target.getImages();
                    }
                }
            }
        }
    }

}
