package tile;

import entity.Entity;
import entity.Item;
import entity.rat.Rat;
import java.util.Random;

/**
 * Main
 *
 * @author Dafydd Maund (2003900)
 */
public class Movement {

    public static Tile[][] tiles;
    public static Tile current;
    public static Rat rat;

    private static int random;
    public static int curX;
    public static int curY;

    private static int generateRandom3() {
        return new Random().nextInt((2) + 1);
    }

    private static int generateRandom2() {
        return new Random().nextInt((1) + 1);
    }

    public static void tryHorizontal(int x, int x2) {
        random = generateRandom3();

        if (NoEntry(0, x)) {
            if (random == 0) {
                if (moveVertical(-1)) {
                    if (moveVertical(1)) {
                        if (moveHorizontal(x)) {
                            moveHorizontal(x2);
                        }
                    }
                }
            } else if (random == 1) {
                if (moveVertical(1)) {
                    if (moveVertical(-1)) {
                        if (moveHorizontal(x)) {
                            moveHorizontal(x2);
                        }
                    }
                }
            } else {
                moveHorizontal(x);
            }
        } else {
            random = generateRandom2();

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

    public static void tryVertical(int y, int y2) {
        random = generateRandom3();

        if (NoEntry(y, 0)) {
            if (random == 0) {
                if (moveHorizontal(-1)) {
                    if (moveHorizontal(1)) {
                        if (moveVertical(y)) {
                            moveVertical(y2);
                        }
                    }
                }
            } else if (random == 1) {
                if (moveHorizontal(1)) {
                    if (moveHorizontal(-1)) {
                        if (moveVertical(y)) {
                            moveVertical(y2);
                        }
                    }
                }
            } else {
                moveHorizontal(1);
            }
        } else {
            random = generateRandom2();

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

    private static boolean moveHorizontal(int x) {
        if (NoEntry(0, x)) {
            current.removeEntityFromTile(rat);
            tiles[curY][curX].getEntitiesOnTile().remove(rat);
            tiles[curY][curX + x].addEntityToTile(rat);

            rat.setCurrentPosX(curX + x);
            rat.setCurrentPosY(curY);

            if (x == -1) {
                rat.setRotatedImage(rat.getLeftImage());
                rat.setDirection(Rat.Direction.LEFT);
            } else {
                rat.setRotatedImage(rat.getRightImage());
                rat.setDirection(Rat.Direction.RIGHT);
            }
            return false;
        }

        return true;
    }

    private static boolean moveVertical(int y) {
        if (NoEntry(y, 0)) {
            current.removeEntityFromTile(rat);
            tiles[curY][curX].getEntitiesOnTile().remove(rat);
            tiles[curY + y][curX].addEntityToTile(rat);

            rat.setCurrentPosX(curX);
            rat.setCurrentPosY(curY + y);

            if (y == -1) {
                rat.setRotatedImage(rat.getUpImage());
                rat.setDirection(Rat.Direction.UP);
            } else {
                rat.setRotatedImage(rat.getDownImage());
                rat.setDirection(Rat.Direction.DOWN);
            }
            return false;

        }
        return true;
    }

    private static boolean NoEntry(int y, int x) {
        if (tiles[curY + y][curX + x].isWalkable()) {
            for (Entity entity : tiles[curY + y][curX + x].getEntitiesOnTile()) {
                if (entity.getEntityType() == Entity.EntityType.ITEM) {
                    Item item = (Item) entity;
                    if (item.getType() == Item.TYPE.NO_ENTRY) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

}
