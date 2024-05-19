package com.example.game_project;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button playbtn;
    AudioManager audioManager;

    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        playbtn = findViewById(R.id.play_button);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        scaleUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_down);

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(MainActivity.this, R.raw.click_sound);
                mP.start();
                v.startAnimation(scaleUp);
                Login Login = new Login(MainActivity.this);
                Login.show();
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, scaleUp.getDuration());
            }
        });

    }

}
