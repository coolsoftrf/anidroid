package com.example.androidanimation.voice;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;

import java.util.ArrayList;

public class RecognitionController {
    private Activity mActivity;
    private RecognitionListener mListener;

    public static final int REQUEST_CODE = 1;

    public RecognitionController (Activity activity){
        mActivity = activity;
    }

    public void recognize(RecognitionListener listener){
        mListener = listener;
        mActivity.startActivityForResult(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                        .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//                        .putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
                , REQUEST_CODE);
    }

    public RecognitionListener getListener() {
        return mListener;
    }

    public interface RecognitionListener {
        void onRecognized(ArrayList<String> data);
    }
}
