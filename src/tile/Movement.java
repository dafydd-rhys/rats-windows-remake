package tile;

import entity.Entity;
import entity.Item;
import entity.rat.Rat;
import java.util.Random;
import main.level.Level;

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

    private static int generateRandom(int count) {
        return new Random().nextInt((count) + 1);
    }

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
                    rat.setDirection(Rat.Direction.LEFT);
                } else {
                    rat.setRotatedImage(rat.getRightImage());
                    rat.setDirection(Rat.Direction.RIGHT);
                }
                return false;
            } else {
                setRatsDirection();
            }
        }

        return true;
    }

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
                    rat.setDirection(Rat.Direction.UP);
                } else {
                    rat.setRotatedImage(rat.getDownImage());
                    rat.setDirection(Rat.Direction.DOWN);
                }
                return false;
            } else {
                setRatsDirection();
            }
        }
        return true;
    }

    private static void setRatsDirection() {
        if (rat.getDirection() == Rat.Direction.UP) {
            rat.setRotatedImage(rat.getDownImage());
            rat.setDirection(Rat.Direction.DOWN);
            rat.setCurrentPosY(curY + 1);
        } else if (rat.getDirection() == Rat.Direction.DOWN) {
            rat.setRotatedImage(rat.getUpImage());
            rat.setDirection(Rat.Direction.UP);
            rat.setCurrentPosY(curY - 1);
        } else if (rat.getDirection() == Rat.Direction.LEFT) {
            rat.setRotatedImage(rat.getRightImage());
            rat.setDirection(Rat.Direction.RIGHT);
            rat.setCurrentPosX(curX + 1);
        } else if (rat.getDirection() == Rat.Direction.RIGHT) {
            rat.setRotatedImage(rat.getLeftImage());
            rat.setDirection(Rat.Direction.LEFT);
            rat.setCurrentPosX(curX - 1);
        }
    }

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
