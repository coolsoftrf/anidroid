package com.example.androidanimation;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androidanimation.flash.FlashController;

public class MainActivity extends ActivityBase {

    private FlashController mFlashController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlashController = new FlashController();
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

    public void onFlash(View view) {
        if (mFlashController.isFlashSupported()) {
            if (mFlashController.isTorchOn()) {
                mFlashController.turnFlashOff();
                button.setText(R.string.flash_on);
            } else {
                mFlashController.turnFlashOn();
                button.setText(R.string.flash_off);
            }
        }
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
