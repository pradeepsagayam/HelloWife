package com.dp.hellowife;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by akshayas on 1/29/2016.
 */
public class HiddenActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hidden_activity);
    }
}
