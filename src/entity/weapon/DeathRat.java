package entity.weapon;

import entity.Entity;
import entity.Item;
import entity.rat.Rat;
import entity.rat.RatSprites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import static main.external.Audio.playGameEffect;

/**
 * DeathRat
 *
 * @author Dafydd-Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */

public class DeathRat extends Item {//used to extend Entities.Item

    private Direction direction;
    private int currentTick = 0;
    private Image rotatedImage;
    private final Image upImage;
    private final Image downImage;
    private final Image leftImage;
    private final Image rightImage;

    public enum Direction {
        UP(),
        RIGHT(),
        DOWN(),
        LEFT()
    }

    public DeathRat() {
        setEntityType(EntityType.ITEM);
        setEntityName("DeathRat");
        setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/death-rat.png"));
        setHp(5);
        setDamage(5);
        setRange(2);
        setFriendlyFire(true);
        setCanBeAttacked(false);
        setType(TYPE.DEATH_RAT);
        setOffsetY(1);

        upImage = RatSprites.upDeath;
        rightImage = RatSprites.rightDeath;
        downImage = RatSprites.downDeath;
        leftImage = RatSprites.leftDeath;

        List<Direction> values = List.of(Direction.values());
        setDirection(values.get(new Random().nextInt(values.size())));
    }

    @Override
    public Item createNewInstance() {
        return new DeathRat();
    }

    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(System.getProperty("user.dir") + "/src/resources/audio/game/rat_dying.wav");
    }

    public void activate(Level level, GraphicsContext gc) {
        currentTick++;

        int moveTick = 2;
        if (currentTick % moveTick == 0) {
            move(level);
        }
    }

    private void move(Level level) {
        DeathRatMovement.tiles = level.getTiles();
        DeathRatMovement.rat = this;
        DeathRatMovement.current = level.getTiles()[getCurrentPosY()][getCurrentPosX()];
        DeathRatMovement.curX = getCurrentPosX();
        DeathRatMovement.curY = getCurrentPosY();

        if (this.getDirection() == Direction.LEFT) {
            DeathRatMovement.tryHorizontal(-1, 1);
        } else if (this.getDirection() == Direction.RIGHT) {
            DeathRatMovement.tryHorizontal(1, -1);
        } else if (this.getDirection() == Direction.UP) {
            DeathRatMovement.tryVertical(-1, 1);
        } else if (this.getDirection() == Direction.DOWN) {
            DeathRatMovement.tryVertical(1, -1);
        }
        checkForOpposition(level);
    }

    private void checkForOpposition(Level level) {
        ArrayList<Entity> entities = level.getTiles()[this.getCurrentPosY()][this.getCurrentPosX()].getEntitiesOnTile();
        ArrayList<Entity> a = level.getTiles()[this.getCurrentPosY() + 1][this.getCurrentPosX()].getEntitiesOnTile();
        ArrayList<Entity> b = level.getTiles()[this.getCurrentPosY() - 1][this.getCurrentPosX()].getEntitiesOnTile();
        ArrayList<Entity> c = level.getTiles()[this.getCurrentPosY()][this.getCurrentPosX() + 1].getEntitiesOnTile();
        ArrayList<Entity> d = level.getTiles()[this.getCurrentPosY()][this.getCurrentPosX() - 1].getEntitiesOnTile();
        entities.addAll(a);
        entities.addAll(b);
        entities.addAll(c);
        entities.addAll(d);
        System.out.println(entities);
        if (!entities.isEmpty()) {
            for (int i = 0; i < entities.size(); i++) {
            // TODO figure out why it dies so easily
                if (entities.get(i).getEntityType() == EntityType.RAT) {
                    Rat targetRat = (Rat) entities.get(i);
                    inflictDamage(level, getDamage(), targetRat);
                    setHp(getHp() - 1);
                    if (getHp() <= 0) {
                        level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(this);
                        level.getItems().remove(this);
                    }
                }
            }
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setRotatedImage(Image rotatedImage) {
        this.rotatedImage = rotatedImage;
    }

    public Image getRotatedImage() {
        return rotatedImage;
    }

    public Image getUpImage() {
        return upImage;
    }

    public Image getRightImage() {
        return rightImage;
    }

    public Image getDownImage() {
        return downImage;
    }

    public Image getLeftImage() {
        return leftImage;
    }

    private static class DeathRatMovement {

        private static int random;
        public static DeathRat rat;
        public static Tile[][] tiles;
        public static Tile current;
        public static int curX;
        public static int curY;

        public static void tryHorizontal(int x, int x2) {
            random = generateRandom();

            if (moveHorizontal(x)) {
                if (random == 1) {
                    if (moveVertical(-1)) {
                        if (moveVertical(1)) {
                            if (moveHorizontal(x2)) {
                                System.out.println("couldn't move");
                            }
                        }
                    }
                } else {
                    if (moveVertical(1)) {
                        if (moveVertical(-1)) {
                            if (moveHorizontal(x2)) {
                                System.out.println("couldn't move");
                            }
                        }
                    }
                }
            }
        }

        public static void tryVertical(int y, int y2) {
            random = generateRandom();

            if (moveVertical(y)) {
                if (random == 1) {
                    if (moveHorizontal(-1)) {
                        if (moveHorizontal(1)) {
                            if (moveVertical(y2)) {
                                System.out.println("couldn't move");
                            }
                        }
                    }
                } else {
                    if (moveHorizontal(1)) {
                        if (moveHorizontal(-1)) {
                            if (moveVertical(y2)) {
                                System.out.println("couldn't move");
                            }
                        }
                    }
                }
            }
        }

        private static boolean moveHorizontal(int x) {
            for (Entity entity : tiles[curY][curX + x].getEntitiesOnTile()) {
                if (entity.getEntityType() == Entity.EntityType.ITEM) {
                    Item item = (Item) entity;
                    if (item.getType() == Item.TYPE.NO_ENTRY) {
                        return true;
                    }
                }
            }

            if (tiles[curY][curX + x].isWalkable()) {
                current.removeEntityFromTile(rat);
                tiles[curY][curX].getEntitiesOnTile().remove(rat);
                tiles[curY][curX + x].addEntityToTile(rat);

                rat.setCurrentPosX(curX + x);
                rat.setCurrentPosY(curY);

                if (x == -1) {
                    rat.setRotatedImage(rat.getLeftImage());
                    rat.setDirection(Direction.LEFT);
                } else {
                    rat.setRotatedImage(rat.getRightImage());
                    rat.setDirection(Direction.RIGHT);
                }
                return false;
            }

            return true;
        }

        private static boolean moveVertical(int y) {
            for (Entity entity : tiles[curY + y][curX].getEntitiesOnTile()) {
                if (entity.getEntityType() == Entity.EntityType.ITEM) {
                    Item item = (Item) entity;
                    if (item.getType() == Item.TYPE.NO_ENTRY) {
                        return true;
                    }
                }
            }

            if (tiles[curY + y][curX].isWalkable()) {
                current.removeEntityFromTile(rat);
                tiles[curY][curX].getEntitiesOnTile().remove(rat);
                tiles[curY + y][curX].addEntityToTile(rat);

                rat.setCurrentPosX(curX);
                rat.setCurrentPosY(curY + y);

                if (y == -1) {
                    rat.setRotatedImage(rat.getUpImage());
                    rat.setDirection(Direction.UP);
                } else {
                    rat.setRotatedImage(rat.getDownImage());
                    rat.setDirection(Direction.DOWN);
                }
                return false;
            }

            return true;
        }

        private static int generateRandom() {
            return new Random().nextInt((1) + 1);
        }

    }

}
