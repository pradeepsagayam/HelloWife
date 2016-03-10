package com.dp.hellowife.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dp.hellowife.R;
import com.dp.hellowife.helper.ColorGeneratorHelper;
import com.dp.hellowife.helper.DataBaseHelper;
import com.dp.hellowife.helper.TextDrawableHelper;
import com.dp.hellowife.interfaces.Builder;
import com.dp.hellowife.interfaces.ItemSelectionListener;
import com.dp.hellowife.model.Notes;

import java.util.ArrayList;

/**
 * Created by pradeepd on 09-01-2016.
 */
public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "NotesRecyclerViewAdapter";
    private ArrayList<Notes> mDataSet;
    ArrayList<Notes> mSelectedData = new ArrayList<>();
    private static MyClickListener myClickListener;
    private static final int HIGHLIGHT_COLOR = 0x99dddddd;
    private final Builder mDrawableBuilder;
    private ColorGeneratorHelper mColorGenerator = ColorGeneratorHelper.MATERIAL;
    DataObjectHolder dataObjectHolder;
    Context context;
    ItemSelectionListener itemSelectionListener;

    public NotesRecyclerViewAdapter(ArrayList<Notes> myDataSet, Builder mDrawableBuilder, Context context, ItemSelectionListener itemSelectionListener) {
        this.mDataSet = myDataSet;
        this.mDrawableBuilder = mDrawableBuilder;
        this.context = context;
        this.itemSelectionListener = itemSelectionListener;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        Notes notesItem = getItem(position);

        holder.label.setText(notesItem.getTitle());
        holder.content.setText(notesItem.getBody());
        holder.dateTime.setText(notesItem.getDate() + " - " + notesItem.getTime());

        updateCheckedState(holder, notesItem);

        Log.i(LOG_TAG, "Adding Listener");

        dataObjectHolder.notesListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onItemClick(position, v);
            }
        });

        dataObjectHolder.textIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onItemClick(position, v);
                Notes notesItem = getItem(position);
                notesItem.setChecked(!notesItem.isChecked);
                updateCheckedState(holder, notesItem);
            }
        });
    }

    @Override
    public DataObjectHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list, parent, false);

        dataObjectHolder = new DataObjectHolder(view);

        return dataObjectHolder;
    }

    private void updateCheckedState(DataObjectHolder dataObjectHolder, Notes item) {
        if (item.isChecked) {
            dataObjectHolder.textIcon.setImageDrawable(mDrawableBuilder.build(" ", 0xff616161));
            dataObjectHolder.notesView.setBackgroundColor(HIGHLIGHT_COLOR);
            dataObjectHolder.checkIcon.setVisibility(View.VISIBLE);
            mSelectedData.add(item);
            itemSelectionListener.onSelected();
        } else {
            TextDrawableHelper drawable = mDrawableBuilder.build(String.valueOf(dataObjectHolder.label.getText().toString().charAt(0)), mColorGenerator.getColor(dataObjectHolder.label));
            dataObjectHolder.textIcon.setImageDrawable(drawable);
            dataObjectHolder.notesView.setBackgroundColor(Color.WHITE);
            dataObjectHolder.checkIcon.setVisibility(View.GONE);
            mSelectedData.remove(item);
            itemSelectionListener.onUnselected();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public Notes getItem(int position) {
        return mDataSet.get(position);
    }

    public void clear() {
        mDataSet.clear();
    }

    public void addAll(ArrayList<Notes> notesArrayList) {
        mDataSet.addAll(notesArrayList);
        notifyDataSetChanged();
    }

    public void addItem(Notes dataObj, int index) {
        mDataSet.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteSelectedItems() {
        for (Notes item : mSelectedData) {
            mDataSet.remove(item);
        }
        notifyDataSetChanged();
    }

    public void updateRecentNotesList() {
        if (null != mDataSet) {
            DataBaseHelper.insertNote(context, mDataSet);
        }
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        NotesRecyclerViewAdapter.myClickListener = myClickListener;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        LinearLayout notesListItem;
        CardView notesView;
        TextView label;
        TextView dateTime;
        TextView content;
        ImageView textIcon;
        ImageView checkIcon;

        public DataObjectHolder(View itemView) {
            super(itemView);
            notesListItem = (LinearLayout) itemView.findViewById(R.id.notes_list_item);
            notesView = (CardView) itemView.findViewById(R.id.notes_card_view);
            label = (TextView) itemView.findViewById(R.id.notes_title);
            dateTime = (TextView) itemView.findViewById(R.id.date_time);
            content = (TextView) itemView.findViewById(R.id.note_content);
            textIcon = (ImageView) itemView.findViewById(R.id.text_icon);
            checkIcon = (ImageView) itemView.findViewById(R.id.check_icon);
        }
    }
}
