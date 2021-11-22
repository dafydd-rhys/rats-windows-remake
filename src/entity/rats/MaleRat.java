package entity.rats;

import javafx.scene.image.Image;

public class MaleRat extends Rat {

    public MaleRat(boolean isFemale) {
        super(isFemale);
        image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/male-rat.png");
    }

}
