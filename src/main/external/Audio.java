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

/**
 * Main
 *
 * @author Dafydd -Rhys Maund (2003900)
 * @author Gareth Wade (1901805)
 */
public class Audio {

    /** */
    private static float music = 50f;
    /** */
    private static float effects = 50f;
    /** */
    private static final float muted = -50f;
    /** */
    private static float musicVolume = -50f + (music * 0.50f);
    /** */
    private static float effectsVolume = -50f + (music * 0.50f);
    /** */
    private static boolean musicMuted = false;
    /** */
    private static boolean effectsMuted = false;
    /** */
    private static final File clickPath = new File(System.getProperty("user.dir") + "\\src\\resources\\audio\\menu\\click.wav");
    /** */
    private static final File musicPath = new File(System.getProperty("user.dir") + "\\src\\resources\\audio\\menu\\music.wav");
    /** */
    private static final String settingsPath = System.getProperty("user.dir") + "\\src\\resources\\config\\settings.txt";
    /** */
    private static Clip musicClip = null;
    /** */
    private static Clip clickEffect = null;

    /**
     * Play music.
     *
     * @throws IOException                   the io exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws LineUnavailableException      the line unavailable exception
     */
    public static void playMusic() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
        musicClip = AudioSystem.getClip();
        musicClip.open(audio);

        if (musicVolume > muted && !musicMuted) {
            ((FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(musicVolume);
            musicClip.start();
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Play game effect.
     *
     * @param path the path
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     * @throws IOException                   the io exception
     * @throws LineUnavailableException      the line unavailable exception
     */
    public static void playGameEffect(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (effectsVolume > muted && !effectsMuted) {
            File effectPath = new File(path);
            AudioInputStream audio = AudioSystem.getAudioInputStream(effectPath);
            Clip clickEffect = AudioSystem.getClip();
            clickEffect.open(audio);
            ((FloatControl) clickEffect.getControl(FloatControl.Type.MASTER_GAIN)).setValue(effectsVolume);
            clickEffect.start();
        }
    }

    /**
     * Click effect.
     *
     * @throws LineUnavailableException      the line unavailable exception
     * @throws IOException                   the io exception
     * @throws UnsupportedAudioFileException the unsupported audio file exception
     */
    public static void clickEffect() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (effectsVolume > muted && !effectsMuted) {
            AudioInputStream audio = AudioSystem.getAudioInputStream(clickPath);
            clickEffect = AudioSystem.getClip();
            clickEffect.open(audio);
            ((FloatControl) clickEffect.getControl(FloatControl.Type.MASTER_GAIN)).setValue(effectsVolume);
            clickEffect.start();
        }
    }

    /**
     * Sets music.
     *
     * @param volume the volume
     * @throws IOException the io exception
     */
    public static void setMusic(float volume) throws IOException {
        Audio.music = volume;
        musicVolume = -50f + (music * 0.50f);
        if(!musicMuted) {
            ((FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(musicVolume);
        }
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
    public static void setEffects(float volume) throws IOException {
        Audio.effects = volume;
        effectsVolume = -50f + (effects * 0.50f);
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
     * Mute music.
     */
    public static void muteMusic() {
        if (musicVolume > muted) {
            ((FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-80f);
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
//Needs to be changed
    public static void muteEffects() {
        if (effectsVolume > muted) {
            ((FloatControl) clickEffect.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-80f);
            effectsMuted = !effectsMuted;
        }
    }

    /**
     * Resume effects.
     */
//Needs to be changed
    public static void resumeEffects() {
        ((FloatControl) clickEffect.getControl(FloatControl.Type.MASTER_GAIN)).setValue(effectsVolume);
        effectsMuted = !effectsMuted;
    }

    /**
     * Is music muted boolean.
     *
     * @return the boolean
     */
    public static boolean isMusicMuted() {return musicMuted;}

    /**
     * Is effects muted boolean.
     *
     * @return the boolean
     */
    public static boolean isEffectsMuted() {return effectsMuted;}

    /**
     * Gets values.
     *
     * @throws IOException the io exception
     */
    public static void getValues() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(settingsPath));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(":");
            if (Objects.equals(split[0], "musicVolume")) {
                music = Float.parseFloat(split[1]);
                musicVolume = -50f + (music * 0.50f);
            } else if (Objects.equals(split[0], "effectsVolume")) {
                effects = Float.parseFloat(split[1]);
                effectsVolume = -50f + (effects * 0.50f);
            }
        }
    }

    /**
     * Is muted double.
     *
     * @param sound the sound
     * @return the double
     */
    public static double isMuted(String sound) {
        if(Objects.equals(sound, "music")) {
            if(musicMuted) {
                return 0.2;
            } else { return 1; }
        } else if(Objects.equals(sound, "effects")) {
            if(effectsMuted) {
                return 0.2;
            } else { return 1; }
        } else { return 0; }
    }

    private static void writeValues() throws IOException {
        PrintWriter writer = new PrintWriter(settingsPath, StandardCharsets.UTF_8);

        writer.println("musicVolume:" + music);
        writer.println("effectsVolume:" + effects);
        writer.close();
    }

}
