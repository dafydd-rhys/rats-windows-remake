package main.level;

import entity.Item;
import entity.weapon.Bomb;
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

    private final int size = 20;
    private final char[][] level = new char[size][size];
    private final char[][] spawns = new char[size][size];
    private final String lvlDirectory;
    private final String spawnDirectory;
    private int expectedTime;
    private int maxRats;
    private final HashMap<Item.TYPE, Integer> timeToGenerate = new HashMap<>();

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

        for (int row = 0; scanner.hasNextLine() && row < size; row++) {
            char[] chars = scanner.nextLine().toCharArray();
            for (int i = 0; i < size && i < chars.length; i++) {
                array[row][i] = chars[i];
            }
        }

        if (dir.equals(lvlDirectory)) {
            maxRats = scanner.nextInt();
            expectedTime = scanner.nextInt() * 1000;
            for (int i = 0; i < 8; i ++) {
                timeToGenerate.put(getItem(scanner.next()), scanner.nextInt());
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
