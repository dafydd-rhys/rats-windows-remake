package main.level;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.image.ImageView;
import player.Inventory.Inventory;
import player.Player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * LevelSave.java
 *
 * Saves the current game state to a text file.
 *
 * @author Maurice Petersen (2013396)
 */
public class LevelSave {

    private final String saveDir;
    private final Level level;
    private final String player;

    public LevelSave(Level currentLevel) {
        this.level = currentLevel;
        this.player = Player.getPlayerName();
        this.saveDir = "src/resources/config/saves/save-" + player + ".txt";

    }

    /**
     * Write level data to file.
     * @throws IOException
     */
    private void writeFile() throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        // Level number
        lines.add(String.valueOf(Level.getCurrentLevel()));

        // Current tick
        lines.add(String.valueOf(level.getCurrentTick()));

        // Current score
        lines.add(String.valueOf(Level.getScore()));

        // Rats in current level
        StringJoiner joiner = new StringJoiner(",");
        for (Rat rat: level.getRats()) {
            joiner.add(rat.toString());
        }
        lines.add(joiner.toString());

        // Items placed on level
        joiner = new StringJoiner(",");
        for (Item item: level.getItems()) {
            joiner.add(item.toString());
        }
        lines.add(joiner.toString());

        // Items in player inventory
        joiner = new StringJoiner(",");
        for (Stack<HashMap<Item, ImageView>> stack : Inventory.getItems()) {
            for (HashMap<Item, ImageView> hashMap : stack) {
                for (Map.Entry<Item, ImageView> entry : hashMap.entrySet()) {
                    if (!entry.getValue().isDisable()) {
                        joiner.add(entry.getKey().toString());
                    }
                }
            }
        }
        lines.add(joiner.toString());

        Path file = Paths.get(saveDir);
        Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void save() {
        try {
            this.writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
