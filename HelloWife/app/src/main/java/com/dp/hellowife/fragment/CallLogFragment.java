package com.dp.hellowife.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.hellowife.R;
import com.dp.hellowife.adapter.CallLogRecyclerViewAdapter;
import com.dp.hellowife.model.CallLogs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallLogFragment extends Fragment {

    private final Context context;
    private RecyclerView callLogRecyclerView;
    CallLogs callLogItem = new CallLogs();
    ArrayList<CallLogs> callLogList = new ArrayList<>();
    CallLogRecyclerViewAdapter mAdapter;

    public CallLogFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.call_logs_card_view, container, false);
        // Inflate the layout for this fragment
        callLogRecyclerView = (RecyclerView) rootView.findViewById(R.id.call_log_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        callLogRecyclerView.setLayoutManager(layoutManager);

        getCallDetails();

        mAdapter = new CallLogRecyclerViewAdapter(callLogList, context);
        callLogRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void getCallDetails() {
//        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final String SELECTION = CallLog.Calls.NUMBER + "=? OR " + CallLog.Calls.NUMBER + "=? OR " + CallLog.Calls.NUMBER + "=? OR " + CallLog.Calls.NUMBER + "=?";
        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, SELECTION, new String[]{"+919952978313", "9952978313", "919952978313", "09952978313"}, null);
//        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        if (null != managedCursor && managedCursor.getCount() > 0) {
            while (managedCursor.moveToNext()) {
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                SimpleDateFormat toFullDate = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                String fullDate = toFullDate.format(callDayTime);
                String callDuration = managedCursor.getString(duration);
                String callName = managedCursor.getString(name);

                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "Outgoing";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "Incoming";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "Missed";
                        break;
                }
                setCallDetails(phNumber, dir, fullDate, callName);
            }
//        managedCursor.close();
        } else {
            Log.v("CallLogFragment", "No Call Logs found!! ");
        }
    }

    private void setCallDetails(String phNumber, String dir, String callDayTime, String callName) {
        callLogItem.setNumber(phNumber);
        callLogItem.setCallType(dir);
        callLogItem.setCallDate(callDayTime);
        callLogItem.setName(callName);

        callLogList.add(callLogItem);
    }

/*    private void getCallDetails() {
        StringBuffer sb = new StringBuffer();
//        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final String SELECTION = CallLog.Calls.NUMBER + "=? OR " + CallLog.Calls.NUMBER + "=? OR " + CallLog.Calls.NUMBER + "=? OR " + CallLog.Calls.NUMBER + "=?";
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, SELECTION, new String[]{"+919500126349", "9500126349", "919500126349", "09500126349"}, null);
//        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Log :");
        if (null != managedCursor && managedCursor.getCount() > 0) {
            while (managedCursor.moveToNext()) {
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String callName = managedCursor.getString(name);


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
                String str = "\nName:---  " + callName + "\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration;
                sb.append(str);
                sb.append("\n----------------------------------");
            }
            textView.setText(sb);
//        managedCursor.close();
        } else {
            System.out.println("pradeep No Call Logs found == ");
        }
    }


    SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener = new SwipeMenuListView.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
            switch (index) {
                case 0:
                    // open
                    break;
                case 1:
                    // delete
                    break;
            }
            // false : close the menu; true : not close the menu
            return false;
        }
    };*/
}
