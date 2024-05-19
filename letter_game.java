package com.example.game_project;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;

public class letter_game extends AppCompatActivity {
    ImageButton backbtn;
    TextView timerText;
    ImageView iv_1, iv_2, iv_3, iv_4, iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24;

    //Array for the images
    Integer[] cardsArray = {101,102,103,104,105,106,201,202,203,204,205,206};

    //Actual Images
    int letter_a, letter_b, letter_c, letter_d, letter_e, letter_f, letter_a1,
            letter_b1, letter_c1, letter_d1, letter_e1, letter_f1;

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;
    MediaPlayer letterSong, bgSong;
    CountDownTimer timer;
    long timeLeftInMillis = 0;
    long startTime = 0;
    long elapsedTime = 0;
    long bestTime = Long.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_letter_game);

        SharedPreferences sharedPreferences = getSharedPreferences("game_prefs", MODE_PRIVATE);
        bestTime = sharedPreferences.getLong("bestTime", Long.MAX_VALUE);

        timerText = findViewById(R.id.timer);
        backbtn = findViewById(R.id.back);

        startTimer();

        iv_1 = findViewById(R.id.iv_1);
        iv_2 = findViewById(R.id.iv_2);
        iv_3 = findViewById(R.id.iv_3);
        iv_4 = findViewById(R.id.iv_4);
        iv_11 = findViewById(R.id.iv_11);
        iv_12 = findViewById(R.id.iv_12);
        iv_13 = findViewById(R.id.iv_13);
        iv_14 = findViewById(R.id.iv_14);
        iv_21 = findViewById(R.id.iv_21);
        iv_22 = findViewById(R.id.iv_22);
        iv_23 = findViewById(R.id.iv_23);
        iv_24 = findViewById(R.id.iv_24);

        iv_1.setTag("0");
        iv_2.setTag("1");
        iv_3.setTag("2");
        iv_4.setTag("3");
        iv_11.setTag("4");
        iv_12.setTag("5");
        iv_13.setTag("6");
        iv_14.setTag("7");
        iv_21.setTag("8");
        iv_22.setTag("9");
        iv_23.setTag("10");
        iv_24.setTag("11");

        startTime = System.currentTimeMillis();
        frontOfCardsResources();
        Collections.shuffle(Arrays.asList(cardsArray));

        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_1, theCard);
            }
        });
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_2, theCard);
            }
        });
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_3, theCard);
            }
        });
        iv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_4, theCard);
            }
        });
        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_11, theCard);
            }
        });
        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_12, theCard);
            }
        });
        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_13, theCard);
            }
        });
        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_14, theCard);
            }
        });
        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_21, theCard);
            }
        });
        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_22, theCard);
            }
        });
        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_23, theCard);
            }
        });
        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_24, theCard);
            }
        });


        letterSong = MediaPlayer.create(getApplicationContext(), R.raw.music_5);
        letterSong.start();
        letterSong.setVolume(1f, 1f);
        letterSong.setLooping(true);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mP = MediaPlayer.create(letter_game.this, R.raw.click_sound);
                mP.start();
                onBackPressed();
            }
        });
    }

    private void startTimer() {
        timer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis += 1000;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        @SuppressLint("DefaultLocale") String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(timeLeftFormatted);
    }
    private void doStuff(ImageView iv, int card) {
        if (cardsArray[card] == 101){
            iv.setImageResource(letter_a);
        } else if (cardsArray[card] == 102) {
            iv.setImageResource(letter_b);
        }else if (cardsArray[card] == 103) {
            iv.setImageResource(letter_c);
        }else if (cardsArray[card] == 104) {
            iv.setImageResource(letter_d);
        }else if (cardsArray[card] == 105) {
            iv.setImageResource(letter_e);
        }else if (cardsArray[card] == 106) {
            iv.setImageResource(letter_f);
        }else if (cardsArray[card] == 201) {
            iv.setImageResource(letter_a1);
        }else if (cardsArray[card] == 202) {
            iv.setImageResource(letter_b1);
        }else if (cardsArray[card] == 203) {
            iv.setImageResource(letter_c1);
        }else if (cardsArray[card] == 204) {
            iv.setImageResource(letter_d1);
        }else if (cardsArray[card] == 205) {
            iv.setImageResource(letter_e1);
        }else if (cardsArray[card] == 206) {
            iv.setImageResource(letter_f1);
        }

        // check  which image is selected and save it to temporary variable
        if (cardNumber == 1) {
            firstCard = cardsArray[card];
            if (firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            iv.setEnabled(false);
        } else if (cardNumber == 2) {
            secondCard = cardsArray[card];
            if (secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            iv_1.setEnabled(false);
            iv_2.setEnabled(false);
            iv_3.setEnabled(false);
            iv_4.setEnabled(false);
            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //check if the selected images are equal
                    calculate();
                }
            }, 1000);
        }
        checkEnd();
    }

    private void calculate() {
        if (firstCard == secondCard) {
            if (clickedFirst == 0){
                iv_1.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 1){
                iv_2.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 2){
                iv_3.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3){
                iv_4.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 4){
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5){
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 6){
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 7){
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 8){
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 9){
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 10){
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 11){
                iv_24.setVisibility(View.INVISIBLE);
            }

            if (clickedSecond == 0){
                iv_1.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1){
                iv_2.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2){
                iv_3.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3){
                iv_4.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 4){
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5){
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 6){
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 7){
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 8){
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 9){
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 10){
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 11){
                iv_24.setVisibility(View.INVISIBLE);
            }

        } else {
            iv_1.setImageResource(R.drawable.default_image);
            iv_2.setImageResource(R.drawable.default_image);
            iv_3.setImageResource(R.drawable.default_image);
            iv_4.setImageResource(R.drawable.default_image);
            iv_11.setImageResource(R.drawable.default_image);
            iv_12.setImageResource(R.drawable.default_image);
            iv_13.setImageResource(R.drawable.default_image);
            iv_14.setImageResource(R.drawable.default_image);
            iv_21.setImageResource(R.drawable.default_image);
            iv_22.setImageResource(R.drawable.default_image);
            iv_23.setImageResource(R.drawable.default_image);
            iv_24.setImageResource(R.drawable.default_image);

        }

        iv_1.setEnabled(true);
        iv_2.setEnabled(true);
        iv_3.setEnabled(true);
        iv_4.setEnabled(true);
        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);

        // check if the game is over

        checkEnd();
    }
    private void checkEnd() {
        if (iv_1.getVisibility() == View.INVISIBLE &&
                iv_2.getVisibility() == View.INVISIBLE &&
                iv_3.getVisibility() == View.INVISIBLE &&
                iv_4.getVisibility() == View.INVISIBLE &&
                iv_11.getVisibility() == View.INVISIBLE &&
                iv_12.getVisibility() == View.INVISIBLE &&
                iv_13.getVisibility() == View.INVISIBLE &&
                iv_14.getVisibility() == View.INVISIBLE &&
                iv_21.getVisibility() == View.INVISIBLE &&
                iv_22.getVisibility() == View.INVISIBLE &&
                iv_23.getVisibility() == View.INVISIBLE &&
                iv_24.getVisibility() == View.INVISIBLE) {

            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime < bestTime) {
                saveTime(elapsedTime);
                bestTime = elapsedTime;
            }

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(letter_game.this);
            alertDialogBuilder
                    .setMessage("Congratulations!!")
                    .setCancelable(false)
                    .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), letter_game.class);
                            startActivity(intent);
                            finish();
                            letterSong.release();
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), PlayActivity.class);
                            startActivity(i);
                            finish();
                            letterSong.release();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void saveTime(long elapsedTime) {
        SharedPreferences sharedPreferences = getSharedPreferences("game_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("playElapsedTime", elapsedTime);
        editor.putLong("bestTime", elapsedTime);
        editor.apply();
    }

    private void frontOfCardsResources() {
        letter_a = R.drawable.letter_a;
        letter_b = R.drawable.letter_b;
        letter_c = R.drawable.letter_c;
        letter_d = R.drawable.letter_d;
        letter_e = R.drawable.letter_e;
        letter_f = R.drawable.letter_f;
        letter_a1 = R.drawable.letter_a1;
        letter_b1 = R.drawable.letter_b1;
        letter_c1 = R.drawable.letter_c1;
        letter_d1 = R.drawable.letter_d1;
        letter_e1 = R.drawable.letter_e1;
        letter_f1 = R.drawable.letter_f1;
    }

    /** @noinspection deprecation*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(letter_game.this, PlayActivity.class);
        startActivity(back);
        finish();
        letterSong.release();

        if (bgSong != null) {
            if (bgSong.isPlaying()) {
                bgSong.stop();
            }
            bgSong.release();
        }

    }

}