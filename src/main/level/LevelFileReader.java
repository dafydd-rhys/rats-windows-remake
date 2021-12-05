package main.level;

import entity.Item;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import main.Resources;

/**
 * LevelFileReader.
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski (857847)
 * @author Maurice Petersen (2013396)
 */
public class LevelFileReader {

    /**
     * Hashmap of the time to generate each item.
     */
    private final HashMap<Item.TYPE, Integer> timeToGenerate = new HashMap<>();
    /**
     * 2D array of the level.
     */
    private char[][] level;
    /**
     * The directory of the level.
     */
    private final File lvlDirectory;
    /**
     * The expected time to complete the level.
     */
    private int expectedTime;
    /**
     * The maximum number of rats allowed on the game board before the player loses.
     */
    private int maxRats;
    /**
     * The size of the level's X axis.
     */
    private int sizeX = 0;
    /**
     * The size of the level's Y axis.
     */
    private int sizeY = 0;
    /**
     * True if loading game save.
     */
    private final boolean save;
    /**
     * The amount of items.
     */
    private static final int AMOUNT_OF_ITEMS = 8;


    /**
     * Instantiates a new Level file reader.
     *
     * @param paramLevel the level
     * @param isSave true if reading a save file
     * @throws IOException the io exception
     */
    public LevelFileReader(final int paramLevel, final boolean isSave) throws IOException {
        this.lvlDirectory = Resources.getLevel(paramLevel);
        this.save = isSave;
        loadLevel();
    }

    /**
     * Get level char [ ] [ ].
     *
     * @return the char [ ] [ ]
     */
    public char[][] getLevel() {
        return level;
    }

    /**
     * Gets size x.
     *
     * @return the size x
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Gets size y.
     *
     * @return the size y
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Gets expected time.
     *
     * @return the expected time
     */
    public int getExpectedTime() {
        return expectedTime;
    }

    /**
     * Gets max rats.
     *
     * @return the max rats
     */
    public int getMaxRats() {
        return maxRats;
    }

    /**
     * Gets time to generate.
     *
     * @return the time to generate
     */
    public HashMap<Item.TYPE, Integer> getTimeToGenerate() {
        return timeToGenerate;
    }

    /**
     * @throws IOException Signals that an I/O exception to some sort has occurred
     */
    private void loadLevel() throws IOException {
        level = readFile(lvlDirectory);
    }

    /**
     * @param file the file to be read
     * @return array of ??
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has
     *                               failed
     */
    private char[][] readFile(final File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        char[][] array;

        String[] split = scanner.next().split(",");
        sizeX = Integer.parseInt(split[0].substring(0, 0) + split[0].substring(1));
        sizeY = Integer.parseInt(split[1].substring(0, 2));
        maxRats = scanner.nextInt();
        expectedTime = scanner.nextInt();

        for (int i = 0; i < AMOUNT_OF_ITEMS; i++) {
            split = scanner.next().split(",");
            StringBuilder amount = new StringBuilder(split[1]);
            timeToGenerate.put(getItem(split[0].substring(0, 0) + split[0].substring(1)),
                    Integer.parseInt(String.valueOf(amount.deleteCharAt(amount.length() - 1))));
        }
        scanner.nextLine();
        
        array = new char[sizeY][sizeX];
        for (int row = 0; scanner.hasNextLine() && row < sizeY; row++) {
            char[] chars = scanner.nextLine().toCharArray();
            for (int i = 0; i < sizeX; i++) {
                if (save) {
                    if (chars[i] == 'M' || chars[i] == 'F') {
                        array[row][i] = 'P';
                    } else if (chars[i] == 'N' || chars[i] == 'K') {
                        array[row][i] = 'T';
                    } else {
                        array[row][i] = chars[i];
                    }
                } else {
                    array[row][i] = chars[i];
                }
            }
        }
        return array;
    }

    /**
     * @param item item
     * @return the item
     */
    private Item.TYPE getItem(final String item) {
        return switch (item) {
            case "Bomb" -> Item.TYPE.BOMB;
            case "Gas" -> Item.TYPE.GAS;
            case "MaleChange" -> Item.TYPE.MALE_CHANGE;
            case "FemaleChange" -> Item.TYPE.FEMALE_CHANGE;
            case "NoEntry" -> Item.TYPE.NO_ENTRY;
            case "Poison" -> Item.TYPE.POISON;
            case "DeathRat" -> Item.TYPE.DEATH_RAT;
            case "Sterilisation" -> Item.TYPE.STERILISATION;
            default -> null;
        };
    }

}
