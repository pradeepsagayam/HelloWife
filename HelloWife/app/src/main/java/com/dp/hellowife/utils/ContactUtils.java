package com.dp.hellowife.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telecom.Call;

import java.util.ArrayList;
import java.util.Date;

import model.CallLogs;

/**
 * Created by pradeepd on 07-01-2016.
 */
public class ContactUtils {

    public static ArrayList<CallLogs> getAllCallLogs(Context context, Cursor managedCursor) {
        ArrayList<CallLogs> callLogsList = new ArrayList<>();
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME); // contact Name
        int numberType = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NUMBER_TYPE); // Home or Work.
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER); // contact number
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE); // call type (Incomming, outgoing etc..).
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int idOfRowToDelete = managedCursor.getColumnIndex(CallLog.Calls._ID); // Call log ID.
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String callName = managedCursor.getString(name);
            int callumberType = managedCursor.getInt(numberType);
            String callLogID = "" + managedCursor.getInt(idOfRowToDelete);

            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
//            sb.append("\nName:---  " + callName + "\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
//            sb.append("\n----------------------------------");
            CallLogs callLogs = new CallLogs();

            callLogs.setCallLogID(callLogID);
            callLogs.setCallDate(callDate);
            callLogs.setCallNumberType(callumberType);
            callLogs.setCallType(dir);
            callLogs.setType(dircode);
            String contactId = fetchContactIdFromPhoneNumber(context, phNumber);
            callLogs.setContactId(contactId);
            callLogs.setContactPhotoUri(getPhotoUri(context.getContentResolver(), contactId));
            callLogs.setDuration(callDuration);
            callLogs.setName(callName);
            callLogs.setNumber(phNumber);
            callLogs.setNumberType("" + callumberType);
            callLogsList.add(callLogs);
        }
        return callLogsList;
    }


    /**
     * function is used to get contact photo from phone number
     */
    public static Uri getPhotoUri(ContentResolver contentResolver, String id) {
        long contactId = Long.parseLong(id);
        try {
            Cursor cursor = contentResolver
                    .query(ContactsContract.Data.CONTENT_URI,
                            null,
                            ContactsContract.Data.CONTACT_ID
                                    + "="
                                    + contactId
                                    + " AND "

                                    + ContactsContract.Data.MIMETYPE
                                    + "='"
                                    + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                                    + "'", null, null);

            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Uri person = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI, contactId);
        return Uri.withAppendedPath(person,
                ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    public static void wirteCallLog(Context context, CallLogs callLogs) {
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

    public static void deleteCallLog(Context context, CallLogs callLogs) {
        deleteCallLog(context, callLogs.getCallLogID());
    }

    public static void deleteCallLog(Context context, String callLogID) {
        context.getContentResolver().delete(Uri.withAppendedPath(CallLog.Calls.CONTENT_URI, callLogID), "", null);
    }

    public static String fetchContactIdFromPhoneNumber(Context context,
                                                       String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cFetch = context.getContentResolver().query(uri,
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID},
                null, null, null);
        String contactId = "";
        if (cFetch.moveToFirst()) {
            cFetch.moveToFirst();
            contactId = cFetch
                    .getString(cFetch.getColumnIndex(ContactsContract.PhoneLookup._ID));
        }
        return contactId;
    }

}
