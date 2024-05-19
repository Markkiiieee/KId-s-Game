package com.example.game_project;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {
    Animation scaleUp, scaleDown;
    ImageButton shapebtn, letterbtn, animalbtn, exitbtn, achievebtn, prof_btn;
    private static MediaPlayer bgSong;
    private profileDialog profileDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play);

        shapebtn = findViewById(R.id.shapes);
        prof_btn = findViewById(R.id.profile);
        letterbtn = findViewById(R.id.letters);
        animalbtn = findViewById(R.id.animals);
        achievebtn = findViewById(R.id.achievements);
        exitbtn = findViewById(R.id.exit);

        scaleUp = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.scale_down);

        shapebtn.startAnimation(scaleUp);
        letterbtn.startAnimation(scaleUp);
        animalbtn.startAnimation(scaleUp);
        achievebtn.startAnimation(scaleUp);
        exitbtn.startAnimation(scaleUp);

        if (bgSong == null) {
            bgSong = MediaPlayer.create(PlayActivity.this, R.raw.music_4);
            bgSong.setLooping(true);
            bgSong.setVolume(1f,1f);
            bgSong.start();
        }
        prof_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(PlayActivity.this, R.raw.click_sound);
                mP.start();
                profileDialog = new profileDialog(PlayActivity.this);
                profileDialog.show();
            }
        });

        shapebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(PlayActivity.this, R.raw.click_sound);
                mP.start();
                Intent shapePlay = new Intent(PlayActivity.this, shape_game.class);
                startActivity(shapePlay);
                finish();

                releaseMediaPlayer();
            }
        });
        letterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(PlayActivity.this, R.raw.click_sound);
                mP.start();
                Intent letterPlay = new Intent(PlayActivity.this, letter_game.class);
                startActivity(letterPlay);
                finish();

                if (bgSong != null && bgSong.isPlaying()) {
                    bgSong.stop();
                } else if (bgSong != null) {
                    bgSong.release();
                }
            }
        });
        animalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(PlayActivity.this, R.raw.click_sound);
                mP.start();
                Intent animalPlay = new Intent(PlayActivity.this,animal_game.class);
                startActivity(animalPlay);
                finish();

                if (bgSong != null && bgSong.isPlaying()) {
                    bgSong.stop();
                } else if (bgSong != null) {
                    bgSong.release();
                }
            }
        });
        achievebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(PlayActivity.this, R.raw.click_sound);
                mP.start();
                Intent Achievement = new Intent(PlayActivity.this, achievement.class);
                startActivity(Achievement);
                finish();
            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finishAffinity();
            }
        });
    }
    /** @noinspection deprecation*/
    @Override
    public void onBackPressed() {
        if (profileDialog != null && profileDialog.isShowing()) {
            profileDialog.dismiss();
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgSong != null){
            releaseMediaPlayer();
        }
    }
    private void releaseMediaPlayer() {
        if (bgSong != null){
            if (bgSong.isPlaying()) {
                bgSong.stop();
            }
            bgSong.release();
            bgSong = null;
        }
    }

}