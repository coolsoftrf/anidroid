package com.example.androidanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

abstract class ActivityBase extends AppCompatActivity {
    AniDroid droid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        droid = findViewById(R.id.anidroid);
    }
}
