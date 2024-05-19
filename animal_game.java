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

public class animal_game extends AppCompatActivity {

    ImageView image1, image2, image3, image4, MainImage;
    TextView tv_Status, timerText;
    ImageButton backbtn;
    MediaPlayer animalSong , bgSong;
    CountDownTimer timer;

    long timeLeftInMillis;
    final long COUNTDOWN_IN_MILLIS = 30000;

    Integer[] images = {
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4,
            R.drawable.image_5,
            R.drawable.image_6,
            R.drawable.image_7,
            R.drawable.image_8,
            R.drawable.image_9,
            R.drawable.image_10,
            R.drawable.image_11,
            R.drawable.image_12,
            R.drawable.image_13,
            R.drawable.image_14,
            R.drawable.image_15,
            R.drawable.image_16,
            R.drawable.image_17,
            R.drawable.image_18,
            R.drawable.image_19
    };

    Integer[] images_bw = {
            R.drawable.image_bw_1,
            R.drawable.image_bw_2,
            R.drawable.image_bw_3,
            R.drawable.image_bw_4,
            R.drawable.image_bw_5,
            R.drawable.image_bw_6,
            R.drawable.image_bw_7,
            R.drawable.image_bw_8,
            R.drawable.image_bw_9,
            R.drawable.image_bw_10,
            R.drawable.image_bw_11,
            R.drawable.image_bw_12,
            R.drawable.image_bw_13,
            R.drawable.image_bw_14,
            R.drawable.image_bw_15,
            R.drawable.image_bw_16,
            R.drawable.image_bw_17,
            R.drawable.image_bw_18,
            R.drawable.image_bw_19
    };
    Integer[] images_numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};

    int turn = 0;
    int correctAnswer = 0;
    int score = 0;
    int roundsPlayed = 0;
    SharedPreferences sharedPreferences;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_animal_game);

        sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        MainImage = findViewById(R.id.MainImage);
        backbtn = findViewById(R.id.back);
        timerText = findViewById(R.id.timer);

        animalSong = MediaPlayer.create(getApplicationContext(), R.raw.music_1);
        animalSong.start();
        animalSong.setVolume(1f, 1f);
        animalSong.setLooping(true);

        tv_Status = findViewById(R.id.tv_Status);

        setImages();
        startTimer();

        image1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                handleAnswer(1);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                handleAnswer(2);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                handleAnswer(3);
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                handleAnswer(4);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(animal_game.this, R.raw.click_sound);
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

        image1.setEnabled(false);
        image2.setEnabled(false);
        image3.setEnabled(false);
        image4.setEnabled(false);

        setImages();
    }

    /** @noinspection deprecation, deprecation */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveHighScore(score);
        Intent back = new Intent(animal_game.this, PlayActivity.class);
        startActivity(back);
        finish();
        animalSong.release();

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
    private void setImages(){
        if (roundsPlayed >= 100) {
            checkEnd();
            return;
        }
        Random r = new Random();
        correctAnswer = r.nextInt(4) + 1;

        int correctImageIndex = images_numbers[turn];
        int wrongAnswer1, wrongAnswer2, wrongAnswer3;

        do {
            wrongAnswer1 = r.nextInt(19);
        } while (wrongAnswer1 == correctImageIndex);

        do {
            wrongAnswer2 = r.nextInt(19);
        } while (wrongAnswer2 == correctImageIndex || wrongAnswer2 == wrongAnswer1);

        do {
            wrongAnswer3 = r.nextInt(19);
        } while (wrongAnswer3 == correctImageIndex || wrongAnswer3 == wrongAnswer1 ||
                wrongAnswer3 == wrongAnswer2);

        ImageView[] imageViews = {image1, image2, image3, image4};
        Integer[] options = {1, 2, 3, 4};
        Collections.shuffle(Arrays.asList(options));

        for (int i = 0; i < imageViews.length; i++) {
            int option = options[i];
            if (option == correctAnswer) {
                imageViews[i].setImageResource(images[correctImageIndex]);
            } else {
                int wrongIndex;
                switch (option) {
                    case 2:
                        wrongIndex = wrongAnswer2;
                        break;
                    case 3:
                        wrongIndex = wrongAnswer3;
                        break;
                    default:
                        wrongIndex = wrongAnswer1;
                }
                imageViews[i].setImageResource(images[wrongIndex]);
            }
            imageViews[i].setEnabled(true);
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleAnswer(option);
                }
            });
        }

        MainImage.setImageResource(images_bw[images_numbers[turn]]);

        tv_Status.setText(" ");
        turn++;
        roundsPlayed++;

        if (turn >= images_numbers.length) {
            turn = 0;
        }
    }

    private void checkEnd() {
        AlertDialog.Builder alert = getBuilder();
        alert.setNegativeButton("QUIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveHighScore(score);
                Intent i = new Intent(animal_game.this, PlayActivity.class);
                startActivity(i);
                finish();
                animalSong.release();

                if (bgSong != null) {
                    if (bgSong.isPlaying()) {
                        bgSong.stop();
                    }
                    bgSong.release();
                }
            }
        });
        alert.create();
        alert.show();
    }

    private void saveHighScore(int score) {
        int currentHighScore = sharedPreferences.getInt("Animal_score", 0);

        if (score > currentHighScore) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Animal_score", score);
            editor.apply();
        }
    }

    @NonNull
    private AlertDialog.Builder getBuilder() {
        AlertDialog.Builder alert = new AlertDialog.Builder(animal_game.this);
        alert.setCancelable(false);
        alert.setMessage("Game Over!         Score: " + score);
        alert.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveHighScore(score);
                Intent i1 = new Intent(animal_game.this, animal_game.class);
                startActivity(i1);
                finish();
                animalSong.release();
            }
        });
        return alert;
    }
}