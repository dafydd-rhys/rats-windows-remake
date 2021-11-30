package main.level;

import entity.Item;
import entity.rat.Rat;
import javafx.scene.image.ImageView;
import player.Inventory.Inventory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class LevelSave {

    private String outputDirectory;
    private Level level;

    public LevelSave(Level currentLevel) {
        this.outputDirectory = "src/resources/config/save.txt";
        this.level = currentLevel;
    }

    public LevelSave() {
        this.outputDirectory = "src/resources/config/save.txt";
    }

    private void writeFile() throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        // Level number
        lines.add(String.valueOf(Level.getCurrentLevel()));

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

        Path file = Paths.get(outputDirectory);
        Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void save() {
        try {
            this.writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
