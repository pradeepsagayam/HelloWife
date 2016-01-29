package com.dp.hellowife.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.dp.hellowife.model.SMSData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by pradeepd on 08-01-2016.
 */
public class MessageLogHelper {

    public static void getAllMessages(Context context, String mobileNumber) {
        final StringBuilder msgString = new StringBuilder();
        List<SMSData> smsList = new ArrayList<>();

//        Uri uri = Uri.parse("content://sms/conversations/");
//        Cursor c = context.getContentResolver().query(uri, null, null, null, null);

        ContentResolver contentResolver = context.getContentResolver();
        final String[] projection = new String[]{"*"};
        final String SELECTION = "address" + "=?";// OR " + "address" + "=? OR " + "address" + "=? OR " + "address" + "=?";
        Uri uri = Uri.parse("content://sms/");
        Cursor c = contentResolver.query(uri, null, SELECTION, new String[]{"88707 03962"}, null);
//        Cursor c = contentResolver.query(uri, null, null, null, null);
//startManagingCursor(c);

// Read the sms data and store it in the list
        if (null != c && c.getCount() > 0) {
            while (c.moveToNext()) {
//                SMSData sms = new SMSData();
//                sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
//                sms.setDate(c.getString(c.getColumnIndexOrThrow("date")).toString());
//                sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
//                smsList.add(sms);

                String address = c.getString(c.getColumnIndexOrThrow("address")).toString();
                String threadId = c.getString(c.getColumnIndexOrThrow("thread_id")).toString();
                String mbody = c.getString(c.getColumnIndexOrThrow("body")).toString();
                String mdate = c.getString(c.getColumnIndexOrThrow("date")).toString();
                Date dt = new Date(Long.valueOf(mdate));

                msgString.append(address + "<-||->" + threadId);
                msgString.append(mbody + "<-||->");
                msgString.append(dt + "<-||->");
                msgString.append(mdate + "<--!-->");
                msgString.append("\n");
            }
            System.out.println(msgString.toString());
            c.close();
        }


    }


    public static void deleteMessage(Context context) {
        Cursor c = context.getContentResolver().query(Uri.parse("content://sms/"), null, null, null, null);
        if (null != c && c.getCount() > 0) {
            while (c.moveToNext()) {
                System.out.println("Pradeep Inside if loop");
                try {
                    String address = c.getString(c.getColumnIndexOrThrow("address"));
                    String MobileNumber = "8870703962";
                    String threadId = c.getString(c.getColumnIndexOrThrow("thread_id")).toString();
                    //Log.i( LOGTAG, MobileNumber + "," + address );
                    address = formatMobileNumber(address);
                    System.out.println("Pradeep address == " + address + " MobileNumber == " + MobileNumber);
                    if (address.trim().equals(MobileNumber)) {
                        System.out.println("Pradeep inside delete");
                        String pid = c.getString(1);
                        String uri = "content://sms/";
                        final String SELECTION = "thread_id" + "=?";
                        context.getContentResolver().delete(Uri.parse(uri), SELECTION, new String[]{threadId});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Pradeep no records===");
        }
    }

    public static String formatMobileNumber(String mobileNumber) {
        String REGEX = "[^0-9]";
        return mobileNumber.replaceAll(REGEX, "");
    }
}
