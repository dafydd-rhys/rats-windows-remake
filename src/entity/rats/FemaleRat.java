package entity.rats;

import javafx.scene.image.Image;

public class FemaleRat extends Rat {

    public FemaleRat(boolean isFemale) {
        super(isFemale);
        image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/female-rat.png");
    }

}
