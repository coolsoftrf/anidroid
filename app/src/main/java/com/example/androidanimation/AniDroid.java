package com.example.androidanimation;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class AniDroid extends FrameLayout {
    private View legs;

    public AniDroid(Context context) {
        this(context, null);
    }

    public AniDroid(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_anidroid, this);
        legs = findViewById(R.id.legs);
    }

    public void toggleLeftArm() {
        toggleSelected(R.id.larm);
    }

    public void toggleRightArm() {
        toggleSelected(R.id.rarm);
    }

    public void toggleHead() {
        toggleSelected(R.id.head);
    }

    public void toggleLegs() {
        toggleSelected(R.id.legs);
        animate().translationY(legs.isSelected() ? Math.min(getMeasuredHeight(), getMeasuredWidth()) / 34 * 7 : 0);
    }

    private void toggleSelected(@IdRes int viewId) {
        View view = findViewById(viewId);
        view.setSelected(!view.isSelected());
    }
}
