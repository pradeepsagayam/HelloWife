package com.msf.pinlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by akshayas on 1/21/2016.
 */
public abstract class ConfirmPin extends BasePin {
    LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLabel(TEXT_ENTER_PIN);

        buttonLayout = (LinearLayout)findViewById(R.id.buttonLayout);
        buttonLayout.setVisibility(View.GONE);
    }

    @Override
    public final void onCompleted(String pin) {
        resetStatus();
        if (isPinCorrect(pin)) {
            setResult(SUCCESS);
            finish();
        } else {
            setLabel(TEXT_PIN_INVALID);
        }
    }

    public abstract boolean isPinCorrect(String pin);

    @Override
    public abstract void onForgotPin();

    @Override
    public void onBackPressed() {
        setResult(CANCELLED);
        finish();
    }
}
