package com.dp.hellowife.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.hellowife.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageLogFragment extends Fragment {

    Context context;

    public MessageLogFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_log, container, false);
    }

}
