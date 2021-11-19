package main;

import javax.swing.*;

/**
 * TickTimer.java
 *
 * @author Dafydd-Rhys Maund
 */
public class TickTimer {

    /**
     * the elapsed time of this timer (seconds).
     */
    private static int elapsedTime;
    /**
     * amount of millis in a second.
     */
    private static final int milliseconds = 1000;

    /**
     * this method simply represents the timer, it works in intervals
     * of 1000 milliseconds or 1 second.
     */
    private static final Timer timer = new Timer(milliseconds, e -> {
        elapsedTime += milliseconds / 1000;
        System.out.println(elapsedTime + "-tick");
    });

    /**
     * Start timer.
     */
    public static void start() {
        timer.start();
    }

    /**
     * Stop timer.
     */
    public static void stop() {
        timer.stop();
    }

    /**
     * restart timer.
     */
    public static void restart() {
        timer.stop();
        timer.start();
    }

}