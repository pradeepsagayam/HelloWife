package com.dp.hellowife.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dp.hellowife.HomeScreen;
import com.dp.hellowife.R;
import com.dp.hellowife.adapter.MyRecyclerViewAdapter;
import com.dp.hellowife.constants.AppConstants;
import com.dp.hellowife.helper.DataBaseHelper;
import com.dp.hellowife.helper.TextDrawableHelper;
import com.dp.hellowife.interfaces.Builder;
import com.dp.hellowife.interfaces.ItemSelectionListener;
import com.dp.hellowife.model.Notes;

import java.util.ArrayList;


/**
 * Created by pradeepd on 09-01-2016.
 */
public class NotesList extends AppCompatActivity implements View.OnClickListener {

    private MyRecyclerViewAdapter mAdapter;
    private static String LOG_TAG = "CardViewActivity";
    FloatingActionButton addNewNote;
    public static final int ROUND = 1;
    public static final int ROUND_WITH_BORDER = 2;
    private Menu menu = null;
    Builder mDrawableBuilder;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view);

        setUpViews();
        setUpControllers();

    }

    private void setUpViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        addNewNote = (FloatingActionButton) findViewById(R.id.addNewNote);
        mLayoutManager = new LinearLayoutManager(this);

        setRoundType(ROUND);

    }

    private void setRoundType(int type) {
        // initialize the builder based on the "TYPE"
        switch (type) {
            case ROUND:
                mDrawableBuilder = TextDrawableHelper.builder().beginConfig().fontSize(70).endConfig().round();
                break;
            case ROUND_WITH_BORDER:
                mDrawableBuilder = TextDrawableHelper.builder().beginConfig().fontSize(70).withBorder(4).endConfig().round();
                break;
            default:
                mDrawableBuilder = TextDrawableHelper.builder().beginConfig().fontSize(70).endConfig().round();
                break;
        }
    }

    private void setUpControllers() {
        addNewNote.setOnClickListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyRecyclerViewAdapter(getNotesList(), mDrawableBuilder, this, new ItemSelectionListener() {
            @Override
            public void onSelected() {
                menu.findItem(R.id.delete).setVisible(true);
                menu.findItem(R.id.delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

            @Override
            public void onUnselected() {
                if (null != menu) {
                    menu.findItem(R.id.delete).setVisible(false);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.delete:
                mAdapter.deleteSelectedItems();
                mAdapter.updateRecentNotesList();
                menu.findItem(R.id.delete).setVisible(false);
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
        if (resultCode == RESULT_OK && requestCode == AppConstants.ADD_NEW_NOTE_RESULT) {
            updateNotesList();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToMainActivity();
    }

    private void navigateToMainActivity() {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
        finish();
    }

}