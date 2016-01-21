package com.msf.pinlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * Created by akshayas on 1/14/2016.
 */
public class KeyPad extends GridView {

    private static String pin = "";
    private final Context context;
    private final TypedArray styledAttributes;

    public KeyPad(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        styledAttributes = context.obtainStyledAttributes(R.style.PinLock, R.styleable.PinLock);
        setNumColumns(3);
        setColumnWidth(GridView.NO_STRETCH);
        setSpacing();
    }

    private void setSpacing() {
        int verticalSpacing = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_keypadVerticalSpacing, 4);
        int horizontalSpacing = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_keypadHorizontalSpacing,4);
        setVerticalSpacing(verticalSpacing);
        setHorizontalSpacing(horizontalSpacing);

    }

    public void setLayoutParameters(){
        int keypadWidth = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_keypadWidth, 300);
        int keypadHeight = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_keypadHeight,330);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(keypadWidth, keypadHeight);
        layoutParams.gravity = Gravity.CENTER;
        setLayoutParams(layoutParams);
    }

    public void setPinListener(PinListener pinListener){
        setAdapter(new KeypadAdapter(context, styledAttributes, pinListener));
    }

    public static String onKeyPressed(String key){
        pin = pin.concat(key);
        return pin;
    }

    public static void resetPin(){
        pin = "";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayoutParameters();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        styledAttributes.recycle();
    }
}
