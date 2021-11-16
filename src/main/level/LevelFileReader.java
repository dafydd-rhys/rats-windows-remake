package main.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    }

}
