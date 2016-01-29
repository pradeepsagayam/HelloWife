package com.dp.hellowife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dp.hellowife.adapter.MyRecyclerViewAdapter;
import com.dp.hellowife.helper.DataBaseHelper;
import com.dp.hellowife.model.Notes;

import java.util.ArrayList;


/**
 * Created by pradeepd on 09-01-2016.
 */
public class NotesList extends AppCompatActivity implements View.OnClickListener {

    View rootView;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    FloatingActionButton addNewNote;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        addNewNote = (FloatingActionButton) findViewById(R.id.addNewNote);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        addNewNote.setOnClickListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getNotesList());
        mRecyclerView.setAdapter(mAdapter);

        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
    }




    private ArrayList<Notes> getNotesList() {

        return DataBaseHelper.retriveNotes(getApplicationContext());
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(NotesList.this, " Clicked on Item " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            if (requestCode == AppConstants.ADD_NEW_NOTE_RESULT) {
                updateNotesList();
            }
        }
    }

    private void updateNotesList() {
        mAdapter.clear();
        mAdapter.addAll(getNotesList());
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v == addNewNote) {
            navigateToAddNewNoteScreen();
        }
    }

    private void navigateToAddNewNoteScreen() {
        Intent i = new Intent(this, AddNewNote.class);
        startActivityForResult(i, AppConstants.ADD_NEW_NOTE_RESULT);
    }
}