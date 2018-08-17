package com.example.androidanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidanimation.flash.FlashController;

abstract class ActivityBase extends AppCompatActivity {
    AniDroid droid;
    Button button;

    FlashController mFlashController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlashController = new FlashController();

        setContentView(R.layout.activity_main);
        droid = findViewById(R.id.anidroid);
        button = findViewById(R.id.button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFlashController.checkPermissions(this)) {
            mFlashController.init(this);
            setupFlashButton();
        } else {
            mFlashController.requestPermissions(this);
            Toast.makeText(this, "Please grant Camera permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFlashController.release();
    }


    public void setupFlashButton() {
        if (mFlashController.isFlashSupported()) {
            button.setVisibility(View.VISIBLE);

            if (mFlashController.isTorchOn()) {
                button.setText(R.string.flash_off);
            } else {
                button.setText(R.string.flash_on);
            }
        } else {
            button.setVisibility(View.GONE);
            Toast.makeText(this, "Flash is not supported.", Toast.LENGTH_SHORT).show();
        }
    }
}
