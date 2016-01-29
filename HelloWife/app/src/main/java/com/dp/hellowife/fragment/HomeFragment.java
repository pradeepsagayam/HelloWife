package com.dp.hellowife.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dp.hellowife.EnterPin;
import com.dp.hellowife.NotesList;
import com.dp.hellowife.R;
import com.dp.hellowife.SetUserPin;
import com.msf.pinlibrary.PrefHelper;


/**
 * Created by akshayas on 1/29/2016.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_CODE_SET_PIN = 0;
    public static final int REQUEST_CODE_CHANGE_PIN = 1;
    public static final int REQUEST_CODE_CONFIRM_PIN = 2;
    SharedPreferences pinLockPrefs;
    FloatingActionButton ring;
    /*ImageView albumIcon, notesIcon, greetIcon;
    TextView memories, notes, greet;*/
    LinearLayout memoriesLayout, notesLayout, greetLayout;
    NavigationView navigationView;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.home_screen, container);
        setUpViews();
        setUpListeners();
        return rootView;
    }

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    private void setUpViews() {
        pinLockPrefs = PrefHelper.getPref(getActivity().getApplicationContext(), "PinLockPrefs");

        navigationView = (NavigationView) rootView.findViewById(R.id.nav_view);

        ring = (FloatingActionButton) rootView.findViewById(R.id.ring);

        /*albumIcon = (ImageView) rootView.findViewById(R.id.album_icon);
        notesIcon = (ImageView) rootView.findViewById(R.id.notes_icon);
        greetIcon = (ImageView) rootView.findViewById(R.id.greet_icon);

        memories = (TextView) rootView.findViewById(R.id.memories);
        notes = (TextView) rootView.findViewById(R.id.notes);
        greet = (TextView) rootView.findViewById(R.id.greet);*/

        memoriesLayout = (LinearLayout) rootView.findViewById(R.id.memories_layout);
        notesLayout = (LinearLayout) rootView.findViewById(R.id.notes_layout);
        greetLayout = (LinearLayout) rootView.findViewById(R.id.greet_layout);

    }

    private void setUpListeners() {
        ring.setOnClickListener(this);
//        albumIcon.setOnClickListener(this);
//        notesIcon.setOnClickListener(this);
//        greetIcon.setOnClickListener(this);
//
//        memories.setOnClickListener(this);
//        notes.setOnClickListener(this);
//        greet.setOnClickListener(this);

        memoriesLayout.setOnClickListener(this);
        notesLayout.setOnClickListener(this);
        greetLayout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.ring:
                System.out.println("AAAA ring");
                if (pinLockPrefs.getBoolean("isPinSet", false)) {
                    Intent intent = new Intent(getActivity(), EnterPin.class);
                    startActivity(intent);
//                    startActivityForResult(intent, REQUEST_CODE_CONFIRM_PIN);
                } else {
                    Intent intent = new Intent(getActivity(), SetUserPin.class);
                    startActivity(intent);
//                    startActivityForResult(intent, REQUEST_CODE_SET_PIN);
                }
                break;

            case R.id.memories_layout:
            case R.id.album_icon:
            case R.id.memories:
                System.out.println("AAAA ring");
                break;

            case R.id.notes_layout:
            case R.id.notes_icon:
            case R.id.notes:
                System.out.println("AAAA ring");
                Intent intent = new Intent(getActivity(), NotesList.class);
                startActivity(intent);
//                Toast.makeText(this, "My Notes on her...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.greet_layout:
            case R.id.greet_icon:
            case R.id.greet:
                System.out.println("AAAA ring");

                break;
        }
    }
}
