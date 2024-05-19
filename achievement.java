package com.example.game_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class achievement extends AppCompatActivity {

    TextView animalScore, shapeScore, letterScore;
    Button backbtn;
    SharedPreferences sharedPreferences;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_achievement);

        sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE);

        animalScore = findViewById(R.id.animalScore);
        shapeScore = findViewById(R.id.shapeScore);
        letterScore = findViewById(R.id.letterScore);
        backbtn = findViewById(R.id.back_button);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(achievement.this, R.raw.click_sound);
                mP.start();
                Intent back = new Intent(achievement.this, PlayActivity.class);
                startActivity(back);
                finish();
            }
        });

        int animalScoreValue = sharedPreferences.getInt("Animal_score", 0);
        int shapeScoreValue = sharedPreferences.getInt("shape_score", 0);
        long letterScoreValue = sharedPreferences.getLong("bestTime", Long.MAX_VALUE);

        if (letterScoreValue != Long.MAX_VALUE) {
            long minutes = letterScoreValue / 60000;
            long seconds = (letterScoreValue % 60000) / 1000;
            letterScore.setText("Letter Game Best Time: " + minutes + ":" + String.format("%02d", seconds));
        }

        animalScore.setText("Animals Game Score: " + animalScoreValue);
        shapeScore.setText("Shape Game Score: " + shapeScoreValue);
    }
}