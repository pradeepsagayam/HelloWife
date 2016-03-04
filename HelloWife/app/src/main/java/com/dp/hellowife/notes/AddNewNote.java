package com.dp.hellowife.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dp.hellowife.R;
import com.dp.hellowife.helper.DataBaseHelper;
import com.dp.hellowife.model.Notes;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by pradeepd on 09-01-2016.
 */
public class AddNewNote extends AppCompatActivity implements
        View.OnClickListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    EditText noteTitle, noteText;
    TextView timeValue, dateValue, locationValue, favouriteValue;
    ImageView timeIcon, dateIcon, locationIcon, favouriteIcon;
    RelativeLayout timeLayout, dateLayout, locationLayout, favouriteLayout;
    Calendar now;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Boolean enableDarkTheme = true;
    Boolean enable24HoursMode = false;
    Boolean enableVibration = true;
    Boolean enableSeconds = false;
    Boolean enableShowYearFirst = false;
    String separator = "/";
    Boolean isFavourite = false;
    private static final int LOCATION_PICKER_REQUEST_CODE = 10;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        setUpViews();
        setUpControllers();

    }

    private void setUpViews() {
        noteTitle = (EditText) findViewById(R.id.note_title);
        noteText = (EditText) findViewById(R.id.note_text);

        timeValue = (TextView) findViewById(R.id.time_value);
        dateValue = (TextView) findViewById(R.id.date_value);
        locationValue = (TextView) findViewById(R.id.location_value);
        favouriteValue = (TextView) findViewById(R.id.favourite_value);

        timeIcon = (ImageView) findViewById(R.id.time_icon);
        dateIcon = (ImageView) findViewById(R.id.date_icon);
        locationIcon = (ImageView) findViewById(R.id.location_icon);
        favouriteIcon = (ImageView) findViewById(R.id.favourite_icon);

        timeLayout = (RelativeLayout) findViewById(R.id.time_layout);
        dateLayout = (RelativeLayout) findViewById(R.id.date_layout);
        locationLayout = (RelativeLayout) findViewById(R.id.location_layout);
        favouriteLayout = (RelativeLayout) findViewById(R.id.favourite_layout);
    }

    private void setUpControllers() {
        noteTitle.requestFocus();
//        noteText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_VARIATION_FILTER);
        noteTitle.setOnEditorActionListener(myEditorActionListener);
        noteText.setOnEditorActionListener(myEditorActionListener);

        noteText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        noteTitle.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        timeIcon.setOnClickListener(this);
        dateIcon.setOnClickListener(this);
        locationIcon.setOnClickListener(this);
        favouriteIcon.setOnClickListener(this);

        timeLayout.setOnClickListener(this);
        dateLayout.setOnClickListener(this);
        locationLayout.setOnClickListener(this);
        favouriteLayout.setOnClickListener(this);
    }

    TextView.OnEditorActionListener myEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (v == noteText) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    saveNotes(getNotesFromInput());
                }
            }
            if (v == noteTitle) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    noteTitle.clearFocus();
                    noteText.requestFocus();
//                    showNoteTextView();
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
        String titleText = noteTitle.getText().toString();
        String bodyText = noteText.getText().toString();
        String timeText = timeValue.getText().toString();
        String dateText = dateValue.getText().toString();
        String locationText = locationValue.getText().toString();
        String favoriteText = favouriteValue.getText().toString();
        if (titleText.length() == 0 && bodyText.length() == 0) {
            return null;
        } else {
            Notes notes = new Notes();
            notes.setTitle(titleText);
            notes.setBody(bodyText);
            notes.setTime(timeText);
            notes.setDate(dateText);
            notes.setPlace(locationText);
            notes.setFavorite(favoriteText);
            return notes;
        }
    }

    private void saveNotes(Notes notes) {
        if (null != notes) {
            ArrayList<Notes> notesArrayList = DataBaseHelper.retriveNotes(getApplicationContext());
            notesArrayList.add(notes);
            DataBaseHelper.insertNote(getApplicationContext(), notesArrayList);
            Toast.makeText(AddNewNote.this, "Note saved Successfully..", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
        } else {
            Toast.makeText(AddNewNote.this, "Can't save Empty Notes..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveNotes(getNotesFromInput());
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_done:
                saveNotes(getNotesFromInput());
                finish();
                break;
            case R.id.action_delete:
                clearAllFields();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void clearAllFields() {
        noteTitle.setText("");
        noteText.setText("");
        timeValue.setText("");
        dateValue.setText("");
        locationValue.setText("");
        favouriteValue.setText("");
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.time_icon:
            case R.id.time_layout:
                noteTitle.clearFocus();
                noteText.clearFocus();
                pickTime();
                break;
            case R.id.date_icon:
            case R.id.date_layout:
                pickDate();
                break;
            case R.id.location_icon:
            case R.id.location_layout:
                pickLocation();
                break;
            case R.id.favourite_icon:
            case R.id.favourite_layout:
                pickFavourite();
                break;
            default:
                break;
        }
    }

    private void pickTime() {
        now = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(
                AddNewNote.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                enable24HoursMode
        );
        timePickerDialog.setThemeDark(enableDarkTheme);
        timePickerDialog.vibrate(enableVibration);
        timePickerDialog.enableSeconds(enableSeconds);
        timePickerDialog.setAccentColor(this.getResources().getColor(R.color.colorPrimary));
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(AddNewNote.this, "Sorry.. Dialog is Cancelled..", Toast.LENGTH_SHORT).show();
            }
        });
        timePickerDialog.show(getFragmentManager(), "Time Picker");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String time;
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        if (enableSeconds) {
            String secondString = second < 10 ? "0" + second : "" + second;
            time = hourString + ":" + minuteString + ":" + secondString;
        } else {
            time = hourString + ":" + minuteString;
        }
        timeValue.setText(time);
    }

    private void pickDate() {
        now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                AddNewNote.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setThemeDark(enableDarkTheme);
        datePickerDialog.vibrate(enableVibration);
        datePickerDialog.setAccentColor(this.getResources().getColor(R.color.colorPrimary));
        datePickerDialog.showYearPickerFirst(enableShowYearFirst);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(AddNewNote.this, "Sorry.. Dialog is Cancelled..", Toast.LENGTH_SHORT).show();
            }
        });
        datePickerDialog.show(getFragmentManager(), "Date Picker");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + separator + (++monthOfYear) + separator + year;
        dateValue.setText(date);
    }

    private void pickLocation() {
        Intent locationIntent = new Intent(this, LocationPicker.class);
        startActivityForResult(locationIntent, LOCATION_PICKER_REQUEST_CODE);
    }

    private void pickFavourite() {
        if (!isFavourite) {
            isFavourite = true;
            favouriteIcon.setBackgroundResource(R.drawable.ic_favourite_selected);
            favouriteValue.setText("Yes!!");
        } else {
            isFavourite = false;
            favouriteIcon.setBackgroundResource(R.drawable.ic_favourite_unselected);
            favouriteValue.setText("No!!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        timePickerDialog = (TimePickerDialog) getFragmentManager().findFragmentByTag("Time Picker");
        datePickerDialog = (DatePickerDialog) getFragmentManager().findFragmentByTag("Date Picker");

        if (timePickerDialog != null) timePickerDialog.setOnTimeSetListener(this);
        if (datePickerDialog != null) datePickerDialog.setOnDateSetListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String place = data.getStringExtra("place");
            locationValue.setText(place);
        }
    }
}
