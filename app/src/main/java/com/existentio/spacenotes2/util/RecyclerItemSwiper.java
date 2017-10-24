package com.existentio.spacenotes2.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.existentio.spacenotes2.data.DBHelper;
import com.existentio.spacenotes2.model.Notes;
import com.existentio.spacenotes2.ui.NoteItemAdapter;

import java.util.List;

/**
 * Created by Георгий on 15.08.2017.
 */

public class RecyclerItemSwiper extends ItemTouchHelper.SimpleCallback {

    private Context context;
    private NoteItemAdapter adapter;
    private List<Notes> list;
    private DBHelper db;
    private ItemTouchHelper itemTouchHelper;

    public RecyclerItemSwiper(int dragDirs, int swipeDirs, Context context,
                              NoteItemAdapter adapter, List<Notes> list, DBHelper db, RecyclerView recyclerView) {
        super(dragDirs, swipeDirs);
        this.context = context;
        this.adapter = adapter;
        this.list = list;
        this.db = db;
        itemTouchHelper = new ItemTouchHelper(RecyclerItemSwiper.this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //do nothing
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();

        int newId = adapter.getListId();
        db.delNote(newId);
        list.remove(position);

        Log.d("item position", String.valueOf(position));
        Log.d("list id", String.valueOf(position));
        adapter.notifyItemRemoved(position);    //item removed from recylcerview
        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
    }
}

