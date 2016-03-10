package com.dp.hellowife.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dp.hellowife.R;
import com.dp.hellowife.model.CallLogs;

import java.util.ArrayList;

/**
 * Created by akshayas on 3/9/2016.
 */
public class CallLogRecyclerViewAdapter extends RecyclerView.Adapter<CallLogRecyclerViewAdapter.DataObjectHolder> {
    private final ArrayList<CallLogs> callLogs;
    private final Context context;

    public CallLogRecyclerViewAdapter(ArrayList<CallLogs> callLog, Context context) {
        this.callLogs = callLog;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder dataObjectHolder, int position) {
        CallLogs item = getItem(position);
        dataObjectHolder.personName.setText(item.getName());
        dataObjectHolder.callType.setText(item.getCallType());
        dataObjectHolder.dateTime.setText(item.getCallDate());
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_call_log, viewGroup, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    private CallLogs getItem(int position) {
        return callLogs.get(position);
    }

    @Override
    public int getItemCount() {
        return callLogs.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        LinearLayout callLogListItem;
        CardView cardView;
        TextView personName;
        TextView callType;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            callLogListItem = (LinearLayout) itemView.findViewById(R.id.call_log_list_item);
            cardView = (CardView) itemView.findViewById(R.id.call_log_cards);
            personName = (TextView) itemView.findViewById(R.id.name);
            callType = (TextView) itemView.findViewById(R.id.call_type);
            dateTime = (TextView) itemView.findViewById(R.id.date_time);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
