package entity.weapon;

import entity.Entity;
import entity.Item;
import entity.rat.Rat;
import entity.rat.RatSprites;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Tile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static main.external.Audio.playGameEffect;

/**
 * DeathRat
 *
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class DeathRat extends Item {//used to extend Entities.Item

    /**
     *
     */
    private final Image upImage;
    /**
     *
     */
    private final Image downImage;
    /**
     *
     */
    private final Image leftImage;
    /**
     *
     */
    private final Image rightImage;
    /**
     *
     */
    private Direction direction;
    /**
     *
     */
    private int currentTick = 0;
    /**
     *
     */
    private Image rotatedImage;

    /**
     * Instantiates a new Death rat.
     */
    public DeathRat() {
        setEntityType(EntityType.ITEM);
        setEntityName("DeathRat");
        setImage(new Image(System.getProperty("user.dir") +
                "/src/resources/images/game/entities/death-rat.png"));
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
    public void playSound()
            throws UnsupportedAudioFileException, LineUnavailableException,
            IOException {
        playGameEffect(System.getProperty("user.dir") +
                "/src/resources/audio/game/rat_dying.wav");
    }

    /**
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
     * @param level
     */
    private void move(Level level) {
        DeathRatMovement.tiles = level.getTiles();
        DeathRatMovement.rat = this;
        DeathRatMovement.current =
                level.getTiles()[getCurrentPosY()][getCurrentPosX()];
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

    /**
     * @param level
     */
    private void checkForOpposition(Level level) {
        ArrayList<Entity> entities =
                level.getTiles()[getCurrentPosY()][getCurrentPosX()].getEntitiesOnTile();

        if (!entities.isEmpty()) {
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).getEntityType() == EntityType.RAT) {
                    Rat targetRat = (Rat) entities.get(i);
                    inflictDamage(level, getDamage(), targetRat);

                    setHp(getHp() - 1);
                    if (getHp() <= 0) {
                        level.getTiles()[getCurrentPosY()][getCurrentPosX()].removeEntityFromTile(
                                this);
                        level.getItems().remove(this);
                    }
                }
            }
        }
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
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
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
     * Sets rotated image.
     *
     * @param rotatedImage the rotated image
     */
    public void setRotatedImage(Image rotatedImage) {
        this.rotatedImage = rotatedImage;
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

    private static class DeathRatMovement {

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
        private static int random;

        /**
         * Try horizontal.
         *
         * @param x  the x
         * @param x2 the x 2
         */
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

        /**
         * Try vertical.
         *
         * @param y  the y
         * @param y2 the y 2
         */
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

        /**
         * @param x
         * @return
         */
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

        /**
         * @param y
         * @return
         */
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

        /**
         * @return
         */
        private static int generateRandom() {
            return new Random().nextInt((1) + 1);
        }

    }

}
