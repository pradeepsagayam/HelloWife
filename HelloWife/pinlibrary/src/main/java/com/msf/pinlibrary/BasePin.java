package com.msf.pinlibrary;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by akshayas on 1/14/2016.
 */
public abstract class BasePin extends Activity implements PinListener{

    private TextView label;
    private StatusDot statusDots;
    private Button cancelButton;
    private Button forgotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        KeyPad keyPad = (KeyPad) findViewById(R.id.keypad);
        keyPad.setPinListener(this);

        label = (TextView)findViewById(R.id.label);
        statusDots = (StatusDot)findViewById(R.id.statusDots);

        setUpButtons();
        setUpStyles();

    }

    private void setUpStyles() {
        TypedArray styledAttributes = obtainStyledAttributes(R.style.PinLock, R.styleable.PinLock);

        int layoutBackground = styledAttributes.getColor(R.styleable.PinLock_backgroundColor, Color.WHITE);
        View layout = findViewById(R.id.pinLockLayout);
        layout.setBackgroundColor(layoutBackground);

        int cancelTextSize = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_cancelTextSize, 8);
        int cancelTextColor = styledAttributes.getColor(R.styleable.PinLock_cancelTextColor, Color.BLACK);
        cancelButton.setTextSize(cancelTextSize);
        cancelButton.setTextColor(cancelTextColor);

        int forgotTextSize = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_forgotTextSize, 8);
        int forgotTextColor = styledAttributes.getColor(R.styleable.PinLock_forgotTextColor, Color.BLACK);
        forgotButton.setTextSize(forgotTextSize);
        if(forgotButton.isEnabled()){
            forgotButton.setTextColor(forgotTextColor);
        } else {
            forgotButton.setTextColor(getResources().getColor(R.color.disable_btn_color));
        }

        int buttonBackground = styledAttributes.getResourceId(R.styleable.PinLock_cancelForgetButtonBackground, R.drawable.rectangle);
        cancelButton.setBackgroundResource(buttonBackground);
        forgotButton.setBackgroundResource(buttonBackground);

        final int infoTextSize = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_infoTextSize, 8);
        final int infoTextColor = styledAttributes.getColor(R.styleable.PinLock_infoTextColor, Color.BLACK);
        label.setTextSize(infoTextSize);
        label.setTextColor(infoTextColor);

    }

    private void setUpButtons() {
        cancelButton = (Button)findViewById(R.id.cancelButton);
        findViewById(R.id.ripple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(CANCELLED);
                finish();
            }
        });

        forgotButton = (Button)findViewById(R.id.forgotButton);
        findViewById(R.id.ripple1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForgotPin();
                setResult(FORGOT);
                finish();
            }
        });
    }

    public void disableForgotButton(){
        forgotButton.setEnabled(false);
        forgotButton.setTextColor(getResources().getColor(R.color.disable_btn_color));
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public void resetStatus() {
        statusDots.initialize();
    }

    @Override
    public abstract void onCompleted(String pin);

    @Override
    public void onPinValueChange(int length) {
            statusDots.updateStatusDot(length);
    }

    @Override
    public void onForgotPin() {

    }
}
