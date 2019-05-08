package com.example.servertest;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
public class emergency extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        getSupportActionBar().setTitle("Emergency");
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            v.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(5000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
