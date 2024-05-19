package com.example.game_project;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import java.util.Objects;

public class Login extends Dialog {

    private EditText et_name, et_age;
    private Button saveBtn;

    public Login(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_Age);
        saveBtn = findViewById(R.id.savebtn);

        SharedPreferences preferences = getContext().getSharedPreferences("loginPrefs", MODE_PRIVATE);
        if (preferences.contains("NAME") && preferences.contains("AGE")) {
            navigateToPlayActivity();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoginDetails();
                final MediaPlayer mP = MediaPlayer.create(getContext(), R.raw.click_sound);
                mP.start();

            }
        });

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

    private void saveLoginDetails() {

        String name = et_name.getText().toString();
        String age = et_age.getText().toString();

        SharedPreferences preferences = getContext().getSharedPreferences("loginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NAME", name);
        editor.putString("AGE", age);
        editor.apply();

        navigateToPlayActivity();
    }

    private void navigateToPlayActivity() {
        Intent i = new Intent(getContext(), PlayActivity.class);
        getContext().startActivity(i);
    }

}