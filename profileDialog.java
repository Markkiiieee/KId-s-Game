package com.example.game_project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Objects;

public class profileDialog extends Dialog {
    private final AudioManager audioManager;
    Button backbtn, Reset;
    TextView characterName, characterAge;
    public profileDialog(@NonNull Context context) {
        super(context);
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SeekBar musicVolume = findViewById(R.id.musicBar);
        characterName = findViewById(R.id.characterName);
        characterAge = findViewById(R.id.characterAge);
        Reset = findViewById(R.id.resetbtn);
        backbtn = findViewById(R.id.backbtn);

        SharedPreferences preferences = getContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        if (preferences.contains("NAME") && preferences.contains("AGE")) {
            String name = preferences.getString("NAME", "");
            String age = preferences.getString("AGE", "");

            characterName.setText(name);
            characterAge.setText(age);
        }

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE).edit();
                editor.remove("NAME");
                editor.remove("AGE");
                editor.apply();

                Intent i = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(i);
            }
        });

        if (backbtn != null) {
            backbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        if (musicVolume != null) {
            musicVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            musicVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

            musicVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    smoothVolumeTransition(progress);
                }
                private void smoothVolumeTransition(int progress) {
                    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volumePercentage = (float) progress / musicVolume.getMax();
                    int targetVolume = (int) (maxVolume * volumePercentage);

                    int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    int volumeDiff= targetVolume - currentVolume;

                    int volumeIncrement = Math.max(1, Math.abs(volumeDiff) / 10);
                    int direction = volumeDiff > 0 ? 1 : -1;

                    for (int i = 0; i < Math.abs(volumeDiff); i += volumeIncrement) {
                        currentVolume += volumeIncrement * direction;
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);

                        try {
                            //noinspection BusyWait
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            //noinspection CallToPrintStackTrace
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        DisplayMetrics dm = new DisplayMetrics();
        Objects.requireNonNull(getWindow()).getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(false);

    }
}