package com.dp.hellowife.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import com.dp.hellowife.model.CallLogs;

import java.util.ArrayList;
import java.util.Date;

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
