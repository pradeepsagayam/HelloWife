package com.dp.hellowife;

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

import com.dp.hellowife.adapter.MyRecyclerViewAdapter;
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

    View rootView;
    private MyRecyclerViewAdapter mAdapter;
    private static String LOG_TAG = "CardViewActivity";
    FloatingActionButton addNewNote;
    public static final int ROUND = 1;
    public static final int ROUND_WITH_BORDER = 2;
    int type = ROUND;
    private Menu menu = null;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view);
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        addNewNote = (FloatingActionButton) findViewById(R.id.addNewNote);

        // initialize the builder based on the "TYPE"
        Builder mDrawableBuilder;
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

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        addNewNote.setOnClickListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyRecyclerViewAdapter(getNotesList(), mDrawableBuilder, this, new ItemSelectionListener() {
            @Override
            public void onSelected() {
                System.out.println("AAAA Item Selected");
                menu.findItem(R.id.delete).setVisible(true);
                menu.findItem(R.id.delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

            @Override
            public void onUnselected() {
                System.out.println("AAAA Item Unselected");
                if (null != menu) {
                    menu.findItem(R.id.delete).setVisible(false);
//                    menu.findItem(R.id.delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
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

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        switch (id) {
            case R.id.delete:
                mAdapter.deleteSelectedItems();
                mAdapter.updateRecentNotesList();
                menu.findItem(R.id.delete).setVisible(false);
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                break;

            case R.id.action_settings:
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*private void updateRecentNotesList() {
    }*/

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