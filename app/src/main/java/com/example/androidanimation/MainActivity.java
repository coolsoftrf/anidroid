package com.example.androidanimation;

import android.view.View;

public class MainActivity extends ActivityBase {

    public void onLeftArm(View view) {
        droid.toggleLeftArm();
    }

    public void onRightArm(View view) {
        droid.toggleRightArm();
    }

    public void onHead(View view) {
        droid.toggleHead();
    }

    public void onSit(View view) {
        droid.toggleLegs();
    }
}
