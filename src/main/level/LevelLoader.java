package main.level;

import entity.Item;
import entity.rat.Rat;
import entity.weapon.*;
import main.Resources;
import player.Player;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * LevelLoader.java
 * <p>
 * Reads data from a save file.
 *
 * @author Maurice Petersen (2013396)
 */
public class LevelLoader {

    private final File saveDir;
    private String player;
    private File levelDir;
    private Level level;
    private int currentLevel;
    private final ArrayList<Rat> ratSpawns;
    private final ArrayList<Item> itemSpawns;
    private final ArrayList<Item.TYPE> inventory;
    private int currentTick;
    private int score;

    public LevelLoader() throws IOException {
        this.player = Player.getPlayerName();
        this.saveDir = Resources.getSaves(player);
        this.ratSpawns = new ArrayList<>();
        this.itemSpawns = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.currentTick = 0;
        this.score = 0;

        loadLevel();
    }

    /**
     * Reads the whole save file.
     *
     * @throws IOException
     */
    private void loadLevel() throws IOException {
        readLevel();
        readRatSpawns();
        readItemSpawns();
        readInventoryItems();
    }

    /**
     * Reads the current level and current tick from the save file.
     *
     * @throws IOException
     */
    private void readLevel() throws IOException {
        Scanner scanner = new Scanner(saveDir);
        this.currentLevel = scanner.nextInt();
        this.currentTick = scanner.nextInt();
        this.score = scanner.nextInt();
        this.levelDir = Resources.getLevel(currentLevel);
        scanner.close();
    }

    /**
     * Reads the line in the save file regarding rat attributes.
     *
     * @throws IOException
     */
    private void readRatSpawns() throws IOException {
        String line = Files.readAllLines(Paths.get(saveDir.toURI())).get(3);
        Scanner scanner = new Scanner(line);

        if (scanner.hasNext()) {
            String[] ratSplit = scanner.next().split(",");
            for (String ratEntry : ratSplit) {
                Rat.Gender gender = null;
                boolean isAdult = false;
                boolean isSterilized = false;
                boolean isPregnant = false;
                Rat.Direction direction = null;

                if (ratEntry.contains("M")) {
                    gender = Rat.Gender.MALE;
                } else if (ratEntry.contains("F")) {
                    gender = Rat.Gender.FEMALE;
                }

                if (ratEntry.contains("A")) {
                    isAdult = true;
                }

                if (ratEntry.contains("S")) {
                    isSterilized = true;
                }

                if (ratEntry.contains("P")) {
                    isPregnant = true;
                }

                if (ratEntry.contains("L")) {
                    direction = Rat.Direction.LEFT;
                } else if (ratEntry.contains("R")) {
                    direction = Rat.Direction.RIGHT;
                } else if (ratEntry.contains("U")) {
                    direction = Rat.Direction.UP;
                } else if (ratEntry.contains("D")) {
                    direction = Rat.Direction.DOWN;
                }

                String[] ratCoords = ratEntry.split(":");
                int x = Integer.parseInt(ratCoords[1]);
                int y = Integer.parseInt(ratCoords[2]);

                Rat newRat = new Rat(gender, isAdult);
                newRat.setSterilised(isSterilized);
                newRat.setPregnant(isPregnant);
                newRat.setCurrentPosX(x);
                newRat.setCurrentPosY(y);
                newRat.setDirection(direction);

                this.ratSpawns.add(newRat);
            }
        }
        scanner.close();
    }

    /**
     * Reads the line in the save file regarding item attributes.
     *
     * @throws IOException
     */
    private void readItemSpawns() throws IOException {
        String line = Files.readAllLines(Paths.get(saveDir.toURI())).get(4);
        Scanner scanner = new Scanner(line);

        if (scanner.hasNext()) {
            String[] itemSplit = scanner.next().split(",");
            for (String itemEntry : itemSplit) {
                Item item = null;

                if (itemEntry.contains("B")) {
                    item = new Bomb();
                } else if (itemEntry.contains("D")) {
                    item = new DeathRat();
                } else if (itemEntry.contains("F")) {
                    item = new FemaleSexChange();
                } else if (itemEntry.contains("G")) {
                    item = new Gas();
                } else if (itemEntry.contains("M")) {
                    item = new MaleSexChange();
                } else if (itemEntry.contains("N")) {
                    item = new NoEntrySign();
                } else if (itemEntry.contains("P")) {
                    item = new Poison();
                } else if (itemEntry.contains("S")) {
                    item = new Sterilisation();
                }

                assert item != null;
                String[] itemCoords = itemEntry.split(":");
                item.setHp(Integer.parseInt(String.valueOf(itemCoords[0].charAt(1))));
                item.setCurrentPosX(Integer.parseInt(itemCoords[1]));
                item.setCurrentPosY(Integer.parseInt(itemCoords[2]));

                this.itemSpawns.add(item);
            }
        }

        scanner.close();
    }

    /**
     * Reads the line in the save file regarding inventory items.
     *
     * @throws IOException
     */
    private void readInventoryItems() throws IOException {
        String line = Files.readAllLines(Paths.get(saveDir.toURI())).get(5);
        Scanner scanner = new Scanner(line);

        if (scanner.hasNext()) {
            String[] invSplit = scanner.next().split(",");
            for (String invEntry : invSplit) {
                if (invEntry.contains("B")) {
                    inventory.add(Item.TYPE.BOMB);
                } else if (invEntry.contains("D")) {
                    inventory.add(Item.TYPE.DEATH_RAT);
                } else if (invEntry.contains("F")) {
                    inventory.add(Item.TYPE.FEMALE_CHANGE);
                } else if (invEntry.contains("G")) {
                    inventory.add(Item.TYPE.GAS);
                } else if (invEntry.contains("M")) {
                    inventory.add(Item.TYPE.MALE_CHANGE);
                } else if (invEntry.contains("N")) {
                    inventory.add(Item.TYPE.NO_ENTRY);
                } else if (invEntry.contains("P")) {
                    inventory.add(Item.TYPE.POISON);
                } else if (invEntry.contains("S")) {
                    inventory.add(Item.TYPE.STERILISATION);
                }
            }
        }
    }

    public static int getCurrentLevel() throws IOException {
        Scanner scanner = new Scanner(Resources.getSaves(Player.getPlayerName()));
        int currLevel = scanner.nextInt();
        scanner.close();
        return currLevel;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public ArrayList<Rat> getRatSpawns() {
        return ratSpawns;
    }

    public ArrayList<Item> getItemSpawns() {
        return itemSpawns;
    }

    public ArrayList<Item.TYPE> getInventory() {
        return inventory;
    }
}
