package edu.hitsz.application;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统一管理背景音乐和音效播放。
 */
public final class SoundManager {

    private static final SoundManager INSTANCE = new SoundManager();
    private static final String BACKGROUND_KEY = "background";
    private static final String BOSS_KEY = "boss";

    private final Map<String, Clip> loopClips = new ConcurrentHashMap<>();

    private SoundManager() {
    }

    public static SoundManager getInstance() {
        return INSTANCE;
    }

    public void playBackgroundLoop(Difficulty difficulty) {
        playLoop(BACKGROUND_KEY, "src/videos/bgm.wav");
    }

    public void playBossLoop() {
        playLoop(BOSS_KEY, "src/videos/bgm_boss.wav");
    }

    public void stopBackgroundLoop() {
        stopLoop(BACKGROUND_KEY);
    }

    public void stopBossLoop() {
        stopLoop(BOSS_KEY);
    }

    public void stopAll() {
        stopBackgroundLoop();
        stopBossLoop();
    }

    public void playBulletHit() {
        playOnce("src/videos/bullet_hit.wav");
    }

    public void playBombExplosion() {
        playOnce("src/videos/bomb_explosion.wav");
    }

    public void playSupplySound() {
        playOnce("src/videos/get_supply.wav");
    }

    public void playGameOverSound() {
        playOnce("src/videos/game_over.wav");
    }

    private synchronized void playLoop(String key, String path) {
        stopLoop(key);
        Clip clip = openClip(path);
        if (clip == null) {
            return;
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
        loopClips.put(key, clip);
    }

    private synchronized void stopLoop(String key) {
        Clip clip = loopClips.remove(key);
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.close();
        }
    }

    private void playOnce(String path) {
        new Thread(() -> {
            Clip clip = openClip(path);
            if (clip == null) {
                return;
            }
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                }
            });
            clip.start();
        }, "sound-once-player").start();
    }

    private Clip openClip(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("Audio file not found: " + path);
            return null;
        }
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }
}

