package entity.rat;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * The type Rat sprites.
 */
public class RatSprites {

    /**
     * The constant upMale.
     */
    public static final Image upMale = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/male-rat.png");
    /**
     * The constant rightMale.
     */
    public static Image rightMale;
    /**
     * The constant downMale.
     */
    public static Image downMale;
    /**
     * The constant leftMale.
     */
    public static Image leftMale;

    /**
     * The constant upMaleSterilised.
     */
    public static final Image upMaleSterilised = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/sterilised-male-rat.png");
    /**
     * The constant downMaleSterilised.
     */
    public static Image downMaleSterilised;
    /**
     * The constant leftMaleSterilised.
     */
    public static Image leftMaleSterilised;
    /**
     * The constant rightMaleSterilised.
     */
    public static Image rightMaleSterilised;

    /**
     * The constant upFemale.
     */
    public static final Image upFemale = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/female-rat.png");
    /**
     * The constant rightFemale.
     */
    public static Image rightFemale;
    /**
     * The constant downFemale.
     */
    public static Image downFemale;
    /**
     * The constant leftFemale.
     */
    public static Image leftFemale;

    /**
     * The constant upFemaleSterilised.
     */
    public static final Image upFemaleSterilised = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/sterilised-female-rat.png");
    /**
     * The constant downFemaleSterilised.
     */
    public static Image downFemaleSterilised;
    /**
     * The constant leftFemaleSterilised.
     */
    public static Image leftFemaleSterilised;
    /**
     * The constant rightFemaleSterilised.
     */
    public static Image rightFemaleSterilised;

    /**
     * The constant upBaby.
     */
    public static final Image upBaby = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/baby-rat.png");
    /**
     * The constant downBaby.
     */
    public static Image downBaby;
    /**
     * The constant leftBaby.
     */
    public static Image leftBaby;
    /**
     * The constant rightBaby.
     */
    public static Image rightBaby;

    /**
     * The constant upDeath.
     */
    public static final Image upDeath = new Image(System.getProperty("user.dir") +
            "/src/resources/images/game/entities/death-rat.png");
    /**
     * The constant downDeath.
     */
    public static Image downDeath;
    /**
     * The constant leftDeath.
     */
    public static Image leftDeath;
    /**
     * The constant rightDeath.
     */
    public static Image rightDeath;

    /**
     * Load.
     */
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

        rightDeath = rotate(upDeath, 90);
        downDeath = rotate(upDeath, 180);
        leftDeath = rotate(upDeath, 270);
    }

    /**
     * Rotate image.
     *
     * @param img   the img
     * @param angle the angle
     * @return the image
     */
    public static Image rotate(Image img, int angle) {
        ImageView iv = new ImageView(img);
        iv.setRotate(angle);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        return iv.snapshot(params, null);
    }

}
