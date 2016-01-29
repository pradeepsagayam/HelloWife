package com.dp.hellowife;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.dp.hellowife.helper.MessageLogHelper;

import java.util.Date;



/**
 * Created by pradeepd on 07-01-2016.
 */
public class DisplayCallLogs extends Activity {

    TextView textView;

//    @InjectView(R.id.listView)
//    SwipeMenuListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_log);
        textView = (TextView) findViewById(R.id.textview_call);
        MessageLogHelper.deleteMessage(getApplicationContext());//getAllMessages(getApplicationContext(), "8870703962");
//        setupListAdapter();
//        getCallDetails();
    }

//    private void setupListAdapter() {
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//
//            @Override
//            public void create(SwipeMenu menu) {
//                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(Utils.dp2px(getApplicationContext(), 90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);
//
//                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                // set item width
//                deleteItem.setWidth(Utils.dp2px(getApplicationContext(), 90));
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_menu_share);
//                // add to menu
//                menu.addMenuItem(deleteItem);
//            }
//        };
//
//// set creator
//        listView.setMenuCreator(creator);
//        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
//        listView.setOnMenuItemClickListener(onMenuItemClickListener);
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getCallDetails() {
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
    };
}
