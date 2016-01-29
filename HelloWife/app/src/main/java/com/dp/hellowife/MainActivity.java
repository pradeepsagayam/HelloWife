package com.dp.hellowife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msf.pinlibrary.PrefHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REQUEST_CODE_SET_PIN = 0;
    public static final int REQUEST_CODE_CHANGE_PIN = 1;
    public static final int REQUEST_CODE_CONFIRM_PIN = 2;
    SharedPreferences pinLockPrefs;
    FloatingActionButton ring;
    Intent intent;
    ImageView albumIcon, notesIcon, greetIcon;
    TextView memories, notes, greet;
    LinearLayout memoriesLayout, notesLayout, greetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();
        setUpListeners();
    }

    private void setUpViews() {
        pinLockPrefs = PrefHelper.getPref(getApplicationContext(), "PinLockPrefs");
        /*String pin = pinLockPrefs.getString("pin", "");
        Boolean isPinSet = pinLockPrefs.getBoolean("isPinSet", false);*/

        ring = (FloatingActionButton) findViewById(R.id.ring);

        albumIcon = (ImageView) findViewById(R.id.album_icon);
        notesIcon = (ImageView) findViewById(R.id.notes_icon);
        greetIcon = (ImageView) findViewById(R.id.greet_icon);

        memories = (TextView) findViewById(R.id.memories);
        notes = (TextView) findViewById(R.id.notes);
        greet = (TextView) findViewById(R.id.greet);

        memoriesLayout = (LinearLayout)findViewById(R.id.memories_layout);
        notesLayout = (LinearLayout)findViewById(R.id.notes_layout);
        greetLayout = (LinearLayout)findViewById(R.id.greet_layout);

    }

    private void setUpListeners() {
        ring.setOnClickListener(this);

        albumIcon.setOnClickListener(this);
        notesIcon.setOnClickListener(this);
        greetIcon.setOnClickListener(this);

        memories.setOnClickListener(this);
        notes.setOnClickListener(this);
        greet.setOnClickListener(this);

        memoriesLayout.setOnClickListener(this);
        notesLayout.setOnClickListener(this);
        greetLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.ring :
                if(pinLockPrefs.getBoolean("isPinSet", false))
                {
                    intent = new Intent(MainActivity.this, EnterPin.class);
                    startActivity(intent);
//                    startActivityForResult(intent, REQUEST_CODE_CONFIRM_PIN);
                } else {
                    intent = new Intent(MainActivity.this, SetUserPin.class);
                    startActivity(intent);
//                    startActivityForResult(intent, REQUEST_CODE_SET_PIN);
                }
                break;

            case R.id.memories_layout:
            case R.id.album_icon:
            case R.id.memories:
                Toast.makeText(this, "My Memories with her...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.notes_layout:
            case R.id.notes_icon:
            case R.id.notes:
                Toast.makeText(this, "My Notes on her...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.greet_layout:
            case R.id.greet_icon:
            case R.id.greet:
                Toast.makeText(this, "Greet her...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /*@Override
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
            case REQUEST_CODE_CONFIRM_PIN: {
                if (resultCode == PinListener.SUCCESS) {
                    Toast.makeText(this, "Pin is correct...", Toast.LENGTH_SHORT).show();
                } else if (resultCode == PinListener.CANCELLED) {
                    Toast.makeText(this, "Pin confirm cancelled...", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }*/

    private void refreshActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
