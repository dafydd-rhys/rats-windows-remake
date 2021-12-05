package tile;

import entity.Entity;
import entity.Item;
import entity.rat.Rat;
import java.util.Random;

/**
 * Movement.java, class used to handle rat movement.
 *
 * @author Dafydd Maund (2003900)
 */
public class Movement {

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
    private static Rat rat;
    /**
     * random number generated
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
                    getRat().setDirection(Rat.Direction.LEFT);
                } else {
                    getRat().setRotatedImage(getRat().getRightImage());
                    getRat().setDirection(Rat.Direction.RIGHT);
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
                    getRat().setDirection(Rat.Direction.UP);
                } else {
                    getRat().setRotatedImage(getRat().getDownImage());
                    getRat().setDirection(Rat.Direction.DOWN);
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
        if (getRat().getDirection() == Rat.Direction.UP) {
            getRat().setRotatedImage(getRat().getDownImage());
            getRat().setDirection(Rat.Direction.DOWN);
            getRat().setCurrentPosY(getCurY() + 1);
        } else if (getRat().getDirection() == Rat.Direction.DOWN) {
            getRat().setRotatedImage(getRat().getUpImage());
            getRat().setDirection(Rat.Direction.UP);
            getRat().setCurrentPosY(getCurY() - 1);
        } else if (getRat().getDirection() == Rat.Direction.LEFT) {
            getRat().setRotatedImage(getRat().getRightImage());
            getRat().setDirection(Rat.Direction.RIGHT);
            getRat().setCurrentPosX(getCurX() + 1);
        } else if (getRat().getDirection() == Rat.Direction.RIGHT) {
            getRat().setRotatedImage(getRat().getLeftImage());
            getRat().setDirection(Rat.Direction.LEFT);
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
        Movement.tiles = t;
    }

    /**
     * sets rat.
     *
     * @param r rat
     */
    public static void setRat(final Rat r) {
        Movement.rat = r;
    }

    /**
     * sets current tile.
     *
     * @param c current tile
     */
    public static void setCurrent(final Tile c) {
        Movement.current = c;
    }

    /**
     * sets current y.
     *
     * @param y current y
     */
    public static void setCurY(final int y) {
        Movement.curY = y;
    }

    /**
     * sets current x.
     *
     * @param x current x
     */
    public static void setCurX(final int x) {
        Movement.curX = x;
    }

    /**
     * sets random.
     *
     * @param r random num
     */
    public static void setRandom(final int r) {
        Movement.random = r;
    }

    /**
     * gets tiles on board
     *
     * @return tiles
     */
    public static Tile[][] getTiles() {
        return tiles;
    }

    /**
     * gets current rat
     *
     * @return rat
     */
    public static Rat getRat() {
        return rat;
    }

    /**
     * gets current tile
     *
     * @return current tile
     */
    public static Tile getCurrent() {
        return current;
    }

    /**
     * gets current y coordinate
     *
     * @return current y
     */
    public static int getCurY() {
        return curY;
    }

    /**
     * gets current x coordinate
     *
     * @return current x
     */
    public static int getCurX() {
        return curX;
    }

    /**
     * returns random number generated.
     *
     * @return random num
     */
    public static int getRandom() {
        return random;
    }

}
