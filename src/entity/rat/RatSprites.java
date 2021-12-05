package entity.rat;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.Resources;

/**
 * The type Rat sprites.
 *
 * Sprites created by Stefan.
 *
 * @author Dafydd-Rhys Maund
 */
public class RatSprites {

    /**
     * The constant upMale.
     */
    public static final Image UP_MALE = Resources.getEntityImage("male-rat");
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
    public static final Image UP_MALE_STERILISED = Resources.getEntityImage("sterilised-male-rat");
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
    public static final Image UP_FEMALE  = Resources.getEntityImage("female-rat");
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
    public static final Image UP_FEMALE_STERILISED = Resources.getEntityImage("sterilised-female-rat");
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
    public static final Image UP_BABY = Resources.getEntityImage("baby-rat");
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
    public static final Image UP_DEATH = Resources.getEntityImage("death-rat");
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
     * Angle to get image rotated right.
     */
    private static final int RIGHT = 90;
    /**
     * Angle to get image rotated down.
     */
    private static final int DOWN = 180;
    /**
     * Angle to get image rotated left.
     */
    private static final int LEFT = 270;

    /**
     * Load - when loaded create an image for every direction of all type of rats.
     */
    public static void load() {
        //rotates image in all directions - does for each image
        rightMale = rotate(UP_MALE, RIGHT);
        downMale = rotate(UP_MALE, DOWN);
        leftMale = rotate(UP_MALE, LEFT);

        rightMaleSterilised = rotate(UP_MALE_STERILISED, RIGHT);
        downMaleSterilised = rotate(UP_MALE_STERILISED, DOWN);
        leftMaleSterilised = rotate(UP_MALE_STERILISED, LEFT);

        rightFemale = rotate(UP_FEMALE, RIGHT);
        downFemale = rotate(UP_FEMALE, DOWN);
        leftFemale = rotate(UP_FEMALE, LEFT);

        rightFemaleSterilised = rotate(UP_FEMALE_STERILISED, RIGHT);
        downFemaleSterilised = rotate(UP_FEMALE_STERILISED, DOWN);
        leftFemaleSterilised = rotate(UP_FEMALE_STERILISED, LEFT);

        rightBaby = rotate(UP_BABY, RIGHT);
        downBaby = rotate(UP_BABY, DOWN);
        leftBaby = rotate(UP_BABY, LEFT);

        rightDeath = rotate(UP_DEATH, RIGHT);
        downDeath = rotate(UP_DEATH, DOWN);
        leftDeath = rotate(UP_DEATH, LEFT);
    }

    /**
     * Rotate image.
     *
     * @param img   the img
     * @param angle the angle
     * @return the image
     */
    public static Image rotate(final Image img, final int angle) {
        ImageView iv = new ImageView(img);
        iv.setRotate(angle);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        return iv.snapshot(params, null);
    }

}
