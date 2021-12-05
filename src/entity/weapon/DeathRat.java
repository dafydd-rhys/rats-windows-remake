package entity.weapon;

import static main.external.Audio.playGameEffect;
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
import main.Resources;
import main.level.Level;
import tile.Tile;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Death Rat weapon class.
 *
 * @author Dafydd -Rhys Maund
 * @author Harry Boyce
 * @author Bryan Kok
 */
public class DeathRat extends Item {

    /**
     * Direction of death rat.
     */
    private Direction direction;
    /**
     * current tick of level.
     */
    private int currentTick = 0;
    /**
     * rotated image of death rat.
     */
    private Image rotatedImage;
    /**
     * up image of death rat.
     */
    private final Image upImage;
    /**
     * down image of death rat.
     */
    private final Image downImage;
    /**
     * left image of death rat.
     */
    private final Image leftImage;
    /**
     * right image of death rat.
     */
    private final Image rightImage;
    /**
     * hp/damage - death rat.
     */
    private static final int HP_AND_DMG = 5;

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
        setImage(Resources.getEntityImage("death-rat"));
        setHp(HP_AND_DMG);
        setDamage(HP_AND_DMG);
        setRange(2);
        setType(TYPE.DEATH_RAT);
        setOffsetY(1);

        upImage = RatSprites.UP_DEATH;
        rightImage = RatSprites.rightDeath;
        downImage = RatSprites.downDeath;
        leftImage = RatSprites.leftDeath;

