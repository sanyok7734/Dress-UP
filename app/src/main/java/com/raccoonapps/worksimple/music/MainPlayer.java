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

    private int currentTrack;
    private int currentPosition = 0;
    private float lastVolume;
    private boolean isMuted = false;

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
        if (player != null) {
            Log.d("PLAYER", "Assigning player null value");
            player = null;
        }

        player = MediaPlayer.create(this.context, trackId);
        player.setLooping(true);
        if (isMuted)
            player.setVolume(0, 0);
        player.start();
        currentTrack = trackId;
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
        }
    }

    public void resume() {
        if (player != null) {
            if (isMuted)
                player.setVolume(0, 0);
            player.seekTo(currentPosition);
            player.start();
        } else {
            play(currentTrack);
        }
    }

    public void resetPlayer() {
        player = null;
        currentPosition = 0;
    }

    public void mute() {
        if (player != null) {
            float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            lastVolume = streamVolume / (streamMaxVolume * 1f);
            Log.d("PLAYER", "Volume = " + lastVolume);
            player.setVolume(0, 0);
            isMuted = true;
        }
    }

    public void unmute() {
        if (player != null) {
            player.setVolume(lastVolume, lastVolume);
            isMuted = false;
        }
    }

    public boolean isMuted() {
        return isMuted;
    }
}
