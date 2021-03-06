package com.existentio.spacenotes2.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.existentio.spacenotes2.R;
import com.existentio.spacenotes2.util.PrefHelper;
import com.existentio.spacenotes2.model.Notes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Георгий on 19.09.2017.
 */

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.ViewHolder> {
    private List<Notes> mNotes = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;
    public static int NOTE_ID = 0;

    public NoteItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setNotes(List<Notes> items) {
        mNotes.clear();
        mNotes.addAll(items);
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public NoteItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteItemAdapter.ViewHolder holder, final int position) {
        if (mNotes.size() <= position) {
            return;
        }

        final Notes note = mNotes.get(position);
        holder.description.setText(note.getDescription());
        holder.date.setText(note.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemSelected(note);
                    setListId(note.getId());
                }
            }
        });

        //init row view based on preferences
        initSettings(holder);

        holder.itemView.setTag(note);
    }


    public void setListId(int id) {
        NOTE_ID = id;
    }

    public int getListId() {
        return NOTE_ID;
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public interface OnItemClickListener {
        void onItemSelected(Notes noteItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private TextView date;
        private LinearLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.note_description);
            date = (TextView) itemView.findViewById(R.id.note_date);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.row_layout);

        }
    }

    private void initSettings(ViewHolder holder) {
        SharedPreferences spTheme = mContext.getSharedPreferences(PrefHelper.PREF_THEME,
                Context.MODE_PRIVATE);
        if (spTheme.contains(PrefHelper.KEY_THEME_WASTELAND)) {
            holder.itemLayout.setBackgroundResource(R.drawable.mars_design);
            holder.description.setTextColor(ContextCompat.getColor(mContext, R.color.mars));
            holder.date.setTextColor(ContextCompat.getColor(mContext, R.color.mars));

        } else if (spTheme.contains(PrefHelper.KEY_THEME_MINIMAL)) {
            holder.itemLayout.setBackgroundResource(R.drawable.minimal_theme);
            holder.description.setTextColor(ContextCompat.getColor(mContext, R.color.neo_green));
            holder.date.setTextColor(ContextCompat.getColor(mContext, R.color.neo_green));
        }
    }
}


