package com.example.androidanimation;

import android.view.View;

public class MainActivity extends ActivityBase {

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
}
