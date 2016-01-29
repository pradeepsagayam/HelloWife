package com.dp.hellowife.helper;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

import model.CallLogs;

/**
 * Created by pradeepd on 08-01-2016.
 */
public class CallLogHelper {


    private static void wirteCallLog(Context context, CallLogs callLogs) {
        ContentValues values = new ContentValues();
        values.put(CallLog.Calls.NUMBER, callLogs.getNumber());
        values.put(CallLog.Calls.DATE, callLogs.getCallDate());
        values.put(CallLog.Calls.DURATION, 0);
        values.put(CallLog.Calls.TYPE, callLogs.getType());
        values.put(CallLog.Calls.NEW, 1);
        values.put(CallLog.Calls.CACHED_NAME, callLogs.getName());
        values.put(CallLog.Calls.CACHED_NUMBER_TYPE, callLogs.getCallNumberType());
        values.put(CallLog.Calls.CACHED_NUMBER_LABEL, "");
        try {
            context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteCallLog(Context context, CallLogs callLogs) {
        deleteCallLog(context, callLogs.getCallLogID());
    }

    private static void deleteCallLog(Context context, String callLogID) {
        final String SELECTION = CallLog.Calls._ID + "=?";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, SELECTION, new String[]{callLogID});
    }

    public static void deleteCallLogUsingMobileNumber(Context context, String mobileNumber) {
        deleteCallLogsUsingCallLogID(context, mobileNumber);
    }

    public static void deleteCallLogUsingMobileNumber(Context context, ArrayList<String> mobileNumberList) {
        for (String mobileNumber : mobileNumberList)
            deleteCallLogsUsingCallLogID(context, mobileNumber);
    }

    private static void deleteCallLogsUsingCallLogID(Context context, String mobileNumber) {
        String callLogID = "";
        mobileNumber = mobileNumber.substring(mobileNumber.length() - 10); // get Exact 10 digit mobile number.
        final String SELECTION = CallLog.Calls.NUMBER + "=? OR " + CallLog.Calls.NUMBER + "=? OR " + CallLog.Calls.NUMBER + "=? OR " + CallLog.Calls.NUMBER + "=?";
        try {
            Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, SELECTION, new String[]{"+91" + mobileNumber, mobileNumber, "91" + mobileNumber, "0" + mobileNumber}, null);
            int idOfRowToDelete = managedCursor.getColumnIndex(CallLog.Calls._ID); // Call log ID.
            while (managedCursor.moveToNext()) {
                callLogID = "" + managedCursor.getInt(idOfRowToDelete);
                deleteCallLog(context, callLogID);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
