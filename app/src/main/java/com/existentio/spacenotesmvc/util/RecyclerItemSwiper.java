package com.existentio.spacenotesmvc.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.existentio.spacenotesmvc.controller.DBHelper;
import com.existentio.spacenotesmvc.model.Notes;
import com.existentio.spacenotesmvc.ui.NoteItemAdapter;

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
        adapter.notifyItemRemoved(position);    //item removed from recylcerview
        int newId = adapter.getListId();
        db.delNote(newId);
        list.remove(position);
    }
}

