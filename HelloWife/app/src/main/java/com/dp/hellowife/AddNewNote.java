package com.dp.hellowife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dp.hellowife.helper.DataBaseHelper;
import com.dp.hellowife.model.Notes;

import java.util.ArrayList;


/**
 * Created by pradeepd on 09-01-2016.
 */
public class AddNewNote extends AppCompatActivity implements View.OnClickListener {

    EditText title;

    EditText body;

    LinearLayout titleLayout;

    RelativeLayout bodyLayout;

    TextView bodyHintTextView;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        setupViews();
        setupControllers();

    }

    private void setupViews() {
        title = (EditText) findViewById(R.id.newNoteTitle);
        body = (EditText) findViewById(R.id.newNoteBody);
        titleLayout = (LinearLayout) findViewById(R.id.newNoteTitleLayout);
        bodyLayout = (RelativeLayout) findViewById(R.id.newNoteBodyLayout);
        bodyHintTextView = (TextView) findViewById(R.id.hintTextView);
    }

    private void setupControllers() {
        body.setOnFocusChangeListener(focusChangeListener);
        titleLayout.setOnClickListener(this);
        bodyLayout.setOnClickListener(this);
        title.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_FILTER);
        body.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_FILTER);
        bodyHintTextView.setOnClickListener(this);
        title.setOnEditorActionListener(onEditorActionListener);
        body.setOnEditorActionListener(onEditorActionListener);
        body.setImeOptions(EditorInfo.IME_ACTION_DONE);
        title.setImeOptions(EditorInfo.IME_ACTION_NEXT);
    }

    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (v == body) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    saveNotes(getNotesFromInput());
                }
            }
            if (v == title) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    showBodyTextView();
                }
            }
            return false;
        }
    };

//    @Override
//    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
//        InputConnection connection = super.onCreateInputConnection(outAttrs);
//        int imeActions = outAttrs.imeOptions & EditorInfo.IME_MASK_ACTION;
//        if ((imeActions & EditorInfo.IME_ACTION_DONE) != 0) {
//            // clear the existing action
//            outAttrs.imeOptions ^= imeActions;
//            // set the DONE action
//            outAttrs.imeOptions |= EditorInfo.IME_ACTION_DONE;
//        }
//        if ((outAttrs.imeOptions & EditorInfo.IME_FLAG_NO_ENTER_ACTION) != 0) {
//            outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
//        }
//        return connection;
//    }

    private Notes getNotesFromInput() {
        String titletext = title.getText().toString();
        String bodyText = body.getText().toString();
        if (titletext.length() == 0 && bodyText.length() == 0) {
            return null;
        } else {
            Notes notes = new Notes();
            notes.setTitle(titletext);
            notes.setBody(bodyText);
            return notes;
        }
    }

    private void saveNotes(Notes notes) {
        if (null != notes) {
            ArrayList<Notes> notesArrayList = DataBaseHelper.retriveNotes(getApplicationContext());
            notesArrayList.add(notes);
            DataBaseHelper.insertNote(getApplicationContext(), notesArrayList);
            Toast.makeText(AddNewNote.this, "Note saved Successfully", Toast.LENGTH_SHORT).show();
            navigateToNotesList();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveNotes(getNotesFromInput());
        finish();
    }

    private void navigateToNotesList() {
        Intent i = new Intent(AddNewNote.this, NotesList.class);
        startActivity(i);
        finish();
    }

    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!v.hasFocus() && ((EditText) v).getText().toString().length() <= 0) {
                bodyHintTextView.setVisibility(View.VISIBLE);
                body.setVisibility(View.GONE);
            }
        }
    };

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v == titleLayout) {

        } else if (v == bodyLayout) {
            showBodyTextView();
        } else if (v == bodyHintTextView) {
            showBodyTextView();
        }

    }

    private void showBodyTextView() {
        bodyHintTextView.setVisibility(View.GONE);
        body.setVisibility(View.VISIBLE);
        body.requestFocus();
    }
}
