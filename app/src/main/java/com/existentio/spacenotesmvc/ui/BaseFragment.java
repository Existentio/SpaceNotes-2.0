package com.existentio.spacenotesmvc.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.existentio.spacenotesmvc.R;
import com.existentio.spacenotesmvc.controller.DBHelper;
import com.existentio.spacenotesmvc.model.Notes;
import com.existentio.spacenotesmvc.util.RecyclerItemSwiper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;
import static com.existentio.spacenotesmvc.ui.MainActivity.notes;


public class BaseFragment extends Fragment implements NoteItemAdapter.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private LinearLayoutManager mLayoutManager;
    private NoteItemAdapter adapter;
    private DBHelper db;

    private RecyclerView mContainer;

    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        mContainer = (RecyclerView) view.findViewById(R.id.rv_base);
        mLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new NoteItemAdapter(getActivity());
        adapter.setListener(this);
        mContainer.setLayoutManager(mLayoutManager);
        mContainer.setAdapter(adapter);

        initApps(notes);

        new RecyclerItemSwiper(0, LEFT | RIGHT, getContext(),
                adapter, notes, db, mContainer);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }

    public void saveToDb() {
//        DBHelper db = new DBHelper(this);
        EditText etText = (EditText) getActivity().findViewById(R.id.et_enter_text);
        String text = etText.getText().toString();
        String date = DateFormat.getDateInstance
                (DateFormat.SHORT).format(Calendar.getInstance().getTime());
        if (text.isEmpty()) {
//            Toast.makeText(this, "empty note", Toast.LENGTH_SHORT).show();
        } else {
            db.addNote(text, date);
//            db.updRec(text, date, adapter.getListId());

//            showToast(R.string.note_added);
//            Toast.makeText(this, "note added", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, this)
                    .commit();
            refresh();
        }
    }

    public void refresh() {
        adapter.notifyDataSetChanged();

    }


    private void initApps(List<Notes> list) {
        adapter.setNotes(list);
        db.getNotes(list);

        mContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(Notes noteItem) {
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, editNoteFragment)
                .setTransition(TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("frags")
                .commit();
        Bundle bundle = new Bundle();
        bundle.putString("test", noteItem.getDescription());
        editNoteFragment.setArguments(bundle);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}