package com.msf.pinlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by akshayas on 1/14/2016.
 */
public class StatusDot extends LinearLayout {
    private final Context context;
    private final TypedArray styledAttributes;

    public StatusDot(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        styledAttributes = context.obtainStyledAttributes(R.style.PinLock, R.styleable.PinLock);
        initialize();
    }

    public void initialize() {
        addDots(0);
    }

    private void addDots(int length) {
        removeAllViews();
        int pinLength = styledAttributes.getInt(R.styleable.PinLock_pinLength, 4);
        for (int i = 0; i < pinLength; i++){
            Dot dot = new Dot(context, styledAttributes, i < length);
            addView(dot);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        styledAttributes.recycle();
    }

    public void updateStatusDot(int pinLength){
        addDots(pinLength);
    }
}
