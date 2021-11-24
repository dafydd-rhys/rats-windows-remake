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

    private void countdown() {
        this.hp -= 1;
        switch (this.hp) {
            case 6 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-3.png"));
            case 4 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-2.png"));
            case 2 -> setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/bomb-1.png"));
            case 0 -> {
                setImage(null);
            }
        }
    }

    // TODO Need to make bomb destroy everything in its path not 1 tile radius
    public void activate() {
        Tile[][] tiles = Level.getTiles();
        if (this.hp > 0) {
            countdown();
        } else {
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
                explode(tiles, "LEFT");
                explode(tiles, "RIGHT");
                explode(tiles, "UP");
                explode(tiles, "DOWN");
                Level.getItems().remove(this);
                tiles[this.currentPosY][this.currentPosX].removeEntityFromTile(this);
            }
        }
    }

    private void explode (Tile[][] tiles, String direction){
        int dir = 0;
        Tile dirTiles = getDirection(direction, dir, tiles);
        ArrayList<Entity> entitiesOnTile;
        // TODO add explosion sprite for visual guide
        while (dirTiles.isWalkable() && dirTiles != null) {
            dirTiles = getDirection(direction, dir, tiles);
            entitiesOnTile = dirTiles.getEntitiesOnTile();
            if (entitiesOnTile != null){
                for (int i = 0; i < entitiesOnTile.size(); i++) {
                    if (entitiesOnTile.get(i).getEntityType().equals(EntityType.RAT)) {
                        Rat targetRat = (Rat) entitiesOnTile.get(i);
                        inflictDamage(this.damage, targetRat);
                        if (targetRat.getHp() <= 0) {
                            Level.getRats().remove(targetRat);
                            dirTiles.removeEntityFromTile(targetRat);
                            entitiesOnTile.remove(targetRat);
                        }
                    }
                }
            }
            dir ++;
        }
    }

    private Tile getDirection(String direction, int dir, Tile[][] tiles) {
        return switch (direction) {
            case "LEFT" -> tiles[this.currentPosY][this.currentPosX - dir];
            case "RIGHT" -> tiles[this.currentPosY][this.currentPosX + dir];
            case "UP" -> tiles[this.currentPosY + dir][this.currentPosX];
            case "DOWN" -> tiles[this.currentPosY - dir][this.currentPosX];
            default -> null;
        };
    }

    /*
            for (int i = 0; i < this.range; i++) {
                 for (int j = 0; j < this.range; j++) {
                     ArrayList<Entity> entitiesOnTile = tiles[this.currentPosY + j - 1][this.currentPosX + i - 1].getEntitiesOnTile();
                     if (entitiesOnTile != null) {
                         for (int k = 0; k < entitiesOnTile.size(); k++) {
                             if (entitiesOnTile.get(k).getEntityName().equals("Rat")) {
                                 Rat targetRat = (Rat) entitiesOnTile.get(k);
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
            */
            
}
