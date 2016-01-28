package com.dp.hellowife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.msf.pinlibrary.PinListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REQUEST_CODE_SET_PIN = 0;
    public static final int REQUEST_CODE_CHANGE_PIN = 1;
    public static final int REQUEST_CODE_CONFIRM_PIN = 2;
    static SharedPreferences pinLockPrefs;
    FloatingActionButton ring;
    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();
        setUpListeners();
    }

    private void setUpViews() {
        pinLockPrefs = getSharedPreferences("PinLockPrefs", MODE_PRIVATE);
        pin = pinLockPrefs.getString("pin", "");

        ring = (FloatingActionButton) findViewById(R.id.ring);

    }

    private void setUpListeners() {
        ring.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(R.id.ring == id){
            if("".equals(pin))
            {
                Intent intent = new Intent(MainActivity.this, SetUserPin.class);
                startActivityForResult(intent, REQUEST_CODE_SET_PIN);
            } else {
                Intent intent = new Intent(MainActivity.this, EnterPin.class);
                startActivityForResult(intent, REQUEST_CODE_CONFIRM_PIN);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SET_PIN: {
                if (resultCode == PinListener.SUCCESS) {
                    Toast.makeText(this, "Pin is Set Successfully...", Toast.LENGTH_SHORT).show();
                } else if (resultCode == PinListener.CANCELLED) {
                    Toast.makeText(this, "Pin Set Cancelled...", Toast.LENGTH_SHORT).show();
                }
                refreshActivity();
                break;
            }
            /*case REQUEST_CODE_CHANGE_PIN: {
                if (resultCode == PinListener.SUCCESS) {
                    Intent intent = new Intent(MainActivity.this, SetPinActivitySample.class);
                    startActivityForResult(intent, REQUEST_CODE_SET_PIN);
                } else if (resultCode == PinListener.CANCELLED) {
                    Toast.makeText(this, "Pin change cancelled :|", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CODE_CONFIRM_PIN: {
                if (resultCode == PinListener.SUCCESS) {
                    Toast.makeText(this, "Pin is correct :)", Toast.LENGTH_SHORT).show();
                } else if (resultCode == PinListener.CANCELLED) {
                    Toast.makeText(this, "Pin confirm cancelled :|", Toast.LENGTH_SHORT).show();
                }
                break;
            }*/
        }
    }

    private void refreshActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
