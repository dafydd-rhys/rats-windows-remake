package main.external;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.Resources;

/**
 * Audio.java - used to play sounds in the program.
 *
 * All sounds are taken from https://freesound.org/ and are under Creative Commons
 *
 * EXAMPLE sound (example.wav) by SomeUser taken from: https://freesound.org/people/kMoon/sounds/90797/
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */
public class Audio {

    /**
     * represent default value for volume.
     */
    private static final float DEFAULT_VALUE = 50f;
    /**
     * The music value produced by the slider in settings.
     */
    private static float music = DEFAULT_VALUE;
    /**
     * The effects value produced by the slider in settings.
     */
    private static float effects = DEFAULT_VALUE;
    /**
     * represent value for 1% volume.
     */
    private static final float POINT_OF_VOLUME = 0.5f;
    /**
     * Value which represents fully muted sound.
     */
    private static final float FULLY_MUTED = -80f;
    /**
     * Value which represents muted.
     */
    private static final float MUTED = -50f;
    /**
     * The calculated volume of music.
     */
    private static float musicVolume = MUTED + (music * POINT_OF_VOLUME);
    /**
     * The calculated volume of effects.
     */
    private static float effectsVolume = MUTED + (music * POINT_OF_VOLUME);
    /**
     * Whether music is currently muted.
     */
    private static boolean musicMuted = false;
    /**
     * Whether effects is currently muted.
     */
    private static boolean effectsMuted = false;
    /**
     * The clip used to play music.
     */
    private static Clip musicClip = null;
    /**
     * The clip used to play effects.
     */
    private static Clip clickEffect = null;

    /**
     * Plays music.
     *
     * @throws IOException                   the io exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     */
    public static void playMusic() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audio = AudioSystem.getAudioInputStream(Resources.getMenuAudio("music"));
        musicClip = AudioSystem.getClip();
        musicClip.open(audio);

        //makes sure music is not muted
        if (musicVolume > MUTED && !musicMuted) {
            ((FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(musicVolume);
            musicClip.start();
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Play game effect.
     *
     * @param file the file to be played.
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws IOException                   the io exception
     * @throws LineUnavailableException      the line unavailable exception
     */
    public static void playGameEffect(final File file) throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        if (effectsVolume > MUTED && !effectsMuted) {
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip click = AudioSystem.getClip();
            click.open(audio);
            ((FloatControl) click.getControl(FloatControl.Type.MASTER_GAIN)).setValue(effectsVolume);
            click.start();
        }
    }

    /**
     * Plays the click effect - used in menu and in-game,
     * represents user click or dragging something.
     *
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     */
    public static void clickEffect() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        //makes sure effects isn't muted
        if (effectsVolume > MUTED && !effectsMuted) {
            AudioInputStream audio = AudioSystem.getAudioInputStream(Resources.getMenuAudio("click"));
            clickEffect = AudioSystem.getClip();
            clickEffect.open(audio);
            ((FloatControl) clickEffect.getControl(FloatControl.Type.MASTER_GAIN)).setValue(effectsVolume);
            clickEffect.start();
        }
    }

    /**
     * Sets music volume.
     *
     * @param volume the volume
     * @throws IOException the io exception
     */
    public static void setMusic(final float volume) throws IOException {
        Audio.music = volume;
        musicVolume = MUTED + (music * POINT_OF_VOLUME);
        if (!musicMuted) {
            ((FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(musicVolume);
        }

        //writes new values to .txt
        writeValues();
    }

    /**
     * Gets music.
     *
     * @return the music
     */
    public static float getMusic() {
        return music;
    }

    /**
     * Sets effects.
     *
     * @param volume the volume
     * @throws IOException the io exception
     */
    public static void setEffects(final float volume) throws IOException {
        Audio.effects = volume;
        effectsVolume = MUTED + (effects * POINT_OF_VOLUME);
        writeValues();
    }

    /**
     * Gets effects.
     *
     * @return the effects
     */
    public static float getEffects() {
        return effects;
    }

    /**
     * Mutes music.
     */
    public static void muteMusic() {
        if (musicVolume > MUTED) {
            ((FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(FULLY_MUTED);
            musicMuted = !musicMuted;
        }
    }

    /**
     * Resume music.
     */
    public static void resumeMusic() {
        ((FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(musicVolume);
        musicMuted = !musicMuted;
    }

    /**
     * Mute effects.
     */
    public static void muteEffects() {
        if (effectsVolume > MUTED) {
            ((FloatControl) clickEffect.getControl(FloatControl.Type.MASTER_GAIN)).setValue(FULLY_MUTED);
            effectsMuted = !effectsMuted;
        }
    }

    /**
     * Resume effects.
     */
    public static void resumeEffects() {
        ((FloatControl) clickEffect.getControl(FloatControl.Type.MASTER_GAIN)).setValue(effectsVolume);
        effectsMuted = !effectsMuted;
    }

    /**
     * Is music muted boolean.
     *
     * @return the boolean
     */
    public static boolean isMusicMuted() {
        return musicMuted;
    }

    /**
     * Is effects muted boolean.
     *
     * @return the boolean
     */
    public static boolean isEffectsMuted() {
        return effectsMuted;
    }

    /**
     * Gets values.
     *
     * @throws IOException the io exception
     */
    public static void getValues() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Resources.getSettings()));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(":");
            if (Objects.equals(split[0], "musicVolume")) {
                music = Float.parseFloat(split[1]);
                musicVolume = MUTED + (music * POINT_OF_VOLUME);
            } else if (Objects.equals(split[0], "effectsVolume")) {
                effects = Float.parseFloat(split[1]);
                effectsVolume = MUTED + (effects * POINT_OF_VOLUME);
            }
        }
    }

    /**
     * Is muted double.
     *
     * @param sound the sound
     * @return the double
     */
    public static double isMuted(final String sound) {
        if (Objects.equals(sound, "music")) {
            if (musicMuted) {
                return 0.2;
            } else {
                return 1;
            }
        } else if (Objects.equals(sound, "effects")) {
            if (effectsMuted) {
                return 0.2;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    /**
     * Writes updated values to config file.
     *
     * @throws IOException caused by file not existing
     */
    private static void writeValues() throws IOException {
        PrintWriter writer = new PrintWriter(Resources.getSettings(), StandardCharsets.UTF_8);

        writer.println("musicVolume:" + music);
        writer.println("effectsVolume:" + effects);
        writer.close();
    }

}
