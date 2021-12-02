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
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class DeathRat extends Item {//used to extend Entities.Item

    /** */
    private Direction direction;
    /** */
    private int currentTick = 0;
    /** */
    private Image rotatedImage;
    /** */
    private final Image upImage;
    /** */
    private final Image downImage;
    /** */
    private final Image leftImage;
    /** */
    private final Image rightImage;

    /**
     * The enum Direction.
     */
    public enum Direction {
        /**
         * Up direction.
         */
        UP(),
        /**
         * Right direction.
         */
        RIGHT(),
        /**
         * Down direction.
         */
        DOWN(),
        /**
         * Left direction.
         */
        LEFT()
    }

    /**
     * Instantiates a new Death rat.
     */
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

    /**
     *
     *
     * @param level the level
     * @param gc    the gc
     */
    public void activate(Level level, GraphicsContext gc) {
        currentTick++;
        checkForOpposition(level);
        int moveTick = 2;
        if (currentTick % moveTick == 0) {
            move(level);
        }
    }

    /**
     *
     *
     * @param level
     */
    private void move(Level level) {
        DeathRatMovement.tiles = level.getTiles();
        DeathRatMovement.rat = this;
        DeathRatMovement.current = level.getTiles()[getCurrentPosY()][getCurrentPosX()];
        DeathRatMovement.curX = getCurrentPosX();
        DeathRatMovement.curY = getCurrentPosY();

        if (getDirection() == Direction.LEFT) {
            DeathRatMovement.tryHorizontal(-1, 1);
        } else if (getDirection() == Direction.RIGHT) {
            DeathRatMovement.tryHorizontal(1, -1);
        } else if (getDirection() == Direction.UP) {
            DeathRatMovement.tryVertical(-1, 1);
        } else if (getDirection() == Direction.DOWN) {
            DeathRatMovement.tryVertical(1, -1);
        }
        checkForOpposition(level);
    }

    /**
     *
     *
     * @param level
     */
    private void checkForOpposition(Level level) {
        ArrayList<Entity> entities = level.getTiles()[getCurrentPosY()][getCurrentPosX()].getEntitiesOnTile();

        if (!entities.isEmpty()) {
            for (int i = 0; i < entities.size(); i++) {
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

    /**
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets rotated image.
     *
     * @param rotatedImage the rotated image
     */
    public void setRotatedImage(Image rotatedImage) {
        this.rotatedImage = rotatedImage;
    }

    /**
     * Gets rotated image.
     *
     * @return the rotated image
     */
    public Image getRotatedImage() {
        return rotatedImage;
    }

    /**
     * Gets up image.
     *
     * @return the up image
     */
    public Image getUpImage() {
        return upImage;
    }

    /**
     * Gets right image.
     *
     * @return the right image
     */
    public Image getRightImage() {
        return rightImage;
    }

    /**
     * Gets down image.
     *
     * @return the down image
     */
    public Image getDownImage() {
        return downImage;
    }

    /**
     * Gets left image.
     *
     * @return the left image
     */
    public Image getLeftImage() {
        return leftImage;
    }

    private static class DeathRatMovement {

        private static int random;
        /**
         * The constant rat.
         */
        public static DeathRat rat;
        /**
         * The Tiles.
         */
        public static Tile[][] tiles;
        /**
         * The constant current.
         */
        public static Tile current;
        /**
         * The constant curX.
         */
        public static int curX;
        /**
         * The constant curY.
         */
        public static int curY;

        private static int generateRandom(int count) {
            return new Random().nextInt((count) + 1);
        }

        /**
         * Try horizontal.
         *
         * @param x  the x
         * @param x2 the x 2
         */
        public static void tryHorizontal(int x, int x2) {
            int count = 0;

            if (tiles[curY][curX + x].isWalkable()) {
                if (tiles[curY + 1][curX].isWalkable()) {
                    count++;
                } else if (tiles[curY - 1][curX].isWalkable()) {
                    count++;
                }
                random = generateRandom(count);

                if (random == 0) {
                    moveHorizontal(x);
                } else if (random == 1) {
                    if (moveVertical(1)) {
                        if (moveVertical(-1)) {
                            if (moveHorizontal(x)) {
                                moveHorizontal(x2);
                            }
                        }
                    }
                } else if (random == 2) {
                    if (moveVertical(-1)) {
                        if (moveVertical(1)) {
                            if (moveHorizontal(x)) {
                                moveHorizontal(x2);
                            }
                        }
                    }
                }
            } else {
                if (tiles[curY + 1][curX].isWalkable() && tiles[curY - 1][curX].isWalkable()) {
                    count = 1;
                }
                random = generateRandom(count);

                if (random == 0) {
                    if (moveVertical(1)) {
                        if (moveVertical(-1)) {
                            moveHorizontal(x2);
                        }
                    }
                } else {
                    if (moveVertical(-1)) {
                        if (moveVertical(1)) {
                            moveHorizontal(x2);
                        }
                    }
                }
            }
        }

        /**
         * Try vertical.
         *
         * @param y  the y
         * @param y2 the y 2
         */
        public static void tryVertical(int y, int y2) {
            int count = 0;

            if (tiles[curY + y][curX].isWalkable()) {
                if (tiles[curY][curX + 1].isWalkable()) {
                    count++;
                } else if (tiles[curY][curX - 1].isWalkable()) {
                    count++;
                }
                random = generateRandom(count);

                if (random == 0) {
                    moveVertical(y);
                } else if (random == 1) {
                    if (moveHorizontal(1)) {
                        if (moveHorizontal(-1)) {
                            if (moveVertical(y)) {
                                moveVertical(y2);
                            }
                        }
                    }
                } else {
                    if (moveHorizontal(-1)) {
                        if (moveHorizontal(1)) {
                            if (moveVertical(y)) {
                                moveVertical(y2);
                            }
                        }
                    }
                }
            } else {
                if (tiles[curY][curX + 1].isWalkable() && tiles[curY][curX - 1].isWalkable()) {
                    count = 1;
                }
                random = generateRandom(count);

                if (random == 0) {
                    if (moveHorizontal(-1)) {
                        if (moveHorizontal(1)) {
                            moveVertical(y2);
                        }
                    }
                } else {
                    if (moveHorizontal(1)) {
                        if (moveHorizontal(-1)) {
                            moveVertical(y2);
                        }
                    }
                }
            }
        }

        /**
         *
         *
         * @param x
         * @return
         */
        private static boolean moveHorizontal(int x) {
            if (tiles[curY][curX + x].isWalkable()) {
                if (NoEntry(0, x)) {
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
                } else {
                    setRatsDirection();
                }
            }

            return true;
        }

        /**
         *
         *
         * @param y
         * @return
         */
        private static boolean moveVertical(int y) {
            if (tiles[curY + y][curX].isWalkable()) {
                if (NoEntry(y, 0)) {
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
                } else {
                    setRatsDirection();
                }
            }
            return true;
        }

        /**
         *
         */
        private static void setRatsDirection() {
            if (rat.getDirection() == Direction.UP) {
                rat.setRotatedImage(rat.getDownImage());
                rat.setDirection(Direction.DOWN);
                rat.setCurrentPosY(curY + 1);
            } else if (rat.getDirection() == Direction.DOWN) {
                rat.setRotatedImage(rat.getUpImage());
                rat.setDirection(Direction.UP);
                rat.setCurrentPosY(curY - 1);
            } else if (rat.getDirection() == Direction.LEFT) {
                rat.setRotatedImage(rat.getRightImage());
                rat.setDirection(Direction.RIGHT);
                rat.setCurrentPosX(curX + 1);
            } else if (rat.getDirection() == Direction.RIGHT) {
                rat.setRotatedImage(rat.getLeftImage());
                rat.setDirection(Direction.LEFT);
                rat.setCurrentPosX(curX - 1);
            }
        }

        /**
         *
         *
         * @param y
         * @param x
         * @return
         */
        private static boolean NoEntry(int y, int x) {
            for (Entity entity : tiles[curY + y][curX + x].getEntitiesOnTile()) {
                if (entity.getEntityType() == Entity.EntityType.ITEM) {
                    Item item = (Item) entity;
                    if (item.getType() == Item.TYPE.NO_ENTRY) {
                        item.setHp(item.getHp() - 2);
                        return false;
                    }
                }
            }
            return true;
        }

    }

}
