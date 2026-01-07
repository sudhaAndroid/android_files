package com.example.complecourseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class AudioVideoActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    VideoView videoView;
    Button btnPlay, btnPause, btnStop,btnPlayVideo;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_video);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        videoView = findViewById(R.id.videoView);
        btnPlayVideo = findViewById(R.id.btnPlayVideo);

        mediaPlayer = MediaPlayer.create(this, R.raw.ringtone);

        //when im handling multiple actions in single button , have to be go with flag
        if (flag==false){
            //button has to be played
            flag=true;
        }else{
            //make the audio to be pause
        }

        btnPlay.setOnClickListener(v -> mediaPlayer.start());
        btnPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) mediaPlayer.pause();
        });
        btnStop.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(this, R.raw.ringtone); // reset
            }
        });

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        btnPlayVideo.setOnClickListener(v -> {
            String path = "android.resource://" + getPackageName() + "/" + R.raw.video;
            Log.d("checkPath",""+path);
            videoView.setVideoURI(Uri.parse(path));
            videoView.start();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AudioVideoActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}