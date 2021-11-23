package entity.rats;

import entity.Entity;
import java.util.List;
import java.util.Random;
import javafx.scene.image.Image;

public class FemaleRat extends Rat {

    public FemaleRat(boolean isFemale) {
        super(isFemale);
        image = new Image(System.getProperty("user.dir") + "/src/resources/images/game/entities/female-rat.png");
        upImage = image;
        leftImage = Entity.rotate(image, 270);
        rightImage = Entity.rotate(image, 90);
        downImage = Entity.rotate(image, 180);

        List<Direction> values = List.of(Direction.values());
        direction = values.get(new Random().nextInt(values.size()));
    }

}
