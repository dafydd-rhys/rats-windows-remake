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
 * @author Dafydd-Rhys Maund (2003900)
 */
public class LevelFileReader {

    private final HashMap<Item.TYPE, Integer> timeToGenerate = new HashMap<>();
    private final int size = 20;
    private final char[][] level = new char[size][size];
    private final char[][] spawns = new char[size][size];
    private final String lvlDirectory;
    private final String spawnDirectory;

    private int expectedTime;
    private int maxRats;
    private int sizeX;
    private int sizeY;

    public LevelFileReader(int level) throws IOException {
        this.lvlDirectory = "src/resources/config/levels/level" + level + ".txt";
        this.spawnDirectory= "src/resources/config/spawns/level" + level + "-spawns.txt";
        loadLevel();
        loadSpawns();
    }

    public char[][] getLevel() {
        return level;
    }

    public char[][] getSpawns() {
        return spawns;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getExpectedTime() {
        return expectedTime;
    }

    public int getMaxRats() {
        return maxRats;
    }

    public HashMap<Item.TYPE, Integer> getTimeToGenerate() {
        return timeToGenerate;
    }

    private void loadLevel() throws IOException {
        readFile(level, lvlDirectory);
    }

    private void loadSpawns() throws FileNotFoundException {
        readFile(spawns, spawnDirectory);
    }

    private void readFile(char[][] array, String dir) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(dir));

        if (dir.equals(lvlDirectory)) {
            String[] split = scanner.next().split(",");
            sizeX = Integer.parseInt(split[0].substring(0, 0) + split[0].substring(1));
            sizeY = Integer.parseInt(split[1].substring(0, 2));
            maxRats = scanner.nextInt();
            expectedTime = scanner.nextInt();

            Level.rows = sizeY;
            Level.cols = sizeX;

            for (int i = 0; i < 8; i ++) {
                split = scanner.next().split(",");
                StringBuilder amount = new StringBuilder(split[1]);
                timeToGenerate.put(getItem(split[0].substring(0, 0) + split[0].substring(1)),
                        Integer.parseInt(String.valueOf(amount.deleteCharAt(amount.length() - 1))));
            }
            scanner.nextLine();
        }

        for (int row = 0; scanner.hasNextLine() && row < sizeY; row++) {
            System.out.println(row);
            char[] chars = scanner.nextLine().toCharArray();
            for (int i = 0; i < sizeX && i < chars.length; i++) {
                array[row][i] = chars[i];
            }
        }
    }

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
