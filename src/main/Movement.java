package main;

import entity.rats.Rat;
import java.util.Random;
import tile.Tile;

public class Movement {

    private static int random;
    public static Rat rat;
    public static Tile[][] tiles;
    public static Tile current;
    public static int curX;
    public static int curY;

    public static void tryLeft() {
        random = generateRandom();

        if (moveLeft()) {
            if (random == 1) {
                if (moveUp()) {
                    if (moveDown()) {
                        if (moveRight()) {
                            System.out.println("couldn't move");
                        }
                    }
                }
            } else {
                if (moveDown()) {
                    if (moveUp()) {
                        if (moveRight()) {
                            System.out.println("couldn't move");
                        }
                    }
                }
            }
        }
    }

    public static void tryRight() {
        random = generateRandom();

        if (moveRight()) {
            if (random == 1) {
                if (moveUp()) {
                    if (moveDown()) {
                        if (moveLeft()) {
                            System.out.println("couldn't move");
                        }
                    }
                }
            } else {
                if (moveDown()) {
                    if (moveUp()) {
                        if (moveLeft()) {
                            System.out.println("couldn't move");
                        }
                    }
                }
            }
        }
    }

    public static void tryUp() {
        random = generateRandom();

        if (moveUp()) {
            if (random == 1) {
                if (moveLeft()) {
                    if (moveRight()) {
                        if (moveDown()) {
                            System.out.println("couldn't move");
                        }
                    }
                }
            } else {
                if (moveRight()) {
                    if (moveLeft()) {
                        if (moveDown()) {
                            System.out.println("couldn't move");
                        }
                    }
                }
            }
        }
    }

    public static void tryDown() {
        random = generateRandom();

        if (moveDown()) {
            if (random == 1) {
                if (moveLeft()) {
                    if (moveRight()) {
                        if (moveUp()) {
                            System.out.println("couldn't move");
                        }
                    }
                }
            } else {
                if (moveRight()) {
                    if (moveLeft()) {
                        if (moveUp()) {
                            System.out.println("couldn't move");
                        }
                    }
                }
            }
        }
    }

    private static boolean moveLeft() {
        if (tiles[curY][curX - 1].getType() != Tile.TYPE.GRASS) {
            current.removeEntityFromTile(rat);
            tiles[curY][curX - 1].addEntityToTile(rat);

            rat.setCurrentPosX(curX - 1);
            rat.setCurrentPosY(curY);

            rat.setRotatedImage(rat.getLeftImage());
            rat.setDirection(Rat.Direction.LEFT);
            return false;
        }

        return true;
    }

    private static boolean moveRight() {
        if (tiles[curY][curX + 1].getType() != Tile.TYPE.GRASS) {
            current.removeEntityFromTile(rat);
            tiles[curY][curX + 1].addEntityToTile(rat);

            rat.setCurrentPosX(curX + 1);
            rat.setCurrentPosY(curY);

            rat.setRotatedImage(rat.getRightImage());
            rat.setDirection(Rat.Direction.RIGHT);
            return false;
        }

        return true;
    }

    private static boolean moveUp() {
        if (tiles[curY - 1][curX].getType() != Tile.TYPE.GRASS) {
            current.removeEntityFromTile(rat);
            tiles[curY - 1][curX].addEntityToTile(rat);

            rat.setCurrentPosX(curX);
            rat.setCurrentPosY(curY - 1);

            rat.setRotatedImage(rat.getUpImage());
            rat.setDirection(Rat.Direction.UP);
            return false;
        }

        return true;
    }

    private static boolean moveDown() {
        if (tiles[curY + 1][curX].getType() != Tile.TYPE.GRASS) {
            current.removeEntityFromTile(rat);
            tiles[curY + 1][curX].addEntityToTile(rat);

            rat.setCurrentPosX(curX);
            rat.setCurrentPosY(curY + 1);

            rat.setRotatedImage(rat.getDownImage());
            rat.setDirection(Rat.Direction.DOWN);
            return false;
        }

        return true;
    }

    private static int generateRandom() {
        return new Random().nextInt((1) + 1);
    }

}