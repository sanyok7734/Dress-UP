package com.raccoonapps.worksimple.music;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

public class MainPlayer {

    private Context context;
    private static MainPlayer instance;
    private MediaPlayer player;
    private AudioManager audioManager;

    private int currentPosition = 0;
    private float lastVolume;

    public static MainPlayer getInstance(Context context) {
        if (instance == null)
            instance = new MainPlayer(context);
        return instance;
    }

    private MainPlayer(Context context) {
        this.context = context;
        this.audioManager = ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE));
    }

    public void play(int trackId) {
        if ((player != null) && player.isPlaying())
            player.stop();

        player = MediaPlayer.create(this.context, trackId);
        Log.d("PLAYER", "Playing track with id = " + trackId);
        player.setLooping(true);
        player.start();
    }

    public void stop() {
        if (player.isPlaying())
            player.stop();
        player.release();
    }

    public void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
            currentPosition = player.getCurrentPosition();
            Log.d("PLAYER", "Pausing on: " + currentPosition);
        }
    }

    public void resume() {
        if (player != null) {
            Log.d("PLAYER", "Resuming from: " + currentPosition);
            player.seekTo(currentPosition);
            player.start();
        }
    }

    public void mute() {
        if (player != null) {
            float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            lastVolume = ((streamVolume * 100 * 1.0f) / streamMaxVolume) / 100;
            player.setVolume(0, 0);
        }
    }

    public void unmute() {
        if (player != null) {
            player.setVolume(lastVolume, lastVolume);
        }
    }
}
