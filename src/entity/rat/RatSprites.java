package entity.rat;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class RatSprites {

    public static final Image upMale = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/male-rat.png");
    public static Image rightMale;
    public static Image downMale;
    public static Image leftMale;

    public static final Image upMaleSterilised = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/sterilised-male-rat.png");
    public static Image downMaleSterilised;
    public static Image leftMaleSterilised;
    public static Image rightMaleSterilised;

    public static final Image upFemale = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/female-rat.png");
    public static Image rightFemale;
    public static Image downFemale;
    public static Image leftFemale;

    public static final Image upFemaleSterilised = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/sterilised-female-rat.png");
    public static Image downFemaleSterilised;
    public static Image leftFemaleSterilised;
    public static Image rightFemaleSterilised;

    public static final Image upBaby = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/baby-rat.png");
    public static Image downBaby;
    public static Image leftBaby;
    public static Image rightBaby;

    public static void load() {
        rightMale = rotate(upMale, 90);
        downMale = rotate(upMale, 180);
        leftMale = rotate(upMale, 270);

        rightMaleSterilised = rotate(upMaleSterilised, 90);
        downMaleSterilised = rotate(upMaleSterilised, 180);
        leftMaleSterilised = rotate(upMaleSterilised, 270);

        rightFemale = rotate(upFemale, 90);
        downFemale = rotate(upFemale, 180);
        leftFemale = rotate(upFemale, 270);

        rightFemaleSterilised = rotate(upFemaleSterilised, 90);
        downFemaleSterilised = rotate(upFemaleSterilised, 180);
        leftFemaleSterilised = rotate(upFemaleSterilised, 270);

        rightBaby = rotate(upBaby, 90);
        downBaby = rotate(upBaby, 180);
        leftBaby = rotate(upBaby, 270);
    }

    public static Image rotate(Image img, int angle) {
        ImageView iv = new ImageView(img);
        iv.setRotate(angle);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        return iv.snapshot(params, null);
    }

}