        List<Direction> values = List.of(Direction.values());
        setDirection(values.get(new Random().nextInt(values.size())));
    }

    /**
     * Instantiates item.
     *
     * @return new death rat item
     */
    @Override
    public Item createNewInstance() {
        return new DeathRat();
    }

    /**
     * Plays sound effect.
     */
    @Override
    public void playSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playGameEffect(Resources.getGameAudio("rat_dying"));
    }

    /**
     * activates rats listener.
     *
     * @param level the level
     * @param gc    the gc
     */
    public void activate(final Level level, final GraphicsContext gc) {
        currentTick++;
        checkForOpposition(level);
        int moveTick = 2;
        if (currentTick % moveTick == 0) {
            move(level);
        }
    }

    /**
     * moves death rat.
     *
     * @param level current level
     */
    private void move(final Level level) {
        DeathRatMovement.setTiles(level.getTiles());
        DeathRatMovement.setRat(this);
        DeathRatMovement.setCurrent(level.getTiles()[getCurrentPosY()][getCurrentPosX()]);
        DeathRatMovement.setCurX(getCurrentPosX());
        DeathRatMovement.setCurY(getCurrentPosY());

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
     * checks for rats to kill.
     *
     * @param level level
     */
    private void checkForOpposition(final Level level) {
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
     * @param dir the direction
     */
    public void setDirection(final Direction dir) {
        this.direction = dir;
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
     * @param image the rotated image
     */
    public void setRotatedImage(final Image image) {
        this.rotatedImage = image;
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

    /**
     * Movement.java, class used to handle rat movement.
     *
     * @author Dafydd Maund (2003900)
     */
    private static class DeathRatMovement {

        /**
         * The Tiles.
         */
        private static Tile[][] tiles;
        /**
         * The constant current.
         */
        private static Tile current;
        /**
         * The constant rat.
         */
        private static DeathRat rat;
        /**
         *
         */
        private static int random;
        /**
         * The constant curX.
         */
        private static int curX;
        /**
         * The constant curY.
         */
        private static int curY;

        /**
         * generate random number.
         *
         * @param count random between 0 and count
         * @return generated random
         */
        private static int generateRandom(final int count) {
            return new Random().nextInt((count) + 1);
        }

        /**
         * Try horizontal movement.
         *
         * @param x  direction travelling
         * @param x2 opposite direction travelling
         */
        public static void tryHorizontal(final int x, final int x2) {
            int count = 0;

            //can move forward
            if (getTiles()[getCurY()][getCurX() + x].isWalkable()) {
                //can move forward & up or down (if yes at crossroads/corner)
                if (getTiles()[getCurY() + 1][getCurX()].isWalkable()) {
                    count++;
                } else if (getTiles()[getCurY() - 1][getCurX()].isWalkable()) {
                    count++;
                }
                setRandom(generateRandom(count));

                //randomizes which direction to move, works with 2 or 3 dir cause of generateRandom(count)
                if (getRandom() == 0) {
                    moveHorizontal(x);
                } else if (getRandom() == 1) {
                    if (moveVertical(1)) {
                        if (moveVertical(-1)) {
                            if (moveHorizontal(x)) {
                                moveHorizontal(x2);
                            }
                        }
                    }
                } else if (getRandom() == 2) {
                    if (moveVertical(-1)) {
                        if (moveVertical(1)) {
                            if (moveHorizontal(x)) {
                                moveHorizontal(x2);
                            }
                        }
                    }
                }
            } else {
                //can't move forward
                if (getTiles()[getCurY() + 1][getCurX()].isWalkable()
                        && getTiles()[getCurY() - 1][getCurX()].isWalkable()) {
                    count = 1;
                }
                setRandom(generateRandom(count));

                //randomizes movement in case of 2 directions
                //turns around if you can't move
                if (getRandom() == 0) {
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
         * Try vertical movement.
         *
         * @param y  direction travelling
         * @param y2 opposite direction travelling
         */
        public static void tryVertical(final int y, final int y2) {
            int count = 0;

            //can move in same direction
            if (getTiles()[getCurY() + y][getCurX()].isWalkable()) {
                //can move forward & left or right (if yes at crossroads/corner)
                if (getTiles()[getCurY()][getCurX() + 1].isWalkable()) {
                    count++;
                } else if (getTiles()[getCurY()][getCurX() - 1].isWalkable()) {
                    count++;
                }
                setRandom(generateRandom(count));

                //randomizes which direction to move, works with 2 or 3 dir cause of generateRandom(count)
                if (getRandom() == 0) {
                    moveVertical(y);
                } else if (getRandom() == 1) {
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
                //can't move in same dir
                if (getTiles()[getCurY()][getCurX() + 1].isWalkable()
                        && getTiles()[getCurY()][getCurX() - 1].isWalkable()) {
                    count = 1;
                }
                setRandom(generateRandom(count));

                //randomizes movement in case of 2 directions
                //turns around if you can't move
                if (getRandom() == 0) {
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
         * moves horizontal.
         *
         * @param x direction
         * @return if rat is moved
         */
        private static boolean moveHorizontal(final int x) {
            //if next tile is walkable
            if (getTiles()[getCurY()][getCurX() + x].isWalkable()) {
                //if not entry sign
                if (noEntry(0, x)) {
                    //remove rat from current
                    getCurrent().removeEntityFromTile(getRat());
                    getTiles()[getCurY()][getCurX()].getEntitiesOnTile().remove(getRat());
                    getTiles()[getCurY()][getCurX() + x].addEntityToTile(getRat());

                    //move rat
                    getRat().setCurrentPosX(getCurX() + x);
                    getRat().setCurrentPosY(getCurY());

                    //set new direction
                    if (x == -1) {
                        getRat().setRotatedImage(getRat().getLeftImage());
                        getRat().setDirection(Direction.LEFT);
                    } else {
                        getRat().setRotatedImage(getRat().getRightImage());
                        getRat().setDirection(Direction.RIGHT);
                    }
                    return false;
                } else {
                    //sets rats opposite direction
                    setRatsDirection();
                }
            }

            return true;
        }

        /**
         * moves vertical.
         *
         * @param y direction
         * @return if rat is moved
         */
        private static boolean moveVertical(final int y) {
            //if next tile is walkable
            if (getTiles()[getCurY() + y][getCurX()].isWalkable()) {
                //if not entry sign
                if (noEntry(y, 0)) {
                    //remove rat from current
                    getCurrent().removeEntityFromTile(getRat());
                    getTiles()[getCurY()][getCurX()].getEntitiesOnTile().remove(getRat());
                    getTiles()[getCurY() + y][getCurX()].addEntityToTile(getRat());

                    //move rat
                    getRat().setCurrentPosX(getCurX());
                    getRat().setCurrentPosY(getCurY() + y);

                    //set new direction
                    if (y == -1) {
                        getRat().setRotatedImage(getRat().getUpImage());
                        getRat().setDirection(Direction.UP);
                    } else {
                        getRat().setRotatedImage(getRat().getDownImage());
                        getRat().setDirection(Direction.DOWN);
                    }
                    return false;
                } else {
                    //sets rats opposite direction
                    setRatsDirection();
                }
            }
            return true;
        }

        /**
         * changes rats dir and image to opposite.
         */
        private static void setRatsDirection() {
            //set rats opposite direction and image for that dir
            if (getRat().getDirection() == Direction.UP) {
                getRat().setRotatedImage(getRat().getDownImage());
                getRat().setDirection(Direction.DOWN);
                getRat().setCurrentPosY(getCurY() + 1);
            } else if (getRat().getDirection() == Direction.DOWN) {
                getRat().setRotatedImage(getRat().getUpImage());
                getRat().setDirection(Direction.UP);
                getRat().setCurrentPosY(getCurY() - 1);
            } else if (getRat().getDirection() == Direction.LEFT) {
                getRat().setRotatedImage(getRat().getRightImage());
                getRat().setDirection(Direction.RIGHT);
                getRat().setCurrentPosX(getCurX() + 1);
            } else if (getRat().getDirection() == Direction.RIGHT) {
                getRat().setRotatedImage(getRat().getLeftImage());
                getRat().setDirection(Direction.LEFT);
                getRat().setCurrentPosX(getCurX() - 1);
            }
        }

        /**
         * checks if no entry is at position.
         *
         * @param y y coordinate
         * @param x x coordinate
         * @return if no entry is at position
         */
        private static boolean noEntry(final int y, final int x) {
            //loops through entities
            for (Entity entity : getTiles()[getCurY() + y][getCurX() + x].getEntitiesOnTile()) {
                if (entity.getEntityType() == Entity.EntityType.ITEM) {
                    Item item = (Item) entity;
                    if (item.getType() == Item.TYPE.NO_ENTRY) {
                        //remove hp from no entry
                        item.setHp(item.getHp() - 2);
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         * set tiles.
         *
         * @param t tiles
         */
        public static void setTiles(final Tile[][] t) {
            DeathRatMovement.tiles = t;
        }

        /**
         * sets rat.
         *
         * @param r rat
         */
        public static void setRat(final DeathRat r) {
            DeathRatMovement.rat = r;
        }

        /**
         * sets current tile.
         *
         * @param c current tile
         */
        public static void setCurrent(final Tile c) {
            DeathRatMovement.current = c;
        }

        /**
         * sets current y.
         *
         * @param y current y
         */
        public static void setCurY(final int y) {
            DeathRatMovement.curY = y;
        }

        /**
         * sets current x.
         *
         * @param x current x
         */
        public static void setCurX(final int x) {
            DeathRatMovement.curX = x;
        }

        /**
         * sets random.
         *
         * @param r random num
         */
        public static void setRandom(final int r) {
            DeathRatMovement.random = r;
        }

        /**
         * @return tiles
         */
        public static Tile[][] getTiles() {
            return tiles;
        }

        /**
         * @return rat
         */
        public static DeathRat getRat() {
            return rat;
        }

        /**
         * @return current tile
         */
        public static Tile getCurrent() {
            return current;
        }

        /**
         * @return current y
         */
        public static int getCurY() {
            return curY;
        }

        /**
         * @return current x
         */
        public static int getCurX() {
            return curX;
        }

        /**
         * @return random num
         */
        public static int getRandom() {
            return random;
        }

    }

}
