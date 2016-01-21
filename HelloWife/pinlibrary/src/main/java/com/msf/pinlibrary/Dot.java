package com.msf.pinlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by akshayas on 1/14/2016.
 */
public class Dot extends View {

    TypedArray styledAttributes;
    int background;

    public Dot(Context context, TypedArray styledAttributes, boolean filled) {
        super(context);
        this.styledAttributes = styledAttributes;
        setBackground(filled);
        setLayoutParameters();
    }

    private void setLayoutParameters() {
        int dotDiameter = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_statusDotDiameter, 50);
        int margin = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_statusDotSpacing, 30);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotDiameter, dotDiameter);
        layoutParams.setMargins(margin, 5, margin, 5);
        setLayoutParams(layoutParams);
    }

    private void setBackground(boolean filled) {
        if (filled){
            background = styledAttributes.getResourceId(R.styleable.PinLock_statusFilledBackground, R.drawable.filled_dot);
            setBackgroundResource(background);
        } else {
            background = styledAttributes.getResourceId(R.styleable.PinLock_statusEmptyBackground, R.drawable.empty_dot);
            setBackgroundResource(background);
        }
    }
}
