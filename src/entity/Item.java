package entity;

import entity.rat.Rat;
import java.util.ArrayList;
import javafx.scene.image.Image;
import main.level.Level;
import tile.Movement;

/**
 * Item
 *
 * @author Dafydd-Rhys Maund (2003900)
 * @author Dawid Wisniewski
 * @author Gareth Wade (1901805)
 */
public abstract class Item extends Entity {

    private boolean friendlyFire;
    private boolean canBeAttacked;
    private Image image;
    private TYPE type;
    private int yOffset;
    private int range;

    public enum TYPE {
        BOMB(),
        GAS(),
        DEATH_RAT(),
        FEMALE_CHANGE(),
        MALE_CHANGE(),
        NO_ENTRY(),
        POISON(),
        STERILISATION()
    }

    public abstract void activate();

    public class Death extends Rat {

        int moveTick = 2;
        int currentTick = 0;

        public Death(Gender gender, boolean isAdult) {
            super(gender, isAdult);
            setEntityType(EntityType.ITEM);
            setEntityName("DeathRat");
            setImage(new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/death-rat.png"));
            setHp(8);
            setDamage(5);
            setRange(2);
            setFriendlyFire(true);
            setCanBeAttacked(false);
            setType(TYPE.DEATH_RAT);
            setOffsetY(1);
        }

        public void activate() {
            currentTick++;

            if (currentTick % moveTick == 0) {
                move();
            }
        }

        private void move() {
            Movement.tiles = Level.getTiles();
            Movement.rat = this;
            Movement.current = Level.getTiles()[getCurrentPosY()][getCurrentPosX()];
            Movement.curX = getCurrentPosX();
            Movement.curY = getCurrentPosY();

            if (this.getDirection() == Rat.Direction.LEFT) {
                Movement.tryHorizontal(-1, 1);
            } else if (this.getDirection() == Rat.Direction.RIGHT) {
                Movement.tryHorizontal(1, -1);
            } else if (this.getDirection() == Rat.Direction.UP) {
                Movement.tryVertical(-1, 1);
            } else if (this.getDirection() == Rat.Direction.DOWN) {
                Movement.tryVertical(1, -1);
            }
            checkForOpps();
        }

        private void checkForOpps() {
            ArrayList<Entity> entities = Level.getTiles()[this.getCurrentPosY()][this.getCurrentPosX()].getEntitiesOnTile();

            if (!entities.isEmpty()) {
                for (int k = 0; k < entities.size(); k++) {
                    if (entities.get(k).getEntityType() == EntityType.RAT){
                        Rat targetRat = (Rat) entities.get(k);
                        inflictDamage(getDamage(), targetRat);
                        setHp(getHp() - 1);

                        if (targetRat.getHp() <= 0) {
                            targetRat.kill();
                        }

                        if (getHp() <= 0) {
                            kill();
                        }
                    }
                }
            }
        }

    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getCurrentPosX() {
        return this.currentPosX;
    }

    @Override
    public void setCurrentPosX(int currentPosX) {
        this.currentPosX = currentPosX;
    }

    @Override
    public int getCurrentPosY() {
        return this.currentPosY;
    }

    @Override
    public void setCurrentPosY(int currentPosY) {
        this.currentPosY = currentPosY;
    }

    @Override
    public String getEntityName() {
        return this.entityName;
    }

    @Override
    protected void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public Image getImage() {
        return image;
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public boolean canBeAttacked() {
        return canBeAttacked;
    }

    public void setCanBeAttacked(boolean canBeAttacked) {
        this.canBeAttacked = canBeAttacked;
    }

    public void setOffsetY(int yOffset) {
        this.yOffset = yOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }

}
