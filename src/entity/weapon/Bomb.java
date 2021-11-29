package entity.weapon;

import entity.Item;
import entity.rat.Rat;
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
        setRange(0);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.BOMB);
        setOffsetY(0);
    }

    @Override
    public Item createNewInstance() {
        return new Bomb();
    }

    private void countdown(Level level) {
        setHp(getHp() - 1);
        switch (getHp()) {
            case 6 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-3.png"));
            case 4 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-2.png"));
            case 2 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-1.png"));
            case 0 -> explode(level);
        }
    }

    public void activate(Level level) {
        countdown(level);
    }

    private void explode(Level level) {
        Tile[][] tiles = level.getTiles();
        Tile startingTile = tiles[getCurrentPosY()][getCurrentPosX()];

        // TODO add explosion sprite for visual guide
        for (Rat.Direction direction : Rat.Direction.values()) {
            Tile current = tiles[getCurrentPosY()][getCurrentPosX()];

            int distance = 0;
            while (current.isWalkable()) {
                current = getDirection(direction, distance, tiles);
                ArrayList<Entity> entitiesOnTile = current.getEntitiesOnTile();

                if (!entitiesOnTile.isEmpty()) {
                    for (int i = 0; i < entitiesOnTile.size(); i++) {
                        Entity entity = entitiesOnTile.get(i);
                        
                        if (entity.getEntityType() == EntityType.RAT) {
                            Rat target = (Rat) entity;
                            inflictDamage(level, getDamage(), target);
                        }
                    }
                }
                distance++;
            }
        }
        level.getItems().remove(this);
        startingTile.removeEntityFromTile(this);
    }

    private Tile getDirection(Rat.Direction direction, int distance, Tile[][] tiles) {
        return switch (direction) {
            case LEFT -> tiles[getCurrentPosY()][getCurrentPosX() - distance];
            case RIGHT -> tiles[getCurrentPosY()][getCurrentPosX() + distance];
            case UP -> tiles[getCurrentPosY() + distance][getCurrentPosX()];
            case DOWN -> tiles[getCurrentPosY() - distance][getCurrentPosX()];
        };
    }

}
