package main.level;

import entity.Item;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * LevelFileReader
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Dawid Wisniewski (857847)
 * @author Maurice Petersen (2013396)
 */
public class LevelFileReader {

    /** */
    private final HashMap<Item.TYPE, Integer> timeToGenerate = new HashMap<>();
    /** */
    private char[][] level;
    /** */
    private char[][] spawns;
    /** */
    private final String lvlDirectory;
    /** */
    private final String spawnDirectory;
    /** */
    private int expectedTime;
    /** */
    private int maxRats;
    /** */
    private int sizeX = 0;
    /** */
    private int sizeY = 0;

    /**
     * Instantiates a new Level file reader.
     *
     * @param level the level
     * @throws IOException the io exception
     */
    public LevelFileReader(int level, boolean isSave) throws IOException {
        this.lvlDirectory = "src/resources/config/levels/level" + level + ".txt";
        this.spawnDirectory= "src/resources/config/spawns/level" + level + "-spawns.txt";
        loadLevel();

        if(!isSave) {
            loadSpawns();
        }
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
     * Get spawns char [ ] [ ].
     *
     * @return the char [ ] [ ]
     */
    public char[][] getSpawns() {
        return spawns;
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
     *
     *
     * @throws IOException
     */
    private void loadLevel() throws IOException {
        level = readFile(lvlDirectory);
    }

    /**
     *
     *
     * @throws FileNotFoundException
     */
    private void loadSpawns() throws FileNotFoundException {
        spawns = readFile(spawnDirectory);
    }

    /**
     *
     *
     * @param dir
     * @return
     * @throws FileNotFoundException
     */
    private char[][] readFile(String dir) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(dir));
        char[][] array;

        if (dir.equals(lvlDirectory)) {
            String[] split = scanner.next().split(",");
            sizeX = Integer.parseInt(split[0].substring(0, 0) + split[0].substring(1));
            sizeY = Integer.parseInt(split[1].substring(0, 2));
            maxRats = scanner.nextInt();
            expectedTime = scanner.nextInt();

            for (int i = 0; i < 8; i ++) {
                split = scanner.next().split(",");
                StringBuilder amount = new StringBuilder(split[1]);
                timeToGenerate.put(getItem(split[0].substring(0, 0) + split[0].substring(1)),
                        Integer.parseInt(String.valueOf(amount.deleteCharAt(amount.length() - 1))));
            }
            scanner.nextLine();
        }

        array = new char[sizeY][sizeX];
        for (int row = 0; scanner.hasNextLine() && row < sizeY; row++) {
            char[] chars = scanner.nextLine().toCharArray();
            for (int i = 0; i < sizeX && i < chars.length; i++) {
                array[row][i] = chars[i];
            }
        }
        return array;
    }

    /**
     *
     *
     * @param item
     * @return
     */
    private Item.TYPE getItem(String item) {
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
