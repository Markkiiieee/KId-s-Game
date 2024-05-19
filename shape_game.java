package com.example.game_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class shape_game extends AppCompatActivity {

    ImageView shape1, shape2, shape3, Qshape;
    TextView tv_Status, timerText;
    CountDownTimer timer;

    MediaPlayer shapeSong, bgSong;
    ImageButton backbtn;

    long timeLeftInMillis;
    final long COUNTDOWN_IN_MILLIS = 30000;

    Integer[] shapes = {
            R.drawable.shape_1,
            R.drawable.shape_2,
            R.drawable.shape_3,
            R.drawable.shape_4,
            R.drawable.shape_5,
            R.drawable.shape_6,
            R.drawable.shape_7,
            R.drawable.shape_8,
            R.drawable.shape_9,
            R.drawable.shape_10,
            R.drawable.shape_11,
            R.drawable.shape_12,
            R.drawable.shape_13
    };

    Integer[] shape_numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    int turn = 0;
    int correctAnswer = 0;
    int score = 0;
    int roundsPlayed = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shape_game);

        sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE);

        shape1 = findViewById(R.id.shape_choice1);
        shape2 = findViewById(R.id.shape_choice2);
        shape3 = findViewById(R.id.shape_choice3);
        Qshape = findViewById(R.id.question);
        backbtn = findViewById(R.id.back);
        timerText = findViewById(R.id.timer);

        shapeSong = MediaPlayer.create(getApplicationContext(), R.raw.music_3);
        shapeSong.start();
        shapeSong.setVolume(1f,1f);
        shapeSong.setLooping(true);

        tv_Status = findViewById(R.id.tv_Status);

        setImages();
        startTimer();

        shape1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                handleAnswer(1);
            }
        });

        shape2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                handleAnswer(2);
            }
        });

        shape3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                handleAnswer(3);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(shape_game.this, R.raw.click_sound);
                mP.start();
                onBackPressed();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void handleAnswer(int i) {
        if (i == correctAnswer) {
            score++;
            tv_Status.setText(" Correct!");
        } else {
            tv_Status.setText(" Wrong!");
        }

        shape1.setEnabled(false);
        shape2.setEnabled(false);
        shape3.setEnabled(false);

        setImages();
    }

    /** @noinspection deprecation, deprecation */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveHighScore(score);
        Intent back = new Intent(shape_game.this, PlayActivity.class);
        startActivity(back);
        finish();
        shapeSong.release();

        if (bgSong != null) {
            if (bgSong.isPlaying()) {
                bgSong.stop();
            }
            bgSong.release();
        }
    }
    private void startTimer() {
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;

        timer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkEnd();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        @SuppressLint("DefaultLocale") String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(timeLeftFormatted);
    }

    private void setImages() {
        if (roundsPlayed >= 100) {
            checkEnd();
            return;
        }
        Random random = new Random();
        correctAnswer = random.nextInt(3) + 1;

        int correctImageIndex = shape_numbers[turn];
        int wrongAnswer1, wrongAnswer2;

        do {
            wrongAnswer1 = random.nextInt(13);
        } while (wrongAnswer1 == correctImageIndex);

        do {
            wrongAnswer2 = random.nextInt(13);
        } while (wrongAnswer2 == correctImageIndex || wrongAnswer2 == wrongAnswer1);

        ImageView[] imageViews = {shape1, shape2, shape3};
        Integer[] options = {1, 2, 3};
        Collections.shuffle(Arrays.asList(options));

        for (int i = 0; i < imageViews.length; i++) {
            int option = options[i];
            if (option == correctAnswer) {
                imageViews[i].setImageResource(shapes[correctImageIndex]);
            } else {
                int wrongIndex;
                if (option == 1) {
                    wrongIndex = wrongAnswer1;
                } else {
                    wrongIndex = wrongAnswer2;
                }
                imageViews[i].setImageResource(shapes[wrongIndex]);
            }
            imageViews[i].setEnabled(true);
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleAnswer(option);
                }
            });
        }
        Qshape.setImageResource(shapes[shape_numbers[turn]]);

        tv_Status.setText(" ");
        turn++;
        roundsPlayed++;

        if (turn >= shape_numbers.length) {
            turn = 0;
        }

    }

    private void checkEnd() {
        AlertDialog.Builder Alt = getBuilder();
        Alt.setNegativeButton("QUIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveHighScore(score);
                Intent i = new Intent(shape_game.this, PlayActivity.class);
                startActivity(i);
                finish();
                shapeSong.release();

                if (bgSong != null) {
                    if (bgSong.isPlaying()) {
                        bgSong.stop();
                    }
                    bgSong.release();
                }
            }
        });
        Alt.create();
        Alt.show();
    }

    private void saveHighScore(int score) {
        int currentHighScore = sharedPreferences.getInt("shape_score", 0);

        if (score > currentHighScore) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("shape_score", score);
            editor.apply();
        }
    }

    @NonNull
    private AlertDialog.Builder getBuilder() {
        AlertDialog.Builder Alt = new AlertDialog.Builder(shape_game.this);
        Alt.setCancelable(false);
        Alt.setMessage("Game Over! Score: " + score);
        Alt.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveHighScore(score);
                Intent i1 = new Intent(shape_game.this, shape_game.class);
                startActivity(i1);
                finish();
                shapeSong.release();
            }
        });
        return Alt;
    }
}