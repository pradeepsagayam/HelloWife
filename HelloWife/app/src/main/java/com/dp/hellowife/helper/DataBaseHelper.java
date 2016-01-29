package com.dp.hellowife.helper;

import android.content.Context;

import com.dp.hellowife.model.Notes;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;


/**
 * Created by pradeepd on 09-01-2016.
 */
public class DataBaseHelper {

//    public static void openDB(Context context, String dbName) {
//        try {
//            SnappyDB mSnappyDB = SnappyDB.with(context).Builder(context).build();
//
//        } catch (SnappydbException e) {
//            e.printStackTrace();
//            throw new IllegalStateException("Can't create database");
//        }
//    }

    public static void insertNote(Context context, ArrayList<Notes> notesList) {
//        Notes[] notes = (Notes[]) notesList.toArray();
        try {
            DB snappydb = DBFactory.open(context, "notes");
            snappydb.put("notesList", notesList);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Notes> retriveNotes(Context context) {
        ArrayList<Notes> notesArrayList = new ArrayList<>();
        try {
            DB snappydb = DBFactory.open(context, "notes");
            notesArrayList = snappydb.getObject("notesList", ArrayList.class);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return notesArrayList;
    }
}
