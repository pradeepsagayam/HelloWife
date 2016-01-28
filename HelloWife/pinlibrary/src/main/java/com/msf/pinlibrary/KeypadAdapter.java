package com.msf.pinlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

/**
 * Created by akshayas on 1/14/2016.
 */
public class KeypadAdapter extends BaseAdapter {
    private final TypedArray styledAttributes;
    private final Context context;
    private final LayoutInflater inflater;
    private final PinListener pinListener;

    public KeypadAdapter(Context context, TypedArray styledAttributes, PinListener pinListener) {
        this.context = context;
        this.styledAttributes = styledAttributes;
        inflater = LayoutInflater.from(this.context);
        this.pinListener = pinListener;
    }

    @Override
    public int getCount() {
        return 11;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (position == 10)
            return 0;
        return ((position + 1) % 10);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button view;
        if (null == convertView){
            view = (Button)inflater.inflate(R.layout.pin_input_button, null);
        } else{
            view = (Button)convertView;
        }

        setStyle(view);
        setValues(position, view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pinLength = styledAttributes.getInt(R.styleable.PinLock_pinLength, 4);
                Button key = (Button) v;
                final String keyText = key.getText().toString();
                String currentPin = KeyPad.onKeyPressed(keyText);
//                System.out.println("AAAA currentPin : " + currentPin);
                int currentPinLength = currentPin.length();
                pinListener.onPinValueChange(currentPinLength);
                if(currentPinLength == pinLength){
                    pinListener.onCompleted(currentPin);
                    KeyPad.resetPin();
                }
            }
        });

        return view;
    }

    private void setValues(int position, Button view) {
        if (position == 10) {
            view.setText("0");
        }else if (position == 9){
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setText(String.valueOf((position + 1) % 10));
        }
    }

    private void setStyle(Button view) {
        int textSize = styledAttributes.getDimensionPixelOffset(R.styleable.PinLock_keypadTextSize, 10);
        int color = styledAttributes.getColor(R.styleable.PinLock_keypadTextColor, Color.BLACK);
        int background = styledAttributes.getResourceId(R.styleable.PinLock_keypadButtonShape, R.drawable.circle);

        view.setTextSize(textSize);
        view.setTextColor(color);
        view.setBackgroundResource(background);
    }
}
